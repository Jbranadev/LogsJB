package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;


class Methods {
    //Obtengo el Usuario de Windows
    private static String usuario=System.getProperty("user.name");

    private static int logtext =0;

    private static String ruta1 ="C:/Reportes/Logs/Log"+".txt";
    private static String ruta= (Paths.get("").toAbsolutePath().normalize().toString()+"/Log.txt").replace("\\","/");

    private static String clase;

    private static String metodo;


    /***
     * Obtiene la fecha actual en formato dd/MM/YYYY HH:MM:SS
     * @return Retorna una cadena de texto con la fecha obtenida
     */
    public static String convertir_fecha(){
        String temp=null;
        //DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //convertir_fecha()
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
    public static String getTabs(String cadena) {
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


        /*
        else if(cadena.length()<25){
            for(int i=0;i<2;i++){
                result=result+tab;
            }
            //Si la cadena es mayor a 24, retornara 1 tabs
        }else if(cadena.length()>24){
            for(int i=0;i<1;i++){
                result=result+tab;
            }
        }*/
        return result;
    }

    /***
     * Metodo para escribir en el Log, lo que esta sucediendo dentro de la prueba,
     * imprime en consola el texto que sera agregado al Log
     * @param Texto Texto que deseamos que almacene en el Log
     */
    public static void writeLog(NivelLog nivelLog, String Texto){
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


    public static void writeLogRegistrador(NivelLog nivelLog, String Texto, String NameClass){
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

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String Usuario) throws NoSuchFieldException, IllegalAccessException {
        Field field = Methods.class.getDeclaredField("usuario");
        field.setAccessible(true);
        field.set(null, Usuario);
    }

    public static int getLogtext() {
        return logtext;
    }

    public static void setLogtext(int Logtext) throws IllegalAccessException, NoSuchFieldException {
        Field field = Methods.class.getDeclaredField("logtext");
        field.setAccessible(true);
        field.set(null, Logtext);
        //logtext = logtext;
    }

    public static String getRuta1() {
        return ruta1;
    }

    public static void setRuta1(String ruta1) {
        Methods.ruta1 = ruta1;
    }

    /***
     * Definimos la ruta de los Logs Por defecto, en base al directorio actual de trabajo
     */
    public static String getRuta() {
        return ruta;
    }

    public static void setRuta(String Ruta) throws NoSuchFieldException, IllegalAccessException {
        Field field = Methods.class.getDeclaredField("ruta");
        field.setAccessible(true);
        field.set(null, Ruta);
        //Methods.ruta = Ruta;
    }

    public static String getClase() {
        return clase;
    }

    public static void setClase(String Clase) throws NoSuchFieldException, IllegalAccessException {
        Field field = Methods.class.getDeclaredField("clase");
        field.setAccessible(true);
        field.set(null, Clase);
        //Methods.clase = clase;
    }

    public static String getMetodo() {
        return metodo;
    }

    public static void setMetodo(String Metodo) throws IllegalAccessException, NoSuchFieldException {
        Field field = Methods.class.getDeclaredField("metodo");
        field.setAccessible(true);
        field.set(null, Metodo);
        //Methods.metodo = metodo;
    }
}
