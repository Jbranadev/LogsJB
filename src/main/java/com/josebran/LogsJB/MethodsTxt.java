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

package com.josebran.LogsJB;


import com.josebran.LogsJB.Numeracion.NivelLog;
import com.josebran.LogsJB.Numeracion.SizeLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.josebran.LogsJB.LogsJB.*;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase que almacena los metodos necesarios para poder escribir el LogTxt
 */
class MethodsTxt {

    /***
     * Obtiene el usuario actual del sistema operativo
     */
    static String usuario=System.getProperty("user.name");

    /***
     * Contador que expresa la cantidad de veces que se a escrito en la ejecución actual de la aplicación
     *
     */
    private static long logtext =0;

    /***
     * Ruta donde se estara escribiendo el log por default, la cual sería:
     *  ContexAplicación/Logs/fecha_hoy/Log.txt
     */
    protected static String ruta= (Paths.get("").toAbsolutePath().normalize().toString()+"/Logs/"+convertir_fecha("dd-MM-YYYY") + "/Log.txt").replace("\\","/");


    /****
     * NivelLog desde el grado configurado hacía arriba se estara escribiendo el Log
     * El NivelLog por default es INFO.
     */
    protected static NivelLog gradeLog=NivelLog.INFO;

    /****
     * Tamaño maximo del archivo LogTxt diario que se estara escribiendo, si se supera el tamaño se modificara
     * el nombre del archivo a LOG_dd-MM-YYYY_HH-MM-SSS.txt, e iniciara la escritura del archivo Log.txt
     * con el nuevo registro.
     */
    protected static SizeLog sizeLog=SizeLog.Little_Little;

    /*MethodsTxt(){
        setearRuta();
        setearNivelLog();
        setearSizelLog();
    }*/

    /***
     * Setea el NivelLog configurado en las propiedades del sistema, de no estar
     * configurada la propiedad correspondiente a NivelLog, setea el nivel por default.
     */
    protected static void setearNivelLog(){
        String nivelLog=System.getProperty("NivelLog");
        if(Objects.isNull(nivelLog)){
                //Si la propiedad del sistema no esta definida, setea el nivel por default
            setGradeLog(NivelLog.INFO);
        }else{
            if(nivelLog.equals("TRACE")){
                setGradeLog(NivelLog.TRACE);
            }
            if(nivelLog.equals("DEBUG")){
                setGradeLog(NivelLog.DEBUG);
            }
            if(nivelLog.equals("INFO")){
                setGradeLog(NivelLog.INFO);
            }
            if(nivelLog.equals("WARNING")){
                setGradeLog(NivelLog.WARNING);
            }
            if(nivelLog.equals("ERROR")){
                setGradeLog(NivelLog.ERROR);
            }
            if(nivelLog.equals("FATAL")){
                setGradeLog(NivelLog.FATAL);
            }
        }
        System.out.println("SystemProperty Seteada soporte: "+System.getProperty("NivelLog"));
    }

    /***
     * Setea la RutaLog configurado en las propiedades del sistema, de no estar
     * configurada la propiedad correspondiente a RutaLog, setea la ruta por default.
     */
    protected static void setearRuta(){
        String rutaLog=System.getProperty("RutaLog");
        if(Objects.isNull(rutaLog)){
            //Si la propiedad del sistema no esta definida, setea la ruta por default
            String ruta=(Paths.get("").toAbsolutePath().normalize().toString()+"/Logs/"+
                    convertir_fecha("dd-MM-YYYY") + "/Log.txt").replace("\\","/");
            setRuta(ruta);
        }else{
            setRuta(rutaLog);
        }
        System.out.println("SystemProperty Seteada soporte: "+System.getProperty("RutaLog"));
    }


    /***
     * Setea el SizeLog configurado en las propiedades del sistema, de no estar
     * configurada la propiedad correspondiente a SizeLog, setea el SizeLog por default.
     */
    protected static void setearSizelLog(){
        String sizeLog=System.getProperty("SizeLog");
        if(Objects.isNull(sizeLog)){
            //Si la propiedad del sistema no esta definida, setea el nivel por default
            setSizeLog(SizeLog.Little_Little);
        }else{
            if(sizeLog.equals("Little_Little")){
                setSizeLog(SizeLog.Little_Little);
            }
            if(sizeLog.equals("Little")){
                setSizeLog(SizeLog.Little);
            }
            if(sizeLog.equals("Small_Medium")){
                setSizeLog(SizeLog.Small_Medium);
            }
            if(sizeLog.equals("Medium")){
                setSizeLog(SizeLog.Medium);
            }
            if(sizeLog.equals("Small_Large")){
                setSizeLog(SizeLog.Small_Large);
            }
            if(sizeLog.equals("Large")){
                setSizeLog(SizeLog.Large);
            }
        }
        System.out.println("SystemProperty Seteada soporte: "+System.getProperty("SizeLog"));
    }


