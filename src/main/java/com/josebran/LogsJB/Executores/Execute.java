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


import com.josebran.LogsJB.LogsJB;
import com.josebran.LogsJB.Numeracion.NivelLog;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.josebran.LogsJB.Methods.verificarSizeFichero;
import static com.josebran.LogsJB.Methods.writeLog;


public class Execute {


    private  String Texto;
    private  NivelLog nivelLog;



    private static Execute instance = new Execute();

    private static ListaMensajesTxt listado=new ListaMensajesTxt();

    /*
    public static ListaMensajesTxt getListaTxt(){
        return ExecutorTxt.getListado();
    }*/


    private Execute() {
        //inicializarhilo();
        //this.setPriority(Thread.MIN_PRIORITY);

        //this.start();
    }

    public static Execute getInstance() {
        return instance;
    }



    public static ListaMensajesTxt getListado() {
        return listado;
    }

    public static void setListado(ListaMensajesTxt listado) {
        Execute.listado = listado;
    }

    public void run(){
        writePrincipal();

    }

    public void writePrincipal(){
        Runnable EscritorPrincipal= ()->{
            String temporal="";
            boolean band=true;
            while(band){
                NivelLog nivel=LogsJB.getGradeLog();
                //String Mensaje=Execute.getInstance().getTexto();
                //NivelLog logtemporal=Execute.getInstance().getNivelLog();
                MensajeWrite mensajetemp=null;
                mensajetemp=getListado().getDato();
                if(Objects.isNull(mensajetemp)){
                    band=false;
                    break;
                    //return;
                }
                String Mensaje=mensajetemp.getTexto();
                NivelLog logtemporal=mensajetemp.getNivelLog();

                //System.out.println("NivelLog definido: "+nivelaplicación);
                //System.out.println("NivelLog temporal: "+intniveltemporal);
                //System.out.println("Cantidad de mensajes Por limpiar: "+getListaTxt().getSize());
                //Verifica que el nivel de Log a escribir sea igual o mayor al nivel predefinido.
                if(logtemporal.getGradeLog()>=nivel.getGradeLog()){
                    if(!temporal.equals(Mensaje)){
                        verificarSizeFichero();
                        writeLog(logtemporal, Mensaje);
                        //writeTXT(Mensaje, logtemporal);
                    }else{

                    }
                    temporal=Mensaje;

                }
                if(getListado().getSize()==0){
                    //getExecutorTxt().shutdown();
                    band=false;
                    break;
                }
            }
            return;
        };


        ExecutorService executorPrincipal = Executors.newFixedThreadPool(1);

        executorPrincipal.submit(EscritorPrincipal);
        executorPrincipal.shutdown();
        //getExecutorPrincipal().shutdown();

    }


    private void writeTXT( String Mensaje, NivelLog logtemporal){
        //System.out.println("El valor es igual o mayor al nivel de la aplicación: "+intniveltemporal);
        //System.out.println("NivelLog: "+niveltemporal);
        //System.out.println("Texto: "+temporal2);
        //System.out.println("Temporal: "+temporal);

            Runnable verificarTxt= ()->{
                //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
                verificarSizeFichero();
                //Ejecuta la escritura en el archivo Log
                //System.out.println("Cantidad de mensajes ExecutorTxt: "+getListado().getSize());
            };
            Runnable writeTxt= ()->{
                //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
                writeLog(logtemporal, Mensaje);
                //Ejecuta la escritura en el archivo Log
                //System.out.println("Cantidad de mensajes ExecutorTxt: "+getListado().getSize());
            };
            ExecutorService executorTxt = Executors.newFixedThreadPool(1);
            ExecutorService verificarsize = Executors.newFixedThreadPool(1);
            verificarsize.submit(verificarTxt);
            executorTxt.submit(writeTxt);
            verificarsize.shutdown();
            executorTxt.shutdown();
            //getExecutorTxt().shutdown();
            return;
    }


/*
    public void run(){
        try{
            //Ejecuta la escritura en el archivo Log
            String temporal="";
            String temporal2="";
            NivelLog niveltemporal=NivelLog.TRACE;
            while(true){
                temporal2=getTexto();
                niveltemporal=getNivelLog();
                int intniveltemporal=niveltemporal.getGradeLog();
                int nivelaplicación=LogsJB.getGradeLog().getGradeLog();
                //System.out.println("NivelLog definido: "+nivelaplicación);
                //System.out.println("NivelLog temporal: "+intniveltemporal);
                //System.out.println("Cantidad de mensajes Por limpiar: "+getListaTxt().getSize());
                //Verifica que el nivel de Log a escribir sea igual o mayor al nivel predefinido.
                if(intniveltemporal>=nivelaplicación){
                    //System.out.println("El valor es igual o mayor al nivel de la aplicación: "+intniveltemporal);
                    //System.out.println("NivelLog: "+niveltemporal);
                    //System.out.println("Texto: "+temporal2);
                    //System.out.println("Temporal: "+temporal);

                    if(!temporal.equals(temporal2)){
                        //System.out.println("Texto es diferente: "+temporal2);
                        MensajeWrite Mensaje= new MensajeWrite();
                        Mensaje.setNivelLog(niveltemporal);
                        Mensaje.setTexto(temporal2);
                        //Verifica si el tamaño del fichero es menor a 5MB
                        verificarSizeFichero();
                        getListaTxt().addDato(Mensaje);
                        //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
                    }else{

                    }
                }

                temporal=temporal2;
                //Verifica si no es la primera vez que corre el hilo
                /
                if(!Objects.isNull(temporal)){
                    int tareas=getListaTxt().getSize();
                    if(tareas==0){

                    }
                }

            }

        }catch (Exception e){
            System.out.println("Excepcion capturada en el metodo run Execute: " +e.getMessage());
        }

    }*/

    public synchronized String getTexto() {
        notify();
        return Texto;
    }

    public synchronized void setTexto(String texto) {
        try{
            //if(getInstance().getState()!= Thread.State.RUNNABLE){
                 Execute ejecutor=Execute.getInstance();
                /*if(Execute.getInstance().isAlive()){
                    System.out.println("Despertara el Execute: "+Thread.currentThread().getName());
                    Execute.getInstance().resume();
                }*/
            //}
            this.Texto=texto;
            //wait();

        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear el texto a escribir en el Log: " +Texto);
        }

    }

    public NivelLog getNivelLog() {
        return nivelLog;
    }

    public synchronized void setNivelLog(NivelLog NnivelLog) {
        try{
            //if(getInstance().getState()!= Thread.State.RUNNABLE){
                //Execute ejecutor=Execute.getInstance();
            //}
            this.nivelLog= NnivelLog;
        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear el nivel del log " +NnivelLog);
        }

    }



}
