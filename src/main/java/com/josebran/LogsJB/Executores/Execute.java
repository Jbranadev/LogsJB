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
