package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;

import static com.josebran.LogsJB.Methods.*;

public class Execute extends Thread{
    private String mensaje;
    private NivelLog nivellog;



    public void run(){
        //Ejecuta las acciones asociadas a los logs en paralelo

        //Primero la escritura en el archivo Log
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
