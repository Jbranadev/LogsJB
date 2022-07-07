package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;

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
import java.util.logging.Level;
import java.util.logging.Logger;


public class Methods {
    //Obtengo el Usuario de Windows
    private static String usuario=System.getProperty("user.name");

    private static int logtext =0;

    private static String ruta= (Paths.get("").toAbsolutePath().normalize().toString()+"/Logs/"+convertir_fecha("dd-MM-YYYY") + "/Log.txt").replace("\\","/");

    private static String clase;

    private static String metodo;


    /***
     * Obtiene la fecha actual en formato dd/MM/YYYY HH:MM:SS
     * @return Retorna una cadena de texto con la fecha obtenida
     */
    private static String convertir_fecha(){
        String temp=null;
        //DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss SSS");
        //convertir_fecha()
        temp=formater.format(LocalDateTime.now());
        return temp;
    }

    /***
     * Obtiene la fecha en el formato indicado
     * @param formato Formato que se desea obtener la fecha
     * @return Retorna una cadena de texto con la fecha obtenida en el formato especificado.
     */
    private static String convertir_fecha(String formato){
        String temp=null;
        DateTimeFormatter formater = DateTimeFormatter.ofPattern(formato);
        temp=formater.format(LocalDateTime.now());
        return temp;
    }

    /**
     * metodo que retorna la cantidad de tabulaciones para el siguiente texto en la misma linea conforme
     * al la longitud de la cadena actual:
     * si la cadena es  igual o menor a 7 da 4 tabulaciones
     * si la cadena es igual o menor a 16 da 3 tabulaciones
     * si la cadena es  mayor a 16 da 2 tabulaciones.
     * @param cadena texto a evaluar para obtener la separcion de tabulaciones de acuerdo al algoritmo definido.
     * @return retorna un string con la cantidad de tabulaciones respecto al siguiente texto en la misma linea.
     */
    private static String getTabs(String cadena) {
        //Reglas del negocio, maximas tabulaciones son 4
        //Minima tabulacion es una
        String result = "";
        String tab = "\u0009";
        //Si la cadena es menor a 8, retornara 4 tabs
        if(cadena.length()<8){
            for(int i=0;i<4;i++){
                result=result+tab;
            }
            //Si la cadena es menor a 17, retornara 3 tabs
        }else if(cadena.length()<17){
            for(int i=0;i<3;i++){
                result=result+tab;
            }
            //Si la cadena es menor a 25, retornara 2 tabs
        }else if(cadena.length()>16){
            for(int i=0;i<2;i++){
                result=result+tab;
            }
        }
        return result;
    }


    private static synchronized void verificarSizeFichero(){
        try {
            File logactual = new File(getRuta());
            //Devuelve el tamaño del fichero en Kb
            long sizeFichero=(logactual.length())/1024;
            //System.out.println("Tamaño del archivo en Kb: " +sizeFichero);

            //5120Kb es la cantidad maxima de quilobytes de un archivo TXT
            if(sizeFichero>5000){//Dejamos 120Kb de margen
                BasicFileAttributes attributes = null;
                String fechaformateada="";
                int numeroaleatorio=0;
                try { attributes = Files.readAttributes(logactual.toPath(), BasicFileAttributes.class);
                    FileTime time = attributes.creationTime();

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
                }else{
                    System.out.println(logactual.toPath()+" No se pudo renombrar el archivo: " +newrute);
                }
            }

        }catch (Exception e){
            System.out.println("Excepcion capturada al verificar el tamaño del archivo: " +getRuta());
        }
    }

