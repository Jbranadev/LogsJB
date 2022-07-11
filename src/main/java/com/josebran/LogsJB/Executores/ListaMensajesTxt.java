package com.josebran.LogsJB.Executores;

import java.util.LinkedList;
import java.util.List;

public class ListaMensajesTxt {
    private List<MensajeWrite> mensajes=new LinkedList<>();

    public synchronized void addDato(MensajeWrite dato)
    {
        /*
        if(ExecutorTxt.getInstance().getState()!= Thread.State.RUNNABLE){
            ExecutorTxt ejecutor= ExecutorTxt.getInstance();

        }*/
        mensajes.add(dato);
        notify();

    }

    public synchronized MensajeWrite getDato()  {
        try{
            if (mensajes.size()==0){
                this.wait();
                //if(!Execute.getInstance().getExecutor().isShutdown()){
                //Execute.getInstance().getExecutor().shutdown();
                //}
                //Execute.getInstance().getExecutor().shutdown();
            }else{
                MensajeWrite dato = mensajes.get(0);
                mensajes.remove(0);
                return dato;
            }
        }catch (Exception e){
            System.out.println("Excepcion capturada al obtener el mensaje: "+Thread.currentThread().getName());
        }
        return null;
    }

    public synchronized int getSize(){
        return mensajes.size();
    }

}
