package com.josebran.LogsJB;

import com.josebran.LogsJB.Executores.Execute;
import com.josebran.LogsJB.Numeracion.NivelLog;

import java.lang.reflect.Field;

public  class LogsJB extends Methods{

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


    public static void info(String Texto){
        executor(NivelLog.INFO, Texto);
    }

    public static void debug(String Texto){
        executor(NivelLog.DEBUG, Texto);
    }

    public static void trace(String Texto){
        executor(NivelLog.TRACE, Texto);
    }
    public static void warning(String Texto){
        executor(NivelLog.WARNING, Texto);
    }
    public static void fatal(String Texto){
        executor(NivelLog.FATAL, Texto);
    }
    public static void error(String Texto){
        executor(NivelLog.ERROR, Texto);
    }


    public static String getRuta() {
        return Methods.getRuta();
    }

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