    /***
     * Metodo para escribir en el Log, lo que esta sucediendo dentro de la prueba,
     * imprime en consola el texto que sera agregado al Log
     * @param Texto Texto que deseamos que almacene en el Log
     */
    public static synchronized void writeLog(NivelLog nivelLog, String Texto){
        try{
            String tab = "\u0009";
            //Aumenta la Cantidad de Veces que se a escrito el Log
            setLogtext(getLogtext()+1);


            //System.out.println("clase: " + getClase() + " metodo: " + getMetodo());

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
            verificarSizeFichero();

            /*Si es un nuevo Test se ejecuta el siguiente codigo, tomando en cuenta que sea el primer
             * TestCase del Test actual*/

            //Si el fichero no Existe, lo creara y agregara el siguiente texto
            if(!fichero.exists()){
                BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
                System.out.println("*"+ "\n");
                System.out.println("*"+ "\n");
                System.out.println("*"+ "\n");
                System.out.println("*"+ "\n");
                System.out.println("*"+ "\n");
                System.out.println(convertir_fecha()+getTabs(convertir_fecha())+getUsuario()+getTabs(getUsuario())+ getClase() +getTabs(getClase())+ getMetodo() +getTabs(getMetodo())+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write(convertir_fecha()+getTabs(convertir_fecha())+getUsuario()+getTabs(getUsuario())+ getClase() +getTabs(getClase())+ getMetodo() +getTabs(getMetodo())+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
                bw.close();
            }else{
                if(getLogtext()==1){
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fichero.getAbsoluteFile(), true));
                    System.out.println("*"+ "\n");
                    System.out.println("*"+ "\n");
                    System.out.println("*"+ "\n");
                    System.out.println("*"+ "\n");
                    System.out.println("*"+ "\n");
                    System.out.println("\n");
                    System.out.println(convertir_fecha()+getTabs(convertir_fecha())+getUsuario()+getTabs(getUsuario())+ getClase() +getTabs(getClase())+ getMetodo() +getTabs(getMetodo())+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    //bw.write("\n");
                    bw.write(convertir_fecha()+getTabs(convertir_fecha())+getUsuario()+getTabs(getUsuario())+ getClase() +getTabs(getClase())+ getMetodo() +getTabs(getMetodo())+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");                    bw.close();
                }else{
                    //Agrega en el fichero el Log
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fichero.getAbsoluteFile(), true));
                    bw.write("\n");
                    bw.write(convertir_fecha()+getTabs(convertir_fecha())+getUsuario()+getTabs(getUsuario())+ getClase() +getTabs(getClase())+ getMetodo() +getTabs(getMetodo())+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
                    bw.close();
                    System.out.println("\n");
                    System.out.println(convertir_fecha()+getTabs(convertir_fecha())+getUsuario()+getTabs(getUsuario())+ getClase() +getTabs(getClase())+ getMetodo() +getTabs(getMetodo())+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");

                }
            }
            //writeLogRegistrador(nivelLog, Texto, clase);

        }catch (Exception ex){
            System.out.println("Excepcion capturada al escribir el log del Text " +nivelLog.toString()+": "+ex.getMessage());
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
     * Obtiene el usuario del sistema sobre el cual corre la aplicación
     * @return Retorna un String con el nombre del usuario actual.
     */
    public static String getUsuario() {
        return usuario;
    }

    /***
     * Setea el nombre del usuario del sistema sobre el cual corre la aplicación
     * @param Usuario Usuario actual del sistema que se desea indicar al Log.
     */
    public static void setUsuario(String Usuario){
        try{
            Field field = Methods.class.getDeclaredField("usuario");
            field.setAccessible(true);
            field.set(null, Usuario);
        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear el usuario del entorno actual "+Usuario);
        }

    }

    /***
     * Obtiene la cantidad de veces que se a escrito en el Txt En la ejecución actual
     * @return Retorna la cantidad de veces que se a escrito en el Log.
     */
    private static int getLogtext() {
        return logtext;
    }

    /***
     * Setea la cantidad de veces que se a escrito en el Log actual.
     * @param Logtext Numero de veces que se a escrito en el Log.
     */
    private static void setLogtext(int Logtext) {
        try{
            Field field = Methods.class.getDeclaredField("logtext");
            field.setAccessible(true);
            field.set(null, Logtext);
            //logtext = logtext;
        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear el contador de las veces que se a escrito en " +
                    "el log " +Logtext);
        }

    }



    /***
     * Obtiene la ruta donde se estara escribiendo el Log.
     * @return Retorna un String con la ruta del archivo .Txt donde se estara escribiendo el Log.
     */
    public static String getRuta() {
        return ruta;
    }

    /**
     * Setea la ruta en la cual se desea que escriba el Log.
     * @param Ruta Ruta del archivo .Txt donde se desea escribir el Log.
     */
    public static void setRuta(String Ruta) {
        try{
            Field field = Methods.class.getDeclaredField("ruta");
            field.setAccessible(true);
            field.set(null, Ruta);
            //Methods.ruta = Ruta;
        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear la ruta del log " +Ruta);
        }

    }

    /***
     * Obtiene el nombre de la clase que actualmente esta llamando al Log
     * @return Retorna el nombre de la clase que esta invocando la escritura del Log
     */
    public static String getClase() {
        return clase;
    }

    /***
     * Setea el nombre de la clase que esta haciendo el llamado al metodo que escribe el Log.
     * @param Clase Nombre de la clase que llama al metodo que escribe el Log.
     */
    public static void setClase(String Clase){
        try{
            Field field = Methods.class.getDeclaredField("clase");
            field.setAccessible(true);
            field.set(null, Clase);
            //Methods.clase = clase;
        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear la clase que llama el log " +Clase);
        }
    }

    /**
     * Obtiene el nombre del metodo que actualmente esta llamando al Log
     * @return Retorna el nombre del metodo que esta invocando la escritura del Log
     */
    public static String getMetodo() {
        return metodo;
    }

    /**
     * Setea el nombre del metodo que esta haciendo el llamado al metodo que escribe el Log.
     * @param Metodo Nombre del metodo que llama al metodo que escribe el Log.
     */
    public static void setMetodo(String Metodo){
        try{
            Field field = Methods.class.getDeclaredField("metodo");
            field.setAccessible(true);
            field.set(null, Metodo);
            //Methods.metodo = metodo;
        }catch (Exception e){
            System.out.println("Excepcion capturada al tratar de setear el metodo que llama el log " +Metodo);
        }

    }
}
