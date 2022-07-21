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


import io.github.josecarlosbran.LogsJB.Mensajes.MensajeWrite;
import io.github.josecarlosbran.LogsJB.Numeracion.NivelLog;
import io.github.josecarlosbran.LogsJB.Numeracion.SizeLog;


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
public  class LogsJB {


    /***
     * Obtiene la ruta donde se estara escribiendo el Log.
     * @return Retorna un String con la ruta del archivo .Txt donde se estara escribiendo el Log.
     */
    public static String getRuta() {
        return io.github.josecarlosbran.LogsJB.MethodsTxt.ruta;
    }

    /**
     * Setea la ruta en la cual se desea que escriba el Log.
     * @param Ruta Ruta del archivo .Txt donde se desea escribir el Log.
     */
    public static void setRuta(String Ruta) {
        try{
            Field field = io.github.josecarlosbran.LogsJB.MethodsTxt.class.getDeclaredField("ruta");
            field.setAccessible(true);
            field.set(null, Ruta);
            com.josebran.LogsJB.LogsJB.setRuta(Ruta);
            System.setProperty("RutaLog",Ruta);
        }catch (Exception e){
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear la ruta del log " +Ruta);
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
        try{
            Field field = io.github.josecarlosbran.LogsJB.MethodsTxt.class.getDeclaredField("gradeLog");
            field.setAccessible(true);
            field.set(null, GradeLog);
            com.josebran.LogsJB.LogsJB.setGradeLog(getNivelLog(GradeLog));
            System.setProperty("NivelLog",GradeLog.name());
            //Methods.metodo = metodo;
        }catch (Exception e){
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear el GradeLog de la aplicación " +GradeLog);
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
     *      * Little_Little = 125Mb,
     *      * Little = 250Mb,
     *      * Small_Medium = 500Mb,
     *      * Medium = 1,000Mb,
     *      * Small_Large = 2,000Mb,
     *      * Large = 4,000Mb.
     * El valor por defaul es Little_Little.
     */
    public static void setSizeLog(SizeLog SizeLog) {
        try{
            Field field = io.github.josecarlosbran.LogsJB.MethodsTxt.class.getDeclaredField("sizeLog");
            field.setAccessible(true);
            field.set(null, SizeLog);
            com.josebran.LogsJB.LogsJB.setSizeLog(getSizeLog(SizeLog));
            //Methods.metodo = metodo;
        }catch (Exception e){
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear el Tamaño del archivo Log " +SizeLog);
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
     * @param Usuario Usuario actual del sistema que se desea indicar al Log.
     */
    public static void setUsuario(String Usuario){
        try{
            Field field = io.github.josecarlosbran.LogsJB.MethodsTxt.class.getDeclaredField("usuario");
            field.setAccessible(true);
            field.set(null, Usuario);
            com.josebran.LogsJB.LogsJB.setUsuario(Usuario);
        }catch (Exception e){
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al tratar de setear el usuario del entorno actual "+Usuario);
            com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : "+e.getClass());
            com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : "+e.getCause());
            com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : "+e.getMessage());
            com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : "+e.getStackTrace());
        }

    }


/*
    public static void main(String[] args) {
        try{
            int archivos=0;
            File archivo = new File(getRuta());
            while(archivos<3){
                try{
                    //LogsJB.setRuta("C:/Reportes/Logs/Log.txt");
                    executor(NivelLog.DEBUG, "Ruta donde se esta escribiendo el log: "+getRuta());
                    //Thread.sleep(2);
                    debug( "Primer comentario grado Debug");
                    //Thread.sleep(2);
                    error( "Primer comentario grado Error");
                    //Thread.sleep(2);
                    fatal( "Primer comentario grado Fatal");
                    //Thread.sleep(2);
                    info( "Primer comentario grado Info");
                    //Thread.sleep(2);
                    trace( "Primer comentario grado Trace");
                    //Thread.sleep(2);
                    warning( "Primer comentario grado Warning");
                    //Thread.sleep(2);
                    debug("Jbran");
                    //Thread.sleep(2);
                    //Thread.sleep(100);
                    File carpeta = new File(archivo.getParent());
                    File[] listado;
                    listado = carpeta.listFiles();
                    archivos=listado.length;

                    //System.out.println("Cantida de archivos: "+archivos);
                    if(archivos>1){
                        return;
                    }

                    //archivos=new File(getRuta()).list().length;
                    //System.out.println("Cantida de archivos: "+archivos);
                }catch (Exception e){
                    System.out.println("Excepcion capturada en el metodo main: "+e.getMessage());
                }


            }
            System.out.println("Salio del While: "+archivos);
        }catch (Exception e){
            System.out.println("Excepcion capturada en el metodo main: "+e.getMessage());
        }


    }
*/


/**/
    public static void main(String[] args) {
    try{

        //LogsJB.debug( "Primer comentario grado Debug");
        LogsJB.setGradeLog(NivelLog.TRACE);
        //Comentario grado Trace
        trace( "Primer comentario grado Trace");
        //Comentario grado Debug
        debug( "Primer comentario grado Debug");
        //Comentario grado Info
        info( "Primer comentario grado Info");
        //Comentario grado Warning
        warning( "Primer comentario grado Warning");
        //Comentario grado Error
        error( "Primer comentario grado Error");
        //Comentario grado Fatal
        fatal( "Primer comentario grado Fatal");

        return;

    }catch (Exception e){
        System.out.println("Excepción capturada en el metodo main: "+e.getMessage());
    }
}



    /***
     * Metodo encargado de hacer la llamada al ejecutor en un hilo de ejecución aparte, para que este se encargue
     * de ejecutar los ejecutores de log's en subprocesos, diferentes al programa principal
     * @param nivelLog NivelLog del mensaje que queremos almacenar en el Log.
     * @param Texto Texto que se desea escribir en el Log.
     */
    private static void executor(NivelLog nivelLog, String Texto){
        try{
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase = null;
            String metodo = null;
            try{
                clase = elements[3].getClassName();
                metodo = elements[3].getMethodName();
            }catch (Exception ex){
                clase = elements[2].getClassName();
                metodo = elements[2].getMethodName();
            }

            if(nivelLog.getGradeLog()>= getGradeLog().getGradeLog()){
                MensajeWrite mensaje=new MensajeWrite();
                mensaje.setTexto(Texto);
                mensaje.setNivelLog(nivelLog);
                mensaje.setClase(clase);
                mensaje.setMetodo(metodo);
                mensaje.setFecha(convertir_fecha());
                //System.out.println("Agregara el dato: "+Thread.currentThread().getName());
                getListado().addDato(mensaje);
                //System.out.println("Correra el metodo run: "+Thread.currentThread().getName());
                getInstance().run();
                //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
                Thread.sleep(2);
            }
        }catch (Exception e){
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al Executor encargado de hacer la llamada al ejecutor en un hilo de ejecución aparte, para que este se encargue\n" +
                    "     * de ejecutar los ejecutores de log's en subprocesos, diferentes al programa principal");
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada en el metodo Metodo por medio del cual se llama la escritura de los logs");
            com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : "+e.getClass());
            com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : "+e.getCause());
            com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : "+e.getMessage());
            com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : "+e.getStackTrace());
        }


    }


    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Informacion.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void info(String Texto){
        executor(NivelLog.INFO, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Debug.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void debug(String Texto){
        executor(NivelLog.DEBUG, Texto);
    }
    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Trace, la cual es un seguimiento mayor a Debug.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void trace(String Texto){
        executor(NivelLog.TRACE, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Advertencia.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void warning(String Texto){
        executor(NivelLog.WARNING, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria Fatal lo cual indica un error del cual no es posible recuperarse.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void fatal(String Texto){
        executor(NivelLog.FATAL, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Error, lo cual indica que capturo un error.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void error(String Texto){
        executor(NivelLog.ERROR, Texto);
    }

    /***
     * Obtiene el equivalente entre la librería de apoyo a interno y la Libreria de ejecución normal
     * @param nivelLog NivelLog que se desea comparar y obtener
     * @return Retorna el NivelLog equivalente entre ambas librerías
     */
    protected static com.josebran.LogsJB.Numeracion.NivelLog getNivelLog(NivelLog nivelLog){
        if(nivelLog==NivelLog.TRACE)
            return com.josebran.LogsJB.Numeracion.NivelLog.TRACE;
        if(nivelLog==NivelLog.DEBUG)
            return com.josebran.LogsJB.Numeracion.NivelLog.DEBUG;
        if(nivelLog==NivelLog.INFO)
            return com.josebran.LogsJB.Numeracion.NivelLog.INFO;
        if(nivelLog==NivelLog.WARNING)
            return com.josebran.LogsJB.Numeracion.NivelLog.WARNING;
        if(nivelLog==NivelLog.ERROR)
            return com.josebran.LogsJB.Numeracion.NivelLog.ERROR;
        if(nivelLog==NivelLog.FATAL)
            return com.josebran.LogsJB.Numeracion.NivelLog.FATAL;
        return com.josebran.LogsJB.Numeracion.NivelLog.INFO;
    }

    /***
     * Obtiene el equivalente entre la librería de apoyo a interno y la Libreria de ejecución normal
     * @param sizeLog SizeLog que se desea comparar y obtener
     * @return Retorna el SizeLog equivalente entre ambas librerías
     */
    protected static com.josebran.LogsJB.Numeracion.SizeLog getSizeLog(SizeLog sizeLog){
        if(sizeLog==SizeLog.Little_Little)
            return com.josebran.LogsJB.Numeracion.SizeLog.Little_Little;
        if(sizeLog==SizeLog.Little)
            return com.josebran.LogsJB.Numeracion.SizeLog.Little;
        if(sizeLog==SizeLog.Small_Medium)
            return com.josebran.LogsJB.Numeracion.SizeLog.Small_Medium;
        if(sizeLog==SizeLog.Medium)
            return com.josebran.LogsJB.Numeracion.SizeLog.Medium;
        if(sizeLog==SizeLog.Small_Large)
            return com.josebran.LogsJB.Numeracion.SizeLog.Small_Large;
        if(sizeLog==SizeLog.Large)
            return com.josebran.LogsJB.Numeracion.SizeLog.Large;
        return com.josebran.LogsJB.Numeracion.SizeLog.Little_Little;
    }

}
