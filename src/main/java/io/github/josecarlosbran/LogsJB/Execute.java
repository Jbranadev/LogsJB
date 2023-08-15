/***
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 *
 * Con licencia de Apache License, Versión 2.0 (la "Licencia");
 * no puede usar este archivo excepto de conformidad con la Licencia.
 * Puede obtener una copia de la Licencia en
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * A menos que lo exija la ley aplicable o se acuerde por escrito, el software
 * distribuido bajo la Licencia se distribuye "TAL CUAL",
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
 * Consulte la Licencia para conocer el idioma específico que rige los permisos y
 * limitaciones bajo la Licencia.
 */

package io.github.josecarlosbran.LogsJB;


import com.google.gson.JsonObject;
import io.github.josecarlosbran.JBRestAPI.Enumeraciones.contentType;
import io.github.josecarlosbran.JBRestAPI.Enumeraciones.requestCode;
import io.github.josecarlosbran.JBRestAPI.RestApi;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.LogsJB.Numeracion.NivelLog;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.josecarlosbran.LogsJB.LogsJB.getKeyLogRest;
import static io.github.josecarlosbran.LogsJB.LogsJB.getUrlLogRest;
import static io.github.josecarlosbran.LogsJB.MethodsTxt.*;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase encargada de recuperar los mensajes de la lista compartida por el Proceso Principal
 * e iniciar un SubProceso encargado de leer los mensajes de la lista y escribirlos en el LogTxt.
 */
class Execute {


    private LogsJBDB log ;

    private Boolean TaskisReady=true;


    /***
     * Lista que funciona como la cola de peticiones que llegan al Ejecutor
     */
    private static ListaMensajes listado = new ListaMensajes();


    /***
     * Se utiliza el patron Singleton, para asegurarnos que sea una unica instancia la que se encargue de
     * Llevar el control de los Logs
     */
    private static Execute instance = new Execute();

    private Execute() {
        setearRuta();
        setearNivelLog();
        setearSizelLog();
        setearWriteTxt();
        setearWriteDB();
        setearWriteRestAPI();
        setearTipeAutenticación();
        setearKeyLogRest();
        setearUrlLogRest();
    }

    /***
     * Proporciona la instancia de la clase encargada de ejecutar las acciónes en segundo Plano.
     * @return Retorna la instancia de la Clase Execute, que estara trabajando las peticiones de la aplicación
     * en segundo plano.
     */
    protected static Execute getInstance() {
        if(instance==null){
            instance= new Execute();
        }
        return instance;
    }


    /***
     * Proporciona el acceso a la lista que sirve como cola de las peticiones
     * @return Retorna una lista de MensajeWrite, la cual lleva la información que se desea registrar en los Logs
     */
    protected static ListaMensajes getListado() {
        return listado;
    }

