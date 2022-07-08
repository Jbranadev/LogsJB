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


    private String mensaje;
    private NivelLog nivellog;


    public void run(){
        //Ejecuta la escritura en el archivo Log
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
