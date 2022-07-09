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


    private  String Texto;
    private  NivelLog nivelLog;


    private static final Execute instance = new Execute();

    public static ListaMensajesTxt getListaTxt(){
        return ExecutorTxt.getListado();
    }

    private Execute() {
        //this.setPriority(Thread.MIN_PRIORITY);
        this.start();
    }

    public static Execute getInstance() {
        return instance;
    }


    public static int getSizeExecutorTXT(){
        return ExecutorTxt.getListado().getSize();
    }



    public void run(){
        //Ejecuta la escritura en el archivo Log
        String temporal="";
        String temporal2="";
        NivelLog niveltemporal;
        while(true){
            temporal2=getTexto();
            niveltemporal=getNivelLog();
            //System.out.println("Cantidad de mensajes Por limpiar: "+getListaTxt().getSize());


            //System.out.println("NivelLog: "+niveltemporal);
            //System.out.println("Texto: "+temporal2);
            //System.out.println("Temporal: "+temporal);
            if(!temporal.equals(temporal2)){
                //System.out.println("Texto es diferente: "+temporal2);
                MensajeWrite Mensaje= new MensajeWrite();
                Mensaje.setNivelLog(niveltemporal);
                Mensaje.setTexto(temporal2);
                getListaTxt().addDato(Mensaje);
            }else{
                int tareas=getListaTxt().getSize();
                if(tareas==0){
                    this.stop();
                }
            }
            temporal=temporal2;


        }
    }

    public synchronized String getTexto() {
        notify();
        return Texto;
    }

    public synchronized void setTexto(String texto) {
        try{
            /*
            Field field = Execute.class.getDeclaredField("Texto");
            field.setAccessible(true);
            field.set(null, texto);*/
            if(getInstance().getState()!= Thread.State.RUNNABLE){
                 Execute ejecutor=Execute.getInstance();
            }
            this.Texto=texto;
            wait();

        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear el texto a escribir en el Log: " +Texto);
        }

    }

    public  NivelLog getNivelLog() {
        return nivelLog;
    }

    public  void setNivelLog(NivelLog NnivelLog) {
        try{/*
            Field field = Execute.class.getDeclaredField("nivelLog");
            field.setAccessible(true);
            field.set(null, NnivelLog);*/
            this.nivelLog= NnivelLog;

        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear el nivel del log " +NnivelLog);
        }
        //this.nivelLog = nivelLog;
    }
}
