package com.josebran.LogsJB.Executores;

import com.josebran.LogsJB.Numeracion.NivelLog;

public class Execute extends Thread{


    private String mensaje;
    private NivelLog nivellog;


    private void writeTxtLog(NivelLog nivelLog, String Texto){
        ExecutorTxt writer=new ExecutorTxt();
        writer.setMensaje(Texto);
        writer.setNivellog(nivelLog);
        writer.start();
        while(writer.getState()!= Thread.State.TERMINATED){

        }
    }

    public void run(){
        //Ejecuta las acciones asociadas a los logs en paralelo

        //Primero la escritura en el archivo Log
        writeTxtLog(getNivellog(), getMensaje());

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public NivelLog getNivellog() {
        return nivellog;
    }

    public void setNivellog(NivelLog nivellog) {
        this.nivellog = nivellog;
    }


}