    /***
     * Metodo por medio del cual se llama la escritura de los logs
     */
    protected void run() {
        try {
            writePrincipal();
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo Metodo por medio del cual se llama la escritura de los logs");
            com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /***
     * Escritor principal, es el que maneja la logica de la aplicación la cual decide si el log se almacena en una BD's,
     * un Txt Ó si se envía a un RestAPI.
     */
    private void writePrincipal() {
        try {
            getInstance().setTaskisReady(false);
            //Decide si escribe o no en la BD's
            //System.out.println("Valor de escritura BD's: "+ LogsJB.getWriteDB());
            if(LogsJB.getWriteDB()){
                if(!LogsJB.getTableDBExists()){
                    System.out.println("Creara la tabla: ");
                    //Creamos el modelo con las caracteristicas de conexión de la Maquina Virtual
                    try {
                        LogsJBDB log = new LogsJBDB();
                        if(log.crateTable()){
                            LogsJB.setTableDBExists(true);
                        }else{
                            LogsJB.setTableDBExists(true);
                        }
                        System.out.println("Creo la tabla: ");
                    }catch (Exception e) {
                        com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo encargado de crear " +
                                "la tabla de Log en BD's");
                        com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                        com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                        com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                        com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    }
                }
            }
            Runnable EscritorPrincipal = () -> {
                String temporal = "";
                boolean band = true;
                Integer i=0;
                while(band){
                    if(i>5000){
                        verificarSizeFichero();
                        i=0;
                    }
                    i++;
                    //String Mensaje=Execute.getInstance().getTexto();
                    //NivelLog logtemporal=Execute.getInstance().getNivelLog();
                    MensajeWrite mensajetemp = null;
                    mensajetemp = getListado().getDato();
                    //System.out.println("Mensaje en Execute: "+mensajetemp.getTexto()+" "+mensajetemp.getNivelLog());

                    String Mensaje = mensajetemp.getTexto();
                    NivelLog logtemporal = mensajetemp.getNivelLog();
                    String Clase = mensajetemp.getClase();
                    String Metodo = mensajetemp.getMetodo();
                    String fecha = mensajetemp.getFecha();

                    if(LogsJB.getWriteTxt()){
                        writeLog(logtemporal, Mensaje, Clase, Metodo, fecha);
                    }
                    //Decide si escribe o no en la BD's
                    if(LogsJB.getWriteDB()){
                        writeBD(logtemporal, Mensaje, Clase, Metodo, fecha);
                    }
                    //Decide si envía los Logs al RestAPI
                    if(LogsJB.getWriteRestAPI()){
                        writeRestAPI(logtemporal, Mensaje, Clase, Metodo, fecha);
                    }

                    //System.out.println("Cantidad de mensajes Por limpiar: "+getListado().getSize());
                    if (getListado().getSize() == 0) {
                        band = false;
                        getInstance().setTaskisReady(true);
                        break;
                    }
                }
            };
            ExecutorService executorPrincipal = Executors.newFixedThreadPool(1);
            executorPrincipal.submit(EscritorPrincipal);
            executorPrincipal.shutdown();
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo Escritor principal, es el que maneja la logica de la aplicación la cual decide si el log se almacena en una BD's,\n" +
                    "     * un Txt Ó si se envía a un RestAPI.");
            com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());

        }
    }



    /**
     * Metodo encargado de almacenar los Logs en Bd's
     * @param logtemporal Nivel de Log especificado para el texto que se escribira.
     * @param Mensaje Texto que se desea escribir en el Log.
     * @param Clase Clase a la que pertenece el metodo el cual inicia el proceso de registro del Log.
     * @param Metodo Metodo desde que se manda a llamar el Log.
     * @param fecha fecha y hora de la escritura del Log.
     */
    private void writeBD(NivelLog logtemporal, String Mensaje, String Clase, String Metodo, String fecha) {
                try{

                    if(log==null){
                        log= new LogsJBDB();
                    }
                    //Asignamos los valores a almacenar
                    log.getNivelLog().setValor(logtemporal.name());
                    log.getTexto().setValor(Mensaje);
                    log.getClase().setValor(Clase);
                    log.getMetodo().setValor(Metodo);
                    log.getFecha().setValor(fecha);
                    //Guardamos el modelo
                    log.save();
                    log.waitOperationComplete();
                    log.setModelExist(false);
                    //System.out.println("Guardo el registro en BD's: ");
                }catch (Exception e) {
                    com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo encargado d crear el objeto que escribira" +
                            "el Log en BD's");
                    com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                    com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                    com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                    com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                }
    }


    /**
     * Metodo encargado de almacenar los Logs en Bd's
     * @param logtemporal Nivel de Log especificado para el texto que se escribira.
     * @param Mensaje Texto que se desea escribir en el Log.
     * @param Clase Clase a la que pertenece el metodo el cual inicia el proceso de registro del Log.
     * @param Metodo Metodo desde que se manda a llamar el Log.
     * @param fecha fecha y hora de la escritura del Log.
     */
    private void writeRestAPI(NivelLog logtemporal, String Mensaje, String Clase, String Metodo, String fecha) {
        try {
            LogsJBDB log = new LogsJBDB(false);
            JsonObject json=new JsonObject();
            json.addProperty("nivelLog", logtemporal.name());
            json.addProperty("texto", Mensaje);
            json.addProperty("clase", Clase);
            json.addProperty("metodo", Metodo);
            json.addProperty("fecha", fecha);

            //System.out.println("Tipo de autenticación seteada en RestApi: "+LogsJB.getTipeautentication());
            RestApi rest= new RestApi(LogsJB.getTipeautentication(), contentType.JSON);
            System.out.println("Json a envíar: "+json.toString());
            String respuesta=rest.Post(getUrlLogRest(), json.toString(), getKeyLogRest());
            requestCode codigorespuesta=rest.getCodigorespuesta();
            System.out.println("Respuesta: "+respuesta);
            System.out.println("Codigo de Respuesta: "+codigorespuesta);

            if((codigorespuesta==requestCode.CREATED)||(codigorespuesta==requestCode.OK)){
                String separador = System.getProperty("file.separator");
                String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                        "Logs" +
                        separador +
                        "LogsJB.db");
                //Verificara si hay Logs por obtener y envíar al servidor
                log.setDataBaseType(DataBase.SQLite);
                log.setBD(BDSqlite);
                List<LogsJBDB> logs = new ArrayList<LogsJBDB>();
                logs=log.getAll();
                log.waitOperationComplete();
                if(!logs.isEmpty()){
                    Iterator<LogsJBDB> iteradorLogs=logs.iterator();
                    while(iteradorLogs.hasNext()){
                        LogsJBDB temp= iteradorLogs.next();
                        //Por cada objeto creo un Json y lo envío
                        JsonObject jsontemp=new JsonObject();
                        jsontemp.addProperty("nivelLog", temp.getNivelLog().getValor());
                        jsontemp.addProperty("texto", temp.getTexto().getValor());
                        jsontemp.addProperty("clase", temp.getClase().getValor());
                        jsontemp.addProperty("metodo", temp.getTexto().getValor());
                        jsontemp.addProperty("fecha", temp.getFecha().getValor());
                        RestApi resttemp= new RestApi(LogsJB.getTipeautentication(), contentType.JSON);
                        resttemp.Post(getUrlLogRest(), jsontemp.toString(), getKeyLogRest());
                        requestCode codigorespuestatemp=resttemp.getCodigorespuesta();
                        //Si logro envíar el Log, elimina el modelo en Bd's
                        if((codigorespuestatemp==requestCode.CREATED)||(codigorespuestatemp==requestCode.OK)){
                            temp.delete();
                            temp.waitOperationComplete();
                            iteradorLogs.remove();
                        }
                    }
                }
            }else{
                //Si el RestApi no esta habilitado, almacenara temporalmente en BD's el Log
                String separador = System.getProperty("file.separator");
                String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                        "Logs" +
                        separador +
                        "LogsJB.db");
                log.setDataBaseType(DataBase.SQLite);
                log.setBD(BDSqlite);
                if(!LogsJB.getTableDBExists()){
                    System.out.println("Creara la tabla: ");
                    //Creamos el modelo con las caracteristicas de conexión de la Maquina Virtual
                    try {
                        if(log.crateTable()){
                            LogsJB.setTableDBExists(true);
                        }else{
                            LogsJB.setTableDBExists(true);
                        }
                        System.out.println("Creo la tabla: ");
                    }catch (Exception e) {
                        com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo encargado de crear " +
                                "la tabla de Log en BD's");
                        com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                        com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                        com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                        com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                    }
                }
                //Asignamos los valores a almacenar
                log.getNivelLog().setValor(logtemporal.name());
                log.getTexto().setValor(Mensaje);
                log.getClase().setValor(Clase);
                log.getMetodo().setValor(Metodo);
                log.getFecha().setValor(fecha);
                //Guardamos el modelo
                log.save();
                log.waitOperationComplete();
                log.setModelExist(false);
            }
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo encargado de" +
                    " envíar el Log al RestApi: "+getUrlLogRest());
            com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Obtiene la bandera que indica si actualmente esta trabajando la clase Execute o si ya no esta trabajando
     * @return True si esta libre, false si actualmente esta trabajando
     */
    protected synchronized Boolean getTaskisReady() {
        return TaskisReady;
    }

    /**
     * Setea la bandera que indica si actualmente esta trabajando la clase Execute o si ya no esta trabajando
     * @param taskisReady True si esta libre, false si actualmente esta trabajando
     */
    protected synchronized void setTaskisReady(Boolean taskisReady) {
        TaskisReady = taskisReady;
    }
}
