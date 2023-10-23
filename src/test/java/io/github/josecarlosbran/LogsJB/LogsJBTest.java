package io.github.josecarlosbran.LogsJB;


import io.github.josecarlosbran.JBRestAPI.Enumeraciones.typeAutentication;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.LogsJB.Numeracion.NivelLog;
import io.github.josecarlosbran.LogsJB.Numeracion.SizeLog;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.josecarlosbran.LogsJB.LogsJB.*;


@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class LogsJBTest {


    @Test(testName = "Write Log sin Setear propiedades")
    public void writeLogSinSetearPropiedades() {
        try {
            File fichero = new File(com.josebran.LogsJB.LogsJB.getRuta());
            //System.out.println("Ruta del log: " + fichero.getAbsolutePath());
            //Verifica si existe la carpeta Logs, si no existe, la Crea
            File directorio = new File(fichero.getParent());
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("*");
                    System.out.println("Crea el directorio donde almacenara el Log de la prueba: " + fichero.getParent());
                    System.out.println("*");
                }
            }
            FileUtils.writeStringToFile(fichero, "Creación archivo Primera Vez", Charset.defaultCharset());

            trace(" comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

            debug(" comentario grado " + "Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

            info(" comentario grado " + "Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

            warning(" comentario grado " + "Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

            error(" comentario grado " + "Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

            fatal(" comentario grado " + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
            LogsJB.waitForOperationComplete();
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    @Test(testName = "Setear Usuario txt", dependsOnMethods = "writeLogSinSetearPropiedades")
    public void setearUsuario() {
        try {
            LogsJB.setUsuario("Carlos Bran");
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getUsuario().equalsIgnoreCase("Carlos Bran"), "El valor de Usuario obtenido no corresponde al seteado");
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Setear Nivel Log txt", dependsOnMethods = "setearUsuario")
    public void setearNivelLog() {
        try {
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog() == NivelLog.TRACE, "El valor de Log obtenido no corresponde al seteado");

            LogsJB.setGradeLog(NivelLog.DEBUG);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog() == NivelLog.DEBUG, "El valor de Log obtenido no corresponde al seteado");

            LogsJB.setGradeLog(NivelLog.INFO);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog() == NivelLog.INFO, "El valor de Log obtenido no corresponde al seteado");

            LogsJB.setGradeLog(NivelLog.WARNING);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog() == NivelLog.WARNING, "El valor de Log obtenido no corresponde al seteado");

            LogsJB.setGradeLog(NivelLog.ERROR);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog() == NivelLog.ERROR, "El valor de Log obtenido no corresponde al seteado");

            LogsJB.setGradeLog(NivelLog.FATAL);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog() == NivelLog.FATAL, "El valor de Log obtenido no corresponde al seteado");

        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    @Test(testName = "Setear Size Log txt", dependsOnMethods = "setearNivelLog")
    public void setearSizeLog() {
        try {
            LogsJB.setSizeLog(SizeLog.Little_Little);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog() == SizeLog.Little_Little.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");


            LogsJB.setSizeLog(SizeLog.Little);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog() == SizeLog.Little.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");

            LogsJB.setSizeLog(SizeLog.Small_Medium);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog() == SizeLog.Small_Medium.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");

            LogsJB.setSizeLog(SizeLog.Medium);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog() == SizeLog.Medium.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");

            LogsJB.setSizeLog(SizeLog.Small_Large);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog() == SizeLog.Small_Large.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");

            LogsJB.setSizeLog(SizeLog.Large);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog() == SizeLog.Large.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");


        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    @Test(testName = "Write Log txt Llegar a 8MB", dependsOnMethods = "setearSizeLog")
    public void writeLog() {
        try {
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            ThreadLocalRandom.current().nextInt(5, 14);
            Integer i = 0;
            Random random = new Random();
            while (i < 55000) {

                trace(i + " comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                debug(i + " comentario grado " + "Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                info(i + " comentario grado " + "Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                warning(i + " comentario grado " + "Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                error(i + " comentario grado " + "Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                fatal(i + " comentario grado " + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                i = i + 6;
            }
            LogsJB.waitForOperationComplete();

        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Write Log txt Llegar a 15MB", dependsOnMethods = "writeLog")
    public void writeLogVeinNueveTxt() {
        try {
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            ThreadLocalRandom.current().nextInt(5, 14);
            Integer i = 0;
            Random random = new Random();
            while (i < 55000) {

                trace(i + " comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                debug(i + " comentario grado " + "Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                info(i + " comentario grado " + "Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                warning(i + " comentario grado " + "Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                error(i + " comentario grado " + "Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                fatal(i + " comentario grado " + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                i = i + 6;
            }
            LogsJB.waitForOperationComplete();

        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Write Log txt Llegar a 25MB", dependsOnMethods = "writeLogVeinNueveTxt")
    public void writeLogTreintaYSeisTexto333() {
        try {
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            ThreadLocalRandom.current().nextInt(5, 14);
            Integer i = 0;
            Random random = new Random();
            while (i < 55000) {

                trace(i + " comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                debug(i + " comentario grado " + "Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                info(i + " comentario grado " + "Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                warning(i + " comentario grado " + "Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                error(i + " comentario grado " + "Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                fatal(i + " comentario grado " + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                i = i + 6;
            }
            LogsJB.waitForOperationComplete();

        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    @Test(testName = "Write Log Segunda Vez txt", dependsOnMethods = "writeLogTreintaYSeisTexto333")
    public void writeLogSegundaOcasionMayorTreintaYSeis() {
        try {
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            LogsJB.getLogsJBProperties();
            //LogsJB.debug( "Primer comentario grado Debug");
            //System.out.println("clase: " + Clase + " metodo: " + Metodo);
            //Rutas de archivos
            File fichero = new File(getRuta());
            //System.out.println("Ruta del log: " + fichero.getAbsolutePath());
            //Verifica si existe la carpeta Logs, si no existe, la Crea
            File directorio = new File(fichero.getParent());
            FileUtils.deleteDirectory(directorio);


            Integer i = 0;
            Random random = new Random();
            while (i < 1200) {

                trace(i + " comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                debug(i + " comentario grado" + " Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                info(i + " comentario grado" + " Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                warning(i + " comentario grado" + " Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                error(i + " comentario grado" + " Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));

                fatal(i + " comentario grado" + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));

                i = i + 6;
            }
            LogsJB.waitForOperationComplete();
            Thread.sleep(1000);
            while (i < 1200) {

                trace(i + "cadena contar caracteres26");

                debug(i + "cadena contar caracteres treinta3");

                info(i + "cadena contar caracteres treinta 6");

                warning(i + "cadena contar caracteres treinta Siete");

                error(i + " comentario grado");

                fatal(i + " comentario grado");

                i = i + 6;
            }
            LogsJB.waitForOperationComplete();

        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    @Test(testName = "Write Log DB"//,
            /*dependsOnMethods = "writeLogSegundaOcasionMayorTreintaYSeis"*/)
    public void writeLogDB() {
        try {
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
            //LogsJB.setGradeLog(NivelLog.WARNING);
            //LogsJB.debug( "Primer comentario grado Debug");
            Integer i = 0;
            while (i < 60) {
                i += 6;
                //Comentario grado Trace
                trace("Primer comentario grado Trace " + i);
                //Comentario grado Debug
                debug("Primer comentario grado Debug " + i);
                //Comentario grado Info
                info("Primer comentario grado Info " + i);
                //Comentario grado Warning
                warning("Primer comentario grado Warning " + i);
                //Comentario grado Error
                error("Primer comentario grado Error " + i);
                //Comentario grado Fatal
                fatal("Primer comentario grado Fatal " + i);
            }
            LogsJB.waitForOperationComplete();
        } catch (Exception e) {
            System.out.println("Excepcion capturada en el metodo main: " + e.getMessage());
        }
    }


    @Test(testName = "Write Log RestAPI",
            dependsOnMethods = "writeLogDB")
    public void writeLogRestAPI() {
        try {
            LogsJB.setWriteTxt(false);
            LogsJB.setWriteDB(false);
            LogsJB.setWriteRestAPI(true);
            LogsJB.setKeyLogRest("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKb3NlIENhcmxvcyBBbGZyZWRvIEJyYW4gQWd1aXJyZSIsImlzcyI6ImxvY2FsaG9zdDo4MDgwIiwiaWF0IjoxNjkzMjg1NDU2LCJleHAiOjE2OTMyODYzNTZ9.8E_XPxXYfXU_cWyWt5momm33PISmOziToJGk6GKQ02iv6AR_syh_AyFzyev2Yh64L886Ntr0jxuCm6JpvwYnsg");
            LogsJB.setUrlLogRest("http://localhost:8080/WebServicesPrueba/Logs");
            LogsJB.setTipeautentication(typeAutentication.BEARER);
            LogsJB.setGradeLog(NivelLog.TRACE);
            //LogsJB.setGradeLog(NivelLog.WARNING);
            //LogsJB.debug( "Primer comentario grado Debug");
            Integer i = 0;
            while (i < 60) {
                i += 6;
                //Comentario grado Trace
                trace("Primer comentario grado Trace " + i);
                //Comentario grado Debug
                debug("Primer comentario grado Debug " + i);
                //Comentario grado Info
                info("Primer comentario grado Info " + i);
                //Comentario grado Warning
                warning("Primer comentario grado Warning " + i);
                //Comentario grado Error
                error("Primer comentario grado Error " + i);
                //Comentario grado Fatal
                fatal("Primer comentario grado Fatal " + i);
            }
            LogsJB.waitForOperationComplete();
        } catch (Exception e) {
            System.out.println("Excepcion capturada en el metodo main: " + e.getMessage());
        }
    }

}