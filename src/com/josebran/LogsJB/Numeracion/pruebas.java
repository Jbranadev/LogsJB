package com.josebran.LogsJB.Numeracion;

import static com.josebran.LogsJB.Methods.writeLog;

public class pruebas {

    public static void main(String[] args)
        {
            writeLog(NivelLog.DEBUG, "Primer comentario grado Debug");
            writeLog(NivelLog.ERROR, "Primer comentario grado Error");
            writeLog(NivelLog.FATAL, "Primer comentario grado Fatal");
            writeLog(NivelLog.INFO, "Primer comentario grado Info");
            writeLog(NivelLog.TRACE, "Primer comentario grado Trace");
            writeLog(NivelLog.WARNING, "Primer comentario grado Warning");



        }


}

