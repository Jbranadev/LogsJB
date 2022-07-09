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

import static com.josebran.LogsJB.Methods.writeLog;

class ExecutorTxt extends Thread{

    private static ListaMensajesTxt listado=new ListaMensajesTxt();


    private static final ExecutorTxt instance = new ExecutorTxt();

    private ExecutorTxt() {
        //this.setPriority(Thread.MAX_PRIORITY);
        this.start();
    }

    public static ExecutorTxt getInstance() {
        return instance;
    }


    public static ListaMensajesTxt getListado() {
        return listado;
    }

    public static void setListado(ListaMensajesTxt listado) {
        ExecutorTxt.listado = listado;
    }


    public void run(){

        while(true){
            //Ejecuta la escritura en el archivo Log
            //System.out.println("Cantidad de mensajes ExecutorTxt: "+getListado().getSize());
            int tareas=getListado().getSize();
            if(tareas==0){
                this.stop();
            }
            MensajeWrite Mensaje= null;
            try {
                Mensaje = getListado().getDato();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String mensaje=Mensaje.getTexto();
            NivelLog nivellog=Mensaje.getNivelLog();
            writeLog(nivellog, mensaje);


        }
    }
}
