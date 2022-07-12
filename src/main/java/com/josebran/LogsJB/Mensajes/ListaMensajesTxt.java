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

package com.josebran.LogsJB.Mensajes;

import java.util.LinkedList;
import java.util.List;

public class ListaMensajesTxt {
    private List<MensajeWrite> mensajes=new LinkedList<>();

    public synchronized void addDato(MensajeWrite dato)
    {
        //System.out.println("Agrega el msj a la lista: "+dato.getTexto()+" "+dato.getNivelLog());
        mensajes.add(dato);
        //notify();

    }

    public synchronized MensajeWrite getDato()  {
        try{
            if (mensajes.size()==0){
                return null;
            }else{
                MensajeWrite dato = mensajes.get(0);
                mensajes.remove(0);
                //System.out.println("Quita el msj a la lista: "+dato.getTexto()+" "+dato.getNivelLog());
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