    /***
     * Obtiene la fecha actual en formato dd/MM/YYYY HH:MM:SS
     * @return Retorna una cadena de texto con la fecha obtenida
     */
    protected static String convertir_fecha(){
        String temp=null;
        try{
            //DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            //DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss SSS");
            //convertir_fecha()
            temp=formater.format(LocalDateTime.now());

        }catch (Exception e){
            System.out.println("Exepcion capturada en el metodo Metodo que Obtiene la fecha actual en formato dd/MM/YYYY HH:MM:SS");
            System.out.println("Tipo de Excepción : "+e.getClass());
            System.out.println("Causa de la Exepción : "+e.getCause());
            System.out.println("Mensaje de la Exepción : "+e.getMessage());
            System.out.println("Trace de la Exepción : "+e.getStackTrace());
        }
        return temp;
    }

    /***
     * Obtiene la fecha en el formato indicado
     * @param formato Formato que se desea obtener la fecha
     * @return Retorna una cadena de texto con la fecha obtenida en el formato especificado.
     */
    public static String convertir_fecha(String formato){

        String temp=null;
        try{
            DateTimeFormatter formater = DateTimeFormatter.ofPattern(formato);
            temp=formater.format(LocalDateTime.now());

        }catch (Exception e){
            System.out.println("Exepcion capturada en el metodo Metodo que Obtiene la fecha en el formato indicado");
            System.out.println("Tipo de Excepción : "+e.getClass());
            System.out.println("Causa de la Exepción : "+e.getCause());
            System.out.println("Mensaje de la Exepción : "+e.getMessage());
            System.out.println("Trace de la Exepción : "+e.getStackTrace());
        }

        return temp;
    }

    /**
     * metodo que retorna la cantidad de tabulaciones para el siguiente texto en la misma linea conforme
     * al la longitud de la cadena actual:
     * si la cadena es  igual o menor a 7 da 4 tabulaciones
     * si la cadena es igual o menor a 16 da 3 tabulaciones
     * si la cadena es  mayor a 16 da 2 tabulaciones.
     * @param cadena Texto a evaluar para obtener la separcion de tabulaciones de acuerdo al algoritmo definido.
     * @return Retorna un string con la cantidad de tabulaciones respecto al siguiente texto en la misma linea.
     */
    public static String getTabs(String cadena) {
        //Reglas del negocio, maximas tabulaciones son 4
        //Minima tabulacion es una
        String result = "";
        String tab = "\u0009";
        int tamaño=cadena.length();
        int sobrantes=tamaño%4;
        if(sobrantes!=0){
            int restantes=4-sobrantes;
            for(int i=1;i<restantes;i++){
                result=result+" ";
            }
        }
        try{
            //Si la cadena es menor a 13, retornara 7 tabs
            if(tamaño<13){
                for(int i=0;i<7;i++){
                    result=result+tab;
                }
                //Si la cadena es menor a 17, retornara 6 tabs
            }else if(tamaño<17){
                for(int i=0;i<6;i++){
                    result=result+tab;
                }
                //Si la cadena es menor a 25, retornara 5 tabs
            }else if(tamaño<25){
                for(int i=0;i<5;i++){
                    result=result+tab;
                }
                //Si la cadena es menor a 29, retornara 4 tabs
            }else if(tamaño<29){
                for(int i=0;i<4;i++){
                    result=result+tab;
                }
                //Si la cadena es menor a 33, retornara 3 tabs
            }else if(tamaño<33){
                for(int i=0;i<3;i++){
                    result=result+tab;
                }
                //Si la cadena es menor a 37, retornara 2 tabs
            }else if(tamaño<37){
                for(int i=0;i<2;i++){
                    result=result+tab;
                }
                //Si la cadena es mayor a 36, retornara 2 tabs
            }else if(tamaño>36){
                for(int i=0;i<2;i++){
                    result=result+tab;
                }
            }
        }catch (Exception e){
            System.out.println("Exepcion capturada en el metodo Metodo que retorna la cantidad de tabulaciones para el siguiente texto en la misma linea conforme\n" +
                    "     * al la longitud de la cadena actual:");
            System.out.println("Tipo de Excepción : "+e.getClass());
            System.out.println("Causa de la Exepción : "+e.getCause());
            System.out.println("Mensaje de la Exepción : "+e.getMessage());
            System.out.println("Trace de la Exepción : "+e.getStackTrace());
        }

        return result;
    }


