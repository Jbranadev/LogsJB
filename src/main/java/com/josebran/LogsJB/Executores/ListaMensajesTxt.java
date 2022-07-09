package com.josebran.LogsJB.Executores;

import java.util.LinkedList;
import java.util.List;

public class ListaMensajesTxt {
    private List<MensajeWrite> mensajes=new LinkedList<>();

    public synchronized void addDato(MensajeWrite dato)
    {
        mensajes.add(dato);
        notify();

    }

    public synchronized MensajeWrite getDato() throws InterruptedException {
        if (mensajes.size()==0)
            this.wait();
        MensajeWrite dato = mensajes.get(0);
        mensajes.remove(0);
        return dato;
    }

    public synchronized int getSize(){
        return mensajes.size();
    }

}
