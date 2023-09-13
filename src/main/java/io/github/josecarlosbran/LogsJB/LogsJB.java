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


import io.github.josecarlosbran.JBRestAPI.Enumeraciones.typeAutentication;
import io.github.josecarlosbran.LogsJB.Numeracion.LogsJBProperties;
import io.github.josecarlosbran.LogsJB.Numeracion.NivelLog;
import io.github.josecarlosbran.LogsJB.Numeracion.SizeLog;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Field;

import static io.github.josecarlosbran.LogsJB.Execute.getInstance;
import static io.github.josecarlosbran.LogsJB.Execute.getListado;
import static io.github.josecarlosbran.LogsJB.MethodsTxt.convertir_fecha;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase que proporciona los metodos de configuración y entrada para la escritura de los Logs en segundo plano.
 * Los metodos de configuración son:
 *      setRuta(Ruta);
 *      setGradeLog(NivelLog);
 *      setSizeLog(SizeLog);
 *
 * Los metodos de entrada son:
 *      trace(Texto);
 *      debug(Texto);
 *      info(Texto);
 *      warning(Texto);
 *      fatal(Texto);
 *      error(Texto);
 */
public class LogsJB {

    private static typeAutentication tipeautentication = typeAutentication.BEARER;

    private static Boolean writeTxt = true;

    private static Boolean writeDB = false;

    private static Boolean writeRestAPI = false;


    private static String keyLogRest = "";

    private static String urlLogRest = "";


    private static Boolean tableDBExists = false;

    /**
     * Constructor por default de la clase
     */
    protected LogsJB() {
    }

    /***
     * Obtiene la ruta donde se estara escribiendo el Log.
     * @return Retorna un String con la ruta del archivo .Txt donde se estara escribiendo el Log.
     */
    public static String getRuta() {
        return io.github.josecarlosbran.LogsJB.MethodsTxt.ruta;
    }

    /**
     * Setea la ruta en la cual se desea que escriba el Log.
     *
     * @param Ruta Ruta del archivo .Txt donde se desea escribir el Log.
     */
    public static void setRuta(String Ruta) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.MethodsTxt.class.getDeclaredField("ruta");
            field.setAccessible(true);
            field.set(null, Ruta);
            System.setProperty(LogsJBProperties.LogsJBRutaLog.getProperty(), Ruta);
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear la ruta del log " + Ruta
                    + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    /***
     * Obtiene el grado del log, sobre el cual se estara realizando el seguimiento de los mensajes que se
     * escriben en las bitacoras de Log de la aplicación actual.
     * Trace = 200,
     * Debug = 400,
     * Info = 500,
     * Warning = 600,
     * Error = 800,
     * Fatal = 1000.
     * @return Retorna un NivelLog el cual expresa el Nivel Log desde el cual se estara reportando al Log y sus
     * superiores, El valor por defaul es Info.
     */
    public static NivelLog getGradeLog() {
        return io.github.josecarlosbran.LogsJB.MethodsTxt.gradeLog;
    }


    /***
     * Setea el NivelLog desde el cual deseamos se escriba en el Log de la aplicación actual.
     * @param GradeLog Nivel Log desde el cual hacía arriba en la jerarquia de logs, deseamos se reporten
     *      * Trace = 200,
     *      * Debug = 400,
     *      * Info = 500,
     *      * Warning = 600,
     *      * Error = 800,
     *      * Fatal = 1000.
     * El valor por defaul es Info. Lo cual hace que se reporten los Logs de grado Info, Warning, Error y Fatal.
     */
    public static void setGradeLog(NivelLog GradeLog) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.MethodsTxt.class.getDeclaredField("gradeLog");
            field.setAccessible(true);
            field.set(null, GradeLog);
            System.setProperty(LogsJBProperties.LogsJBNivelLog.getProperty(), GradeLog.name());
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear el GradeLog de la aplicación " + GradeLog
                    + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /****
     * Obtiene el tamaño maximo actual definido para el fichero de Log sobre el cual se estara escribiendo.
     * @return Retorna un SizeLog que representa el tamaño configurado para el archivo log de la aplicación,
     * El valor por defaul es Little_Little.
     */
    public static SizeLog getSizeLog() {
        return io.github.josecarlosbran.LogsJB.MethodsTxt.sizeLog;
    }

