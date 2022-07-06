package com.josebran.LogsJB.Executores;

import com.josebran.LogsJB.Numeracion.NivelLog;

import static com.josebran.LogsJB.Methods.writeLog;

class ExecutorTxt extends Thread{


    private String mensaje;
    private NivelLog nivellog;


    public void run(){
        //Ejecuta la escritura en el archivo Log
        writeLog(getNivellog(), getMensaje());

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
