package com.josebran.LogsJB;

import com.josebran.LogsJB.Executores.Execute;
import com.josebran.LogsJB.Numeracion.NivelLog;

import java.io.File;
import java.lang.reflect.Field;

public  class LogsJB extends Methods{

    public static void main(String[] args) throws InterruptedException {
        //Rutas de archivos
        File fichero = new File(getRuta());
        File directorio = new File(fichero.getParent());
        File[] lista = directorio.listFiles();
        int cuenta= 0;
        while(cuenta<3){
            //LogsJB.setRuta("C:/Reportes/Logs/Log.txt");
            executor(NivelLog.DEBUG, "Ruta donde se esta escribiendo el log: "+getRuta());
            debug( "Primer comentario grado Debug");
            error( "Primer comentario grado Error");
            fatal( "Primer comentario grado Fatal");
            info( "Primer comentario grado Info");
            trace( "Primer comentario grado Trace");
            warning( "Primer comentario grado Warning");
            debug("Jbran");
            Thread.sleep(750);
            lista = directorio.listFiles();
            cuenta= lista.length;
        }

        /*LogsJB.setRuta("C:/Reportes/Logs/Log.txt");
        executor(NivelLog.DEBUG, "Ruta donde se esta escribiendo el log: "+getRuta());
        debug( "Primer comentario grado Debug");
        error( "Primer comentario grado Error");
        fatal( "Primer comentario grado Fatal");
        info( "Primer comentario grado Info");
        trace( "Primer comentario grado Trace");
        warning( "Primer comentario grado Warning");
        debug("Jbran");
         */


    }

    /**/

    /***
     * Procedimiento encargado de hacer la llamada al ejecutor en un hilo de ejecución aparte, para que este se encargue
     * de ejecutar los ejecutores de log's en subprocesos, diferentes al programa principal
     * @param nivelLog NivelLog del mensaje que queremos almacenar en el Log.
     * @param Texto Texto que se desea escribir en el Log.
     */
    protected static void executor(NivelLog nivelLog, String Texto){
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
            Methods.setClase(clase);
            Methods.setMetodo(metodo);
            Execute writer= new Execute();
            writer.setMensaje(Texto);
            writer.setNivellog(nivelLog);
            writer.start();
        }catch (Exception e){
            System.out.println("Excepcion capturada al Executor encargado de llamar al proceso asincrono " +nivelLog.toString()+": "+e.getMessage());
        }


    }

    /***
     * Procedimiento encargado de hacer la llamada al ejecutor en un hilo de ejecución aparte, para que este se encargue
     * de ejecutar los ejecutores de log's en subprocesos, diferentes al programa principal
     * @param nivelLog NivelLog del mensaje que queremos almacenar en el Log.
     * @param Texto Texto que se desea escribir en el Log.
     * @param clase Clase a la que pertenece el metodo desde el que se desea escribir el Log.
     * @param metodo Metodo desde el que se llama la escritura del Log.
     */
    protected static void executor(NivelLog nivelLog, String Texto, String clase, String metodo){
        try{
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            Methods.setClase(clase);
            Methods.setMetodo(metodo);
            Execute writer= new Execute();
            writer.setMensaje(Texto);
            writer.setNivellog(nivelLog);
            writer.start();
            while(writer.getState()!= Thread.State.TERMINATED){

            }
        }catch (Exception e){
            System.out.println("Excepcion capturada al Executor encargado de llamar al proceso asincrono " +nivelLog.toString()+": "+e.getMessage());
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
     * Obtiene la ruta donde se estara escribiendo el Log.
     * @return Retorna un String con la ruta del archivo .Txt donde se estara escribiendo el Log.
     */
    public static String getRuta() {
        return Methods.getRuta();
    }

    /**
     * Setea la ruta en la cual se desea que escriba el Log.
     * @param Ruta Ruta del archivo .Txt donde se desea escribir el Log.
     */
    public static void setRuta(String Ruta) {
        try{
            Field field = Methods.class.getDeclaredField("ruta");
            field.setAccessible(true);
            field.set(null, Ruta);

        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear la ruta del log " +Ruta);
        }

    }
}