    /***
     * Setea el tamaño maximo para el archivo Log de la aplicación actual.
     * @param SizeLog Tamaño maximo del archivo sobre el cual se estara escribiendo el Log.
     *      * Little_Little = 25Mb,
     *      * Little = 50Mb,
     *      * Small_Medium = 100Mb,
     *      * Medium = 150Mb,
     *      * Small_Large = 250Mb,
     *      * Large = 500Mb.
     * El valor por defaul es Little_Little.
     */
    public static void setSizeLog(SizeLog SizeLog) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.MethodsTxt.class.getDeclaredField("sizeLog");
            field.setAccessible(true);
            field.set(null, SizeLog);
            System.setProperty(LogsJBProperties.LogsJBSizeLog.getProperty(), SizeLog.name());
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear el Tamaño del archivo Log " + SizeLog
                    + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    /***
     * Obtiene el usuario del sistema sobre el cual corre la aplicación
     * @return Retorna un String con el nombre del usuario actual.
     */
    public static String getUsuario() {
        return io.github.josecarlosbran.LogsJB.MethodsTxt.usuario;
    }

    /***
     * Setea el nombre del usuario del sistema sobre el cual corre la aplicación
     * @param Usuario Usuario actual del sistema que se desea índicar al Log.
     */
    public static void setUsuario(String Usuario) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.MethodsTxt.class.getDeclaredField("usuario");
            field.setAccessible(true);
            field.set(null, Usuario);
            com.josebran.LogsJB.LogsJB.setUsuario(Usuario);
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear el usuario del entorno actual "
                    + Usuario + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }

    }


    /**
     * Obtiene la bandera que indica si no existe alguna tarea pendiente de realizar por parte LogsJB
     *
     * @return True si la LogsJB se encuentra libre, si esta ocupado retorna False
     */
    public static Boolean getTaskIsReady() {
        return io.github.josecarlosbran.LogsJB.Execute.getInstance().getTaskisReady();
    }

    /**
     * Espera que se termine de ejecutar los trabajos que esta realizando el Log
     */
    public static void waitForOperationComplete() {
        while (!io.github.josecarlosbran.LogsJB.Execute.getInstance().getTaskisReady()) {

        }
        com.josebran.LogsJB.LogsJB.info("Completo de escribir los Logs");
    }


    /***
     * Metodo encargado de hacer la llamada al ejecutor en un hilo de ejecución aparte, para que este se encargue
     * de ejecutar los ejecutores de log's en subprocesos, diferentes al programa principal
     * @param nivelLog NivelLog del mensaje que queremos almacenar en el Log.
     * @param Texto Texto que se desea escribir en el Log.
     */
    private static void executor(NivelLog nivelLog, String Texto) {
        try {
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase = null;
            String metodo = null;
            try {
                clase = elements[3].getClassName();
                metodo = elements[3].getMethodName() + " => " + elements[3].getLineNumber();
            } catch (Exception ex) {
                clase = elements[2].getClassName();
                metodo = elements[2].getMethodName() + " => " + elements[2].getLineNumber();
            }

            if (nivelLog.getGradeLog() >= getGradeLog().getGradeLog()) {
                MensajeWrite mensaje = new MensajeWrite();
                mensaje.setTexto(Texto);
                mensaje.setNivelLog(nivelLog);
                mensaje.setClase(clase);
                mensaje.setMetodo(metodo);
                mensaje.setFecha(convertir_fecha());
                //System.out.println("Agregara el dato: "+Thread.currentThread().getName());
                getListado().addDato(mensaje);
                //System.out.println("Correra el metodo run: "+Thread.currentThread().getName());
                if (getInstance().getTaskisReady()) {
                    getInstance().run();
                }
                //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
                //Thread.sleep(2);
            }
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al Executor encargado de hacer la llamada al ejecutor en un hilo de ejecución aparte, para que este se encargue\n" +
                    "     * de ejecutar los ejecutores de log's en subprocesos, diferentes al programa principal"
                    + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    /**
     * Recuperara las propiedades de LogsJB seteadas en las propiedades del sistema
     */
    public static void getLogsJBProperties() {
        getInstance().getLogsJBProperties();
    }


    /***
     * Escribe en el Log el mensaje especificado índicando que pertenece a la categoria de Informacion.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void info(String Texto) {
        executor(NivelLog.INFO, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado índicando que pertenece a la categoria de Debug.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void debug(String Texto) {
        executor(NivelLog.DEBUG, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado índicando que pertenece a la categoria de Trace, la cual es un seguimiento mayor a Debug.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void trace(String Texto) {
        executor(NivelLog.TRACE, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado índicando que pertenece a la categoria de Advertencia.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void warning(String Texto) {
        executor(NivelLog.WARNING, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado índicando que pertenece a la categoria Fatal lo cual índica un error del cual no es posible recuperarse.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void fatal(String Texto) {
        executor(NivelLog.FATAL, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado índicando que pertenece a la categoria de Error, lo cual índica que capturo un error.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void error(String Texto) {
        executor(NivelLog.ERROR, Texto);
    }


    /**
     * Obtiene la bandera que índica a LogsJB si se escribirá el log en el archivo TXT
     *
     * @return True si se desea escribir el Log en el archivo TXT, False si se desea
     * que no se escriba el Log en el archivo TXT
     */
    public static Boolean getWriteTxt() {
        return writeTxt;
    }

    /**
     * Setea la bandera que índica a LogsJB si se escribirá el log en el archivo TXT
     *
     * @param writeTxt True si se desea escribir el Log en el archivo TXT, False si se desea
     *                 que no se escriba el Log en el archivo TXT
     */
    public static void setWriteTxt(Boolean writeTxt) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.LogsJB.class.getDeclaredField("writeTxt");
            field.setAccessible(true);
            field.set(null, writeTxt);
            System.setProperty(LogsJBProperties.LogsJBWriteTxt.getProperty(), String.valueOf(writeTxt));
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear " +
                    "si se escribirá el Log en Txt: " + writeTxt +
                    " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
        //Execute.writeTxt = writeTxt;
    }

    /**
     * Obtiene la bandera que índica a LogsJB si se escribirá el log en BD's
     *
     * @return True si se desea escribir el Log en BD's, False si se desea
     * que no se escriba el Log en BD's
     */
    public static Boolean getWriteDB() {
        return writeDB;
    }

    /**
     * Setea la bandera que índica a LogsJB si se escribirá el log en BD's
     *
     * @param writeDB True si se desea escribir el Log en BD's, False si se desea
     *                que no se escriba el Log en BD's
     */
    public static void setWriteDB(Boolean writeDB) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.LogsJB.class.getDeclaredField("writeDB");
            field.setAccessible(true);
            field.set(null, writeDB);
            System.setProperty(LogsJBProperties.LogsJBWriteDB.getProperty(), String.valueOf(writeDB));
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear " +
                    "si se escribirá el Log en BD's: " + writeDB +
                    " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
        //Execute.writeDB = writeDB;
    }

    /**
     * Obtiene la bandera que índica a LogsJB si se enviaran los logs a un RestAPI
     *
     * @return True si se desea envíar el Log a un RestAPI, False si se desea
     * que no se envíen
     */
    public static Boolean getWriteRestAPI() {
        return writeRestAPI;
    }

    /**
     * Setea la bandera que índica si se envíaran los logs a un RestAPI
     *
     * @param writeRestAPI True si se desea envíar el Log a un RestAPI,
     *                     False si no se desea que se envíen los Logs a un RestAPI
     */
    public static void setWriteRestAPI(Boolean writeRestAPI) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.LogsJB.class.getDeclaredField("writeRestAPI");
            field.setAccessible(true);
            field.set(null, writeRestAPI);
            System.setProperty(LogsJBProperties.LogsJBWriteRestApi.getProperty(), String.valueOf(writeRestAPI));
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear " +
                    "si se envíara el Log a un RestAPI: " + writeRestAPI +
                    " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
        //LogsJB.writeRestAPI = writeRestAPI;
    }

    /**
     * Obtiene la bandera que índica a LogsJB si la tabla en BD's correspondiente a los Logs existe
     * en BD's
     *
     * @return True si la tabla existe en BD's, False si la tabla no existe.
     */
    public static Boolean getTableDBExists() {
        return tableDBExists;
    }

    /**
     * Setea la bandera que índica a LogsJB si la tabla en BD's correspondiente a los Logs existe
     *
     * @param tableDBExists True si la tabla existe en BD's, False si la tabla no existe.
     */
    public static void setTableDBExists(Boolean tableDBExists) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.LogsJB.class.getDeclaredField("tableDBExists");
            field.setAccessible(true);
            field.set(null, tableDBExists);
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear " +
                    "si la tabla de los Logs existe en BD's: " + tableDBExists +
                    " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    /***
     * Obtiene el tipo de autenticación que se índica para consumir el RestAPI
     * @return Retorna un objeto typeAutentication con el tipo de autenticación índicada para
     * consumir el RestAPI
     */
    public static typeAutentication getTipeautentication() {
        return tipeautentication;
    }


    /***
     * Setea el tipo de autenticación que estaremos utilizando para consumir el RestAPI
     * @param tipeautentication Tipo de autenticación que acepta el RestAPI
     */
    public static void setTipeautentication(typeAutentication tipeautentication) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.LogsJB.class.getDeclaredField("tipeautentication");
            field.setAccessible(true);
            field.set(null, tipeautentication);
            System.setProperty(LogsJBProperties.LogsJBTypeAutenticatiosRest.getProperty(), String.valueOf(tipeautentication));
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear el tipo de autenticación " +
                    "para el RestAPI: " + tipeautentication +
                    " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    /**
     * Obtiene la clave con la cual se debera de autenticar para envíar los Logs a un RestAPI
     *
     * @return Clave con la cual se debera de autenticar para envíar los Logs a un RestAPI
     */
    public static String getKeyLogRest() {
        return keyLogRest;
    }

    /**
     * Setea la clave con la cual se debera de autenticar para envíar los Logs a un RestAPI
     *
     * @param keyLogRest Clave con la cual se debera de autenticar para envíar los Logs a un RestAPI
     */
    public static void setKeyLogRest(String keyLogRest) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.LogsJB.class.getDeclaredField("keyLogRest");
            field.setAccessible(true);
            field.set(null, keyLogRest);
            System.setProperty(LogsJBProperties.LogsJBKeyLogRest.getProperty(), keyLogRest);
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear la keyLogRest " +
                    "Se autenticara en el RestAPI: " + keyLogRest +
                    " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }

    }

    /**
     * Obtiene la Url del endpoint que estara escuchando las solicitudes de escribir el Log en un Servidor
     *
     * @return Url donde estara escuchando las solicitudes el servidor
     */
    public static String getUrlLogRest() {

        return urlLogRest;
    }

    /**
     * Setea la Url del endpoint que estara escuchando las solicitudes de escribir el Log en un Servidor
     *
     * @param urlLogRest Url donde estara escuchando las solicitudes el servidor
     */
    public static void setUrlLogRest(String urlLogRest) {
        try {
            Field field = io.github.josecarlosbran.LogsJB.LogsJB.class.getDeclaredField("urlLogRest");
            field.setAccessible(true);
            field.set(null, urlLogRest);
            System.setProperty(LogsJBProperties.LogsJBUrlLogRest.getProperty(), urlLogRest);
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear la keyLogRest " +
                    "Se autenticara en el RestAPI: " + urlLogRest +
                    " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
        //LogsJB.urlLogRest = urlLogRest;
    }
}
