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


import io.github.josecarlosbran.JBRestAPI.Enumeraciones.requestCode;
import io.github.josecarlosbran.JBRestAPI.JBRestAPI;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.LogsJB.Numeracion.NivelLog;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.exception.ExceptionUtils;

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

    private JBRestAPI clienteJB;

    private LogsJBDB log;

    private Log logPojo = new Log();

    private Boolean TaskisReady = true;


    /***
     * Lista que funciona como la cola de peticiones que llegan al Ejecutor
     */
    private static ListaMensajes listado = new ListaMensajes();


    /***
     * Se utiliza el patron Singleton, para asegurarnos que sea una unica instancia la que se encargue de
     * Llevar el control de los Logs
     */
    private static Execute instance = null;

    /**
     * Ejecutor de Tareas asincronas
     */
    private ExecutorService executorPrincipal = Executors.newCachedThreadPool();

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

    /**
     * Recuperara las propiedades de LogsJB seteadas en las propiedades del sistema
     */
    protected void getLogsJBProperties() {
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
        if (instance == null) {
            instance = new Execute();
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
        writePrincipal();
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
            if (LogsJB.getWriteDB()) {
                if (!LogsJB.getTableDBExists()) {
                    com.josebran.LogsJB.LogsJB.debug("Creara la tabla de Logs: ");
                    //Creamos el modelo con las caracteristicas de conexión de la Maquina Virtual
                    LogsJBDB log = new LogsJBDB();
                    log.createTable();
                    LogsJB.setTableDBExists(true);
                    com.josebran.LogsJB.LogsJB.debug("Creo la tabla: "+log.getTableName());
                }
            }
            Runnable EscritorPrincipal = () -> {
                String temporal = "";
                boolean band = true;
                Integer i = 0;
                while (band) {
                    if (i > 5000) {
                        verificarSizeFichero();
                        i = 0;
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

                    if (LogsJB.getWriteTxt()) {
                        writeLog(logtemporal, Mensaje, Clase, Metodo, fecha);
                    }
                    //Decide si escribe o no en la BD's
                    if (LogsJB.getWriteDB()) {
                        writeBD(logtemporal, Mensaje, Clase, Metodo, fecha);
                    }
                    //Decide si envía los Logs al RestAPI
                    if (LogsJB.getWriteRestAPI()) {
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
            this.executorPrincipal.submit(EscritorPrincipal);
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo Escritor principal, es el que maneja la logica de la aplicación la cual decide si el log se almacena en una BD's,\n" +
                    "     * un Txt Ó si se envía a un RestAPI." + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    /**
     * Metodo encargado de almacenar los Logs en Bd's
     *
     * @param logtemporal Nivel de Log especificado para el texto que se escribira.
     * @param Mensaje     Texto que se desea escribir en el Log.
     * @param Clase       Clase a la que pertenece el metodo el cual inicia el proceso de registro del Log.
     * @param Metodo      Metodo desde que se manda a llamar el Log.
     * @param fecha       fecha y hora de la escritura del Log.
     */
    private void writeBD(NivelLog logtemporal, String Mensaje, String Clase, String Metodo, String fecha) {
        try {

            if (log == null) {
                log = new LogsJBDB();
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
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo encargado d crear el objeto que escribira" +
                    "el Log en BD's" + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    /**
     * Metodo encargado de almacenar los Logs en Bd's
     *
     * @param logtemporal Nivel de Log especificado para el texto que se escribira.
     * @param Mensaje     Texto que se desea escribir en el Log.
     * @param Clase       Clase a la que pertenece el metodo el cual inicia el proceso de registro del Log.
     * @param Metodo      Metodo desde que se manda a llamar el Log.
     * @param fecha       fecha y hora de la escritura del Log.
     */
    private void writeRestAPI(NivelLog logtemporal, String Mensaje, String Clase, String Metodo, String fecha) {
        try {
            if (Objects.isNull(this.clienteJB)) {
                MultivaluedMap<String, Object> myHeaders = new MultivaluedHashMap<>();
                myHeaders.add("Authorization", LogsJB.getTipeautentication() + getKeyLogRest());
                this.clienteJB = JBRestAPI.builder().Url(getUrlLogRest()).
                        MediaType(MediaType.APPLICATION_JSON_TYPE).
                        Headers(myHeaders).newClient();
            }
            this.logPojo.setNivelLog(logtemporal.name());
            this.logPojo.setTexto(Mensaje);
            this.logPojo.setClase(Clase);
            this.logPojo.setMetodo(Metodo);
            this.logPojo.setFecha(fecha);
            Response respuesta = this.clienteJB.post(Entity.entity(this.logPojo, MediaType.APPLICATION_JSON_TYPE));
            com.josebran.LogsJB.LogsJB.info("Resultado de enviar el Log al Endpoint es: " + respuesta);
            int codigorespuesta = respuesta.getStatus();
            if ((codigorespuesta == requestCode.CREATED.getCodigo()) || (codigorespuesta == requestCode.OK.getCodigo())) {
                LogsJBDB log = new LogsJBDB(false);
                String separador = System.getProperty("file.separator");
                String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                        "Logs" +
                        separador +
                        "LogsJB.db");
                //Verificara si hay Logs por obtener y envíar al servidor
                log.setDataBaseType(DataBase.SQLite);
                log.setBD(BDSqlite);
                List<LogsJBDB> logs = new ArrayList<LogsJBDB>();
                logs = log.getAll();
                log.waitOperationComplete();
                if (!logs.isEmpty()) {
                    Iterator<LogsJBDB> iteradorLogs = logs.iterator();
                    while (iteradorLogs.hasNext()) {
                        LogsJBDB temp = iteradorLogs.next();
                        temp.llenarControlador(this.logPojo, temp);
                        this.logPojo.setId(null);
                        Response respuestatemp = this.clienteJB.post(Entity.entity(this.logPojo, MediaType.APPLICATION_JSON_TYPE));
                        com.josebran.LogsJB.LogsJB.info("Resultado de enviar el Log al Endpoint es: " + respuesta);
                        //Si logro envíar el Log, elimina el modelo en Bd's
                        int codigorespuestatemp = respuestatemp.getStatus();
                        if ((codigorespuestatemp == requestCode.CREATED.getCodigo()) || (codigorespuestatemp == requestCode.OK.getCodigo())) {
                            temp.delete();
                            temp.waitOperationComplete();
                            iteradorLogs.remove();
                        } else {
                            this.clienteJB = null;
                        }
                    }
                }
            } else {
                this.clienteJB = null;
                LogsJBDB log = new LogsJBDB(false);
                //Si el RestApi no esta habilitado, almacenara temporalmente en BD's el Log
                String separador = System.getProperty("file.separator");
                String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                        "Logs" +
                        separador +
                        "LogsJB.db");
                log.setDataBaseType(DataBase.SQLite);
                log.setBD(BDSqlite);
                if (!LogsJB.getTableDBExists()) {
                    System.out.println("Creara la tabla: ");
                    //Creamos el modelo con las caracteristicas de conexión de la Maquina Virtual
                    try {
                        if (log.createTable()) {
                            LogsJB.setTableDBExists(true);
                        } else {
                            LogsJB.setTableDBExists(true);
                        }
                        System.out.println("Creo la tabla: ");
                    } catch (Exception e) {
                        com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo encargado de crear " +
                                "la tabla de Log en BD's" + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));

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
                    " envíar el Log al RestApi: " + getUrlLogRest() + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));

        }
    }

    /**
     * Obtiene la bandera que indica si actualmente esta trabajando la clase Execute o si ya no esta trabajando
     *
     * @return True si esta libre, false si actualmente esta trabajando
     */
    protected synchronized Boolean getTaskisReady() {
        return TaskisReady;
    }

    /**
     * Setea la bandera que indica si actualmente esta trabajando la clase Execute o si ya no esta trabajando
     *
     * @param taskisReady True si esta libre, false si actualmente esta trabajando
     */
    protected synchronized void setTaskisReady(Boolean taskisReady) {
        TaskisReady = taskisReady;
    }
}
