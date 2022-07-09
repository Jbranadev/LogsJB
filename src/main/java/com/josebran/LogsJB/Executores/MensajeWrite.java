package com.josebran.LogsJB.Executores;

import com.josebran.LogsJB.Numeracion.NivelLog;

public class MensajeWrite {
    private String Texto;
    private NivelLog nivelLog;

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
}
