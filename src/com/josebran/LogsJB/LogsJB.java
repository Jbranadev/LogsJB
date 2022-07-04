package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;

import java.lang.reflect.Field;

public class LogsJB extends Methods{



    protected static void executor(NivelLog nivelLog, String Texto){
        try{
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase = elements[2].getClassName();
            String metodo = elements[2].getMethodName();
            Methods.setClase(clase);
            Methods.setMetodo(metodo);

            Execute writer= new Execute();
            writer.setMensaje(Texto);
            writer.setNivellog(nivelLog);
            writer.start();
            while(writer.getState()!= Thread.State.TERMINATED){

            }
        }catch (Exception e){
            System.out.println("Excepcion capturada al escribir el log del Text " +nivelLog.toString()+": "+e.getMessage());
        }


    }

    public static String getRuta() {
        return Methods.getRuta();
    }

    public static void setRuta(String Ruta) throws NoSuchFieldException, IllegalAccessException {
        Field field = Methods.class.getDeclaredField("ruta");
        field.setAccessible(true);
        field.set(null, Ruta);

    }
}