    /***
     * Verifica el tamaño del fichero de log actual, cuando este alcance los 5MB le asignara el nombre
     * LOG_dd-MM-YYYY_HH-MM-SSS.txt donde la fecha y hora que se le coloca, corresponde a la fecha y hora de creación del archivo
     */
    protected static synchronized void verificarSizeFichero(){
        try {
            //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
            File logactual = new File(getRuta());
            //Devuelve el tamaño del fichero en Mb
            long sizeFichero=((logactual.length())/1024)/1024;
            //long sizeFichero=((logactual.length())/1024);
            //System.out.println("Tamaño del archivo en Kb: " +sizeFichero);

            if(sizeFichero>getSizeLog().getSizeLog()){
                BasicFileAttributes attributes = null;
                String fechaformateada="";
                int numeroaleatorio=0;
                try { attributes = Files.readAttributes(logactual.toPath(), BasicFileAttributes.class);
                    //FileTime time = attributes.creationTime();
                    FileTime time = attributes.lastModifiedTime();

                    String pattern = "dd-MM-yyyy HH:mm:ss SS";
                    numeroaleatorio = (int) Math.floor(Math.random()*(9-0+1)+0);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                    fechaformateada = simpleDateFormat.format( new Date( time.toMillis() ) );

                    //System.out.println( "La fecha y hora de creación del archivo es: " + fechaformateada );
                }catch(IOException exception) {
                    System.out.println("Exception handled when trying to get file " + "attributes: " + exception.getMessage());
                }

                //SimpleDateFormat  formatofecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                //String fechalog=(formatofecha.format(logactual.lastModified())).replace(":","-").replace(" ", "_");
                String fechalog=fechaformateada.replace(":","-").replace(" ", "_")+numeroaleatorio;
                String newrute=getRuta().replace(".txt", "")+"_"+fechalog+".txt";
                File newfile= new File(newrute);
                if(logactual.renameTo(newfile)){
                    System.out.println("Archivo renombrado: " +newrute);
                    logactual.delete();
                    //Thread.sleep(5000);
                    logactual.createNewFile();
                }else{
                    System.out.println(logactual.toPath()+" No se pudo renombrar el archivo: " +newrute);
                }
            }

        }catch (Exception e){
            System.out.println("Exepcion capturada en el metodo Metodo por medio del cual se verifica el tamaño del archivo: " +getRuta());
            System.out.println("Tipo de Excepción : "+e.getClass());
            System.out.println("Causa de la Exepción : "+e.getCause());
            System.out.println("Mensaje de la Exepción : "+e.getMessage());
            System.out.println("Trace de la Exepción : "+e.getStackTrace());
        }
    }

    /***
     * Metodo para escribir en el Log, lo que esta sucediendo dentro de la prueba,
     * imprime en consola el texto que sera agregado al Log
     * @param nivelLog nivelLog que representa el grado de log del mensaje.
     * @param Texto Texto que es el mensaje que se desea escribir.
     * @param Clase Clase que representa la clase en la cual se mando a llamar la escritura del Log.
     * @param Metodo Metodo que representa el metodo desde el cual se llama la escritura del Log.
     * @param fecha fecha y hora de la escritura del Log.
     */
    protected synchronized static void writeLog(NivelLog nivelLog, String Texto, String Clase, String Metodo, String fecha){
        try{
            //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
            String tab = "\u0009";
            //Aumenta la Cantidad de Veces que se a escrito el Log
            setLogtext(getLogtext()+1);


            //System.out.println("clase: " + Clase + " metodo: " + Metodo);

            //Rutas de archivos
            File fichero = new File(getRuta());
            //System.out.println("Ruta del log: " + fichero.getAbsolutePath());

            //Verifica si existe la carpeta Logs, si no existe, la Crea
            File directorio = new File(fichero.getParent());
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("*");
                    System.out.println("Crea el directorio donde almacenara el Log de la prueba: "+fichero.getParent());
                    System.out.println("*");
                }
            }
            
            /////Esta seccion se encarga de Crear y escribir en el Log/////
            //verificarSizeFichero();

            /*Si es un nuevo Test se ejecuta el siguiente codigo, tomando en cuenta que sea el primer
             * TestCase del Test actual*/

