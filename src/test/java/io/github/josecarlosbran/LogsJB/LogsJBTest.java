package io.github.josecarlosbran.LogsJB;


import io.github.josecarlosbran.JBRestAPI.Enumeraciones.typeAutentication;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.LogsJB.Numeracion.NivelLog;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static io.github.josecarlosbran.LogsJB.LogsJB.*;
@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class LogsJBTest {

    @Test(testName = "Write Log Txt")
    public void writeLogTxt() {
        try{
            LogsJB.setWriteTxt(true);
            LogsJB.setWriteDB(false);
            LogsJB.setWriteRestAPI(false);
            LogsJB.setGradeLog(NivelLog.TRACE);
            //LogsJB.debug( "Primer comentario grado Debug");
            Integer i=0;
            while(i<6000){
                i+=6;
                //Comentario grado Trace
                trace( "Primer comentario grado Trace " +i);
                //Comentario grado Debug
                debug( "Primer comentario grado Debug "+i);
                //Comentario grado Info
                info( "Primer comentario grado Info "+i);
                //Comentario grado Warning
                warning( "Primer comentario grado Warning "+i);
                //Comentario grado Error
                error( "Primer comentario grado Error "+i);
                //Comentario grado Fatal
                fatal( "Primer comentario grado Fatal "+i);
            }
            LogsJB.waitForOperationComplete();
        }catch (Exception e){
            System.out.println("Excepcion capturada en el metodo main: "+e.getMessage());
        }
    }


    @Test(testName = "Write Log DB",
    dependsOnMethods = "writeLogTxt")
    public void writeLogDB() {
        try{
            LogsJB.setWriteTxt(false);
            LogsJB.setWriteDB(true);
            LogsJB.setWriteRestAPI(false);
            String separador = System.getProperty("file.separator");
            String BDSqlite = (Paths.get("").toAbsolutePath().normalize().toString() + separador +
                    "Logs" +
                    separador +
                    "LogsJB.db");
            LogsJBDB.setDataBaseGlobal(BDSqlite);
            LogsJBDB.setDataBaseTypeGlobal(DataBase.SQLite);
            LogsJBDB.setHostGlobal("");
            LogsJBDB.setUserGlobal("");
            LogsJBDB.setPortGlobal("");
            LogsJBDB.setPropertisUrlConexionGlobal("");
            LogsJB.setGradeLog(NivelLog.TRACE);
            //com.josebran.LogsJB.LogsJB.setGradeLog(com.josebran.LogsJB.Numeracion.NivelLog.WARNING);
            //LogsJB.debug( "Primer comentario grado Debug");
            Integer i=0;
            while(i<6000){
                i+=6;
                //Comentario grado Trace
                trace( "Primer comentario grado Trace " +i);
                //Comentario grado Debug
                debug( "Primer comentario grado Debug "+i);
                //Comentario grado Info
                info( "Primer comentario grado Info "+i);
                //Comentario grado Warning
                warning( "Primer comentario grado Warning "+i);
                //Comentario grado Error
                error( "Primer comentario grado Error "+i);
                //Comentario grado Fatal
                fatal( "Primer comentario grado Fatal "+i);
            }
            LogsJB.waitForOperationComplete();
        }catch (Exception e){
            System.out.println("Excepcion capturada en el metodo main: "+e.getMessage());
        }
    }


    @Test(testName = "Write Log RestAPI")
    public void writeLogRestAPI() {
        try{
            LogsJB.setWriteTxt(false);
            LogsJB.setWriteDB(false);
            LogsJB.setWriteRestAPI(true);
            LogsJB.setKeyLogRest("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKb3NlIENhcmxvcyBBbGZyZWRvIEJyYW4gQWd1aXJyZSIsImlzcyI6ImxvY2FsaG9zdDo4MDgwIiwiaWF0IjoxNjkxNDUzMjkyLCJleHAiOjE2OTE0NTQxOTJ9.oCQ0krmifGh_mKmrpVuJMoU5bqMKqr3gesX3RAjFP7D74MU79qZbqKtkMXFCC6TK7_O0iOKv3w3A3mm7qCrOfg");
            LogsJB.setUrlLogRest("http://localhost:8080/WebServicesPrueba/Logs");
            LogsJB.setTipeautentication(typeAutentication.BEARER);
            LogsJB.setGradeLog(NivelLog.TRACE);
            //com.josebran.LogsJB.LogsJB.setGradeLog(com.josebran.LogsJB.Numeracion.NivelLog.WARNING);
            //LogsJB.debug( "Primer comentario grado Debug");
            Integer i=0;
            while(i<6){
                i+=6;
                //Comentario grado Trace
                trace( "Primer comentario grado Trace " +i);
                //Comentario grado Debug
                debug( "Primer comentario grado Debug "+i);
                //Comentario grado Info
                info( "Primer comentario grado Info "+i);
                //Comentario grado Warning
                warning( "Primer comentario grado Warning "+i);
                //Comentario grado Error
                error( "Primer comentario grado Error "+i);
                //Comentario grado Fatal
                fatal( "Primer comentario grado Fatal "+i);
            }
            LogsJB.waitForOperationComplete();
        }catch (Exception e){
            System.out.println("Excepcion capturada en el metodo main: "+e.getMessage());
        }
    }

}