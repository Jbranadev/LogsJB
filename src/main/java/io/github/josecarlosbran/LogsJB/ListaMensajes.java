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

package io.github.josecarlosbran.LogsJB;

import java.util.LinkedList;
import java.util.List;

/***
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Esta clase sirve como acople entre el proceso de ejecución principal que se encarga de agregar un mensaje
 * por escribir a la lista, mientras que el proceso de ejecución secundario se encarga de escribir los mensajes.
 */
class ListaMensajes {
    /**
     * Lista de mensajes a escribir
     */
    private List<MensajeWrite> mensajes;



    /**
     * Constructor por default
     */
    protected ListaMensajes(){
        this.mensajes=new LinkedList<MensajeWrite>();
    }

    /***
     * Agrega un mensaje a la lista para que este luego sea escrito.
     * @param dato Mensaje que se desea agregar a la lista.
     */
    protected synchronized void addDato(MensajeWrite dato) {
        this.mensajes.add(dato);
    }

    /***
     * Retorna el primer mensaje en la cola que encuentra por medio del algoritmo FIFO, si en dado caso no hay mensajes en la cola, retorna
     * null.
     * @return Restorna el proximo mensaje en la cola a ser escrito, basado en el algoritmo FIFO, si no hay mensajes
     * retorna null.
     */
    protected synchronized MensajeWrite getDato()  {
        try{
            if (this.mensajes.size()==0){
                return null;
            }else{
                MensajeWrite dato = mensajes.get(0);
                this.mensajes.remove(0);
                return dato;
            }
        }catch (Exception e){
            com.josebran.LogsJB.LogsJB.fatal("Excepción capturada al obtener el mensaje: "+Thread.currentThread().getName());
            com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : "+e.getClass());
            com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : "+e.getCause());
            com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : "+e.getMessage());
            com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : "+e.getStackTrace());
        }
        return null;
    }

    /***
     * Retorna la cantidad de mensajes que tiene la lista actualmente.
     * @return Retorna un entero que representa la cantidad de mensajes que actualmente tiene la lista.
     */
    protected synchronized int getSize(){
        return this.mensajes.size();
    }

}