            //Si el fichero no Existe, lo creara y agregara el siguiente texto
            if(!fichero.exists()){
                BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write(fecha+getTabs(fecha)+getUsuario()+getTabs(getUsuario())+ Clase +getTabs(Clase)+ Metodo +getTabs(Metodo)+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
                bw.close();
                System.out.println("*"+ "\n");
                System.out.println("*"+ "\n");
                System.out.println("*"+ "\n");
                System.out.println("*"+ "\n");
                System.out.println("*"+ "\n");
                System.out.println(fecha+getTabs(fecha)+getUsuario()+getTabs(getUsuario())+ Clase +getTabs(Clase)+ Metodo +getTabs(Metodo)+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
            }else{
                if(getLogtext()==1){
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fichero.getAbsoluteFile(), true));
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    //bw.write("\n");
                    bw.write(fecha+getTabs(fecha)+getUsuario()+getTabs(getUsuario())+ Clase +getTabs(Clase)+ Metodo +getTabs(Metodo)+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
                    bw.close();
                    //System.out.println("*"+ "\n");
                    //System.out.println("*"+ "\n");
                    //System.out.println("*"+ "\n");
                    //System.out.println("*"+ "\n");
                    //System.out.println("*"+ "\n");
                    System.out.println("\n");
                    System.out.println(fecha+getTabs(fecha)+getUsuario()+getTabs(getUsuario())+ Clase +getTabs(Clase)+ Metodo +getTabs(Metodo)+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
                }else{
                    //Agrega en el fichero el Log
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fichero.getAbsoluteFile(), true));
                    bw.write("\n");
                    bw.write(fecha+getTabs(fecha)+getUsuario()+getTabs(getUsuario())+ Clase +getTabs(Clase)+ Metodo +getTabs(Metodo)+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
                    bw.close();
                    System.out.println("\n");
                    System.out.println(fecha+getTabs(fecha)+getUsuario()+getTabs(getUsuario())+ Clase +getTabs(Clase)+ Metodo +getTabs(Metodo)+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");

                }
            }
        }catch (Exception e){
            System.out.println("Exepcion capturada en el metodo Metodo por medio del cual se escribir el log del Text");
            System.out.println("Tipo de Excepción : "+e.getClass());
            System.out.println("Causa de la Exepción : "+e.getCause());
            System.out.println("Mensaje de la Exepción : "+e.getMessage());
            System.out.println("Trace de la Exepción : "+e.getStackTrace());
        }

    }


    /****
     * Reporta al log del sistema
     * @param nivelLog NivelLog del texto que se desea reportar
     * @param Texto Texto que se desea Reportar
     * @param NameClass Nombre de la clase que llama al metodo encargado de escribir el Log
     */
    private static void writeLogRegistrador(NivelLog nivelLog, String Texto, String NameClass){
        // Create a Logger
        Logger logger = Logger.getLogger(NameClass);

        // log messages using log(Level level, String msg)
        if(nivelLog==NivelLog.TRACE){
            //La salida mas detallada voluminosamente posible
            logger.log(Level.FINEST, Texto);
        }

        //Salida no tan detallada
        if(nivelLog==NivelLog.DEBUG){
            logger.log(Level.FINE, Texto);
        }

        if(nivelLog==NivelLog.INFO){
            logger.log(Level.INFO, Texto);
        }

        if(nivelLog==NivelLog.WARNING){
            logger.log(Level.WARNING, Texto);
        }
        if(nivelLog==NivelLog.ERROR){
            logger.log(Level.SEVERE, Texto);
        }
        if(nivelLog==NivelLog.FATAL){
            logger.log(Level.SEVERE, Texto);
        }
    }


    /***
     * Obtiene la cantidad de veces que se a escrito en el Txt En la ejecución actual
     * @return Retorna la cantidad de veces que se a escrito en el Log.
     */
    private static long getLogtext() {
        return logtext;
    }

    /***
     * Setea la cantidad de veces que se a escrito en el Log actual.
     * @param Logtext Numero de veces que se a escrito en el Log.
     */
    private static void setLogtext(long Logtext) {
        try{
            Field field = MethodsTxt.class.getDeclaredField("logtext");
            field.setAccessible(true);
            field.set(null, Logtext);
            //logtext = logtext;
        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear el contador de las veces que se a escrito en " +
                    "el log " +Logtext);
            System.out.println("Tipo de Excepción : "+e.getClass());
            System.out.println("Causa de la Exepción : "+e.getCause());
            System.out.println("Mensaje de la Exepción : "+e.getMessage());
            System.out.println("Trace de la Exepción : "+e.getStackTrace());
        }

    }

    
}
