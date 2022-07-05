package com.josebran.LogsJB;


import com.josebran.LogsJB.Numeracion.NivelLog;

import static com.josebran.LogsJB.LogsJB.*;
import static com.josebran.LogsJB.Methods.writeLog;

public class pruebas {

    public static void main(String[] args)
        {
            executor(NivelLog.DEBUG, "Ruta donde se esta escribiendo el log: "+getRuta());
            debug( "Primer comentario grado Debug");
            error( "Primer comentario grado Error");
            fatal( "Primer comentario grado Fatal");
            info( "Primer comentario grado Info");
            trace( "Primer comentario grado Trace");
            warning( "Primer comentario grado Warning");



        }


}

