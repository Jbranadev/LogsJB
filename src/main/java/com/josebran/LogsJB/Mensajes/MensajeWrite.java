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

import com.josebran.LogsJB.Numeracion.NivelLog;

public class MensajeWrite {
    private String Texto;
    private NivelLog nivelLog;

    private String Clase;

    private String Metodo;

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String texto) {
        Texto = texto;
    }

    public NivelLog getNivelLog() {
        return nivelLog;
    }

    public void setNivelLog(NivelLog nivelLog) {
        this.nivelLog = nivelLog;
    }
    /***
     * Obtiene el nombre de la clase que actualmente esta llamando al Log
     * @return Retorna el nombre de la clase que esta invocando la escritura del Log
     */
    public String getClase() {
        return Clase;
    }
    /***
     * Setea el nombre de la clase que esta haciendo el llamado al metodo que escribe el Log.
     * @param clase Nombre de la clase que llama al metodo que escribe el Log.
     */
    public void setClase(String clase) {
        Clase = clase;
    }
    /**
     * Obtiene el nombre del metodo que actualmente esta llamando al Log
     * @return Retorna el nombre del metodo que esta invocando la escritura del Log
     */
    public String getMetodo() {
        return Metodo;
    }
    /**
     * Setea el nombre del metodo que esta haciendo el llamado al metodo que escribe el Log.
     * @param metodo Nombre del metodo que llama al metodo que escribe el Log.
     */
    public void setMetodo(String metodo) {
        Metodo = metodo;
    }
}
