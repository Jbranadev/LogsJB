package com.josebran.LogsJB;


import com.josebran.LogsJB.Numeracion.NivelLog;

import static com.josebran.LogsJB.LogsJB.*;
import static com.josebran.LogsJB.Methods.writeLog;

public class pruebas {

    public static void main(String[] args)
        {
            executor(NivelLog.INFO, "Ruta donde se esta escribiendo el log: "+getRuta());
            executor(NivelLog.DEBUG, "Primer comentario grado Debug");
            executor(NivelLog.ERROR, "Primer comentario grado Error");
            executor(NivelLog.FATAL, "Primer comentario grado Fatal");
            executor(NivelLog.INFO, "Primer comentario grado Info");
            executor(NivelLog.TRACE, "Primer comentario grado Trace");
            executor(NivelLog.WARNING, "Primer comentario grado Warning");



        }


}

