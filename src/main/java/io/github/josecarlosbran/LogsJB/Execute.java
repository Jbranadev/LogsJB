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
import io.github.josecarlosbran.JBRestAPI.RestApi;
import io.github.josecarlosbran.JBSqlLite.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlLite.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlLite.Exceptions.ValorUndefined;
import io.github.josecarlosbran.LogsJB.Mensajes.ListaMensajes;
import io.github.josecarlosbran.LogsJB.Mensajes.MensajeWrite;
import io.github.josecarlosbran.LogsJB.Numeracion.NivelLog;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.josecarlosbran.LogsJB.MethodsTxt.*;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase encargada de recuperar los mensajes de la lista compartida por el Proceso Principal
 * e iniciar un SubProceso encargado de leer los mensajes de la lista y escribirlos en el LogTxt.
 */
class Execute {

    private static Boolean writeTxt=true;

    private static Boolean writeDB=false;

    private static Boolean tableDBExists=false;



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
    }

    /***
     * Proporciona la instancia de la clase encargada de ejecutar las acciónes en segundo Plano.
     * @return Retorna la instancia de la Clase Execute, que estara trabajando las peticiones de la aplicación
     * en segundo plano.
     */
    protected static Execute getInstance() {
        return instance;
    }


    /***
     * Proporciona el acceso a la lista que sirve como cola de las peticiones
     * @return Retorna una lista de MensajeWrite, la cual lleva la información que se desea registrar en los Logs
     */
    protected static ListaMensajes getListado() {
        return listado;
    }

    /**
     * Obtiene la bandera que índica a LogsJB si se escribirá el log en el archivo TXT
     * @return True si se desea escribir el Log en el archivo TXT, False si se desea
     *         que no se escriba el Log en el archivo TXT
     */
    public static Boolean getWriteTxt() {
        return writeTxt;
    }

    /**
     * Setea la bandera que índica a LogsJB si se escribirá el log en el archivo TXT
     * @param writeTxt True si se desea escribir el Log en el archivo TXT, False si se desea
     *                 que no se escriba el Log en el archivo TXT
     */
    public static void setWriteTxt(Boolean writeTxt) {
        try{
            Field field = io.github.josecarlosbran.LogsJB.Execute.class.getDeclaredField("writeTxt");
            field.setAccessible(true);
            field.set(null, writeTxt);
            System.setProperty("writeTxt", String.valueOf(writeTxt));
        }catch (Exception e){
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear " +
                    "si se escribirá el Log en Txt: " +writeTxt);
        }
        //Execute.writeTxt = writeTxt;
    }

    /**
     * Obtiene la bandera que índica a LogsJB si se escribirá el log en BD's
     * @return True si se desea escribir el Log en BD's, False si se desea
     *         que no se escriba el Log en BD's
     */
    public static Boolean getWriteDB() {
        return writeDB;
    }

    /**
     * Setea la bandera que índica a LogsJB si se escribirá el log en BD's
     * @param writeDB True si se desea escribir el Log en BD's, False si se desea
     *                que no se escriba el Log en BD's
     */
    public static void setWriteDB(Boolean writeDB) {
        try{
            Field field = io.github.josecarlosbran.LogsJB.Execute.class.getDeclaredField("writeDB");
            field.setAccessible(true);
            field.set(null, writeDB);
            System.setProperty("writeDB", String.valueOf(writeDB));
        }catch (Exception e){
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear " +
                    "si se escribirá el Log en BD's: " +writeDB);
        }
        //Execute.writeDB = writeDB;
    }

    /**
     * Obtiene la bandera que índica a LogsJB si la tabla en BD's correspondiente a los Logs existe
     * en BD's
     * @return True si la tabla existe en BD's, False si la tabla no existe.
     */
    public static Boolean getTableDBExists() {
        return tableDBExists;
    }

    /**
     * Setea la bandera que índica a LogsJB si la tabla en BD's correspondiente a los Logs existe
     * @param tableDBExists True si la tabla existe en BD's, False si la tabla no existe.
     */
    public static void setTableDBExists(Boolean tableDBExists) {
        try{
            Field field = io.github.josecarlosbran.LogsJB.Execute.class.getDeclaredField("tableDBExists");
            field.setAccessible(true);
            field.set(null, tableDBExists);
            System.setProperty("writeDB", String.valueOf(tableDBExists));
        }catch (Exception e){
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear " +
                    "si la tabla de los Logs existe en BD's: " +tableDBExists);
        }
        //Execute.tableDBExists = tableDBExists;
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
            Runnable EscritorPrincipal = () -> {
                String temporal = "";
                boolean band = true;
                while (band) {
                    NivelLog nivel = LogsJB.getGradeLog();
                    //String Mensaje=Execute.getInstance().getTexto();
                    //NivelLog logtemporal=Execute.getInstance().getNivelLog();
                    MensajeWrite mensajetemp = null;
                    mensajetemp = getListado().getDato();
                    //System.out.println("Mensaje en Execute: "+mensajetemp.getTexto()+" "+mensajetemp.getNivelLog());
                    if (Objects.isNull(mensajetemp)) {
                        band = false;
                        break;
                        //return;
                    }
                    String Mensaje = mensajetemp.getTexto();
                    NivelLog logtemporal = mensajetemp.getNivelLog();
                    String Clase = mensajetemp.getClase();
                    String Metodo = mensajetemp.getMetodo();
                    String fecha = mensajetemp.getFecha();

                    //System.out.println("NivelLog definido: "+nivelaplicación);
                    //System.out.println("NivelLog temporal: "+intniveltemporal);
                    //System.out.println("Cantidad de mensajes Por limpiar: "+getListaTxt().getSize());
                    //Verifica que el nivel de Log a escribir sea igual o mayor al nivel predefinido.
                    /*if(logtemporal.getGradeLog()>=nivel.getGradeLog()){

                    }*/
                    if (!temporal.equals(Mensaje)) {
                        //verificarSizeFichero();
                        //writeLog(logtemporal, Mensaje, Clase, Metodo);
                        //Decide si escribe o no en el TXT
                        if(Execute.getWriteTxt()){
                            writeTXT(logtemporal, Mensaje, Clase, Metodo, fecha);
                        }
                        //Decide si escribe o no en la BD's
                        if(Execute.getWriteDB()){
                            if(!Execute.getTableDBExists()){
                                //Creamos el modelo con las caracteristicas de conexión de la Maquina Virtual
                                try {
                                    LogsJBDB log = new LogsJBDB();
                                    if(log.crateTable()){
                                        Execute.setTableDBExists(true);
                                    }else{
                                        Execute.setTableDBExists(true);
                                    }
                                }catch (Exception e) {
                                    com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo encargado de crear " +
                                            "la tabla de Log en BD's");
                                    com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : " + e.getClass());
                                    com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : " + e.getCause());
                                    com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
                                    com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
                                }
                            }
                            writeBD(logtemporal, Mensaje, Clase, Metodo, fecha);
                        }
                    } else {

                    }
                    temporal = Mensaje;


                    //System.out.println("Cantidad de mensajes Por limpiar: "+getListado().getSize());
                    if (getListado().getSize() == 0) {
                        band = false;
                        break;
                    }
                }
                return;
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


    /****
     * Metodo encargado de crear los subprocesos encargados de escribir los logs en los TXT.
     * Crea un hilo que verifica el tamaño del archivo y otro que se encarga de escribir en el archivo.
     * @param logtemporal Nivel de Log especificado para el texto que se escribira.
     * @param Mensaje Texto que se desea escribir en el Log.
     * @param Clase Clase a la que pertenece el metodo el cual inicia el proceso de registro del Log.
     * @param Metodo Metodo desde que se manda a llamar el Log.
     * @param fecha fecha y hora de la escritura del Log.
     */
    private void writeTXT(NivelLog logtemporal, String Mensaje, String Clase, String Metodo, String fecha) {
        try {
            //System.out.println("El valor es igual o mayor al nivel de la aplicación: "+intniveltemporal);
            //System.out.println("NivelLog 2: "+logtemporal);
            //System.out.println("Texto 2: "+Mensaje);
            //System.out.println("Temporal: "+temporal);

            Runnable verificarTxt = () -> {
                //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
                //Verifica el tamaño del archivo
                verificarSizeFichero();
            };
            ExecutorService verificarsize = Executors.newFixedThreadPool(1);
            verificarsize.submit(verificarTxt);
            verificarsize.shutdown();

            Runnable writeTxt = () -> {
                //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
                //Ejecuta la escritura en el archivo Log
                writeLog(logtemporal, Mensaje, Clase, Metodo, fecha);

            };
            ExecutorService executorTxt = Executors.newFixedThreadPool(1);
            executorTxt.submit(writeTxt);
            executorTxt.shutdown();
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo encargado de crear los subprocesos encargados de escribir los logs en los TXT.\n" +
                    "     * Crea un hilo que verifica el tamaño del archivo y otro que se encarga de escribir en el archivo.");
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
        try {
            //System.out.println("El valor es igual o mayor al nivel de la aplicación: "+intniveltemporal);
            //System.out.println("NivelLog 2: "+logtemporal);
            //System.out.println("Texto 2: "+Mensaje);
            //System.out.println("Temporal: "+temporal);


            //Creamos el modelo con las caracteristicas de conexión de la Maquina Virtual
            LogsJBDB log = new LogsJBDB();

            //Asignamos los valores a almacenar
            log.getNivelLog().setValor(logtemporal.name());
            log.getTexto().setValor(Mensaje);
            log.getClase().setValor(Clase);
            log.getMetodo().setValor(Metodo);
            log.getFecha().setValor(fecha);

            //Guardamos el modelo
            log.save();


        } catch (Exception e) {
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
            //System.out.println("El valor es igual o mayor al nivel de la aplicación: "+intniveltemporal);
            //System.out.println("NivelLog 2: "+logtemporal);
            //System.out.println("Texto 2: "+Mensaje);
            //System.out.println("Temporal: "+temporal);

            JsonObject json=new JsonObject();
            json.addProperty("NivelLog", logtemporal.name());
            json.addProperty("Texto", Mensaje);
            json.addProperty("Clase", Clase);
            json.addProperty("Metodo", Metodo);
            json.addProperty("Fecha", fecha);

            RestApi rest= new RestApi(LogsJB.getTypeAutentication(), contentType.JSON);

            rest.Post("url", json.getAsString(), "Credenciales");





        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo encargado d crear el objeto que escribira" +
                    "el Log en BD's");
            com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }
}
