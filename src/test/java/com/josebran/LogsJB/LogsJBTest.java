package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;
import org.testng.annotations.Test;
import static com.josebran.LogsJB.LogsJB.*;



public class LogsJBTest {


    @Test(testName = "Write Log txt")
    public void writeLog() {
    try{
        LogsJB.setGradeLog(NivelLog.TRACE);
        //LogsJB.debug( "Primer comentario grado Debug");
        Integer i=0;
        while(i<6000){
            i++;
            //Comentario grado Trace
            trace( "Primer comentario grado Trace");
            //Comentario grado Debug
            debug( "Primer comentario grado Debug");
            //Comentario grado Info
            info( "Primer comentario grado Info");
            //Comentario grado Warning
            warning( "Primer comentario grado Warning");
            //Comentario grado Error
            error( "Primer comentario grado Error");
            //Comentario grado Fatal
            fatal( "Primer comentario grado Fatal");
        }

        LogsJB.waitForOperationComplete();


    }catch (Exception e){
        System.out.println("Excepcion capturada en el metodo main: "+e.getMessage());
    }
}



}