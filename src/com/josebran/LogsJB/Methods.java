package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Methods {
    //Obtengo el Usuario de Windows
    private static String usuario=System.getProperty("user.name");

    private static int logtext =0;


    /***
     * Obtiene la fecha actual en formato dd/MM/YYYY HH:MM:SS
     * @return Retorna una cadena de texto con la fecha obtenida
     */
    public static String convertir_fecha(){
        String temp=null;
        //DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        //convertir_fecha()
        temp=formater.format(LocalDateTime.now());
        return temp;
    }

    /**
     * metodo que retorna la cantidad de tabulaciones para el siguiente texto en la misma linea conforme
     * al la longitud de la cadena actual:
     * si la cadena es  igual o menor a 7 da 4 tabulaciones
     * si la cadena es igual o menor a 16 da 3 tabulaciones
     * si la cadena es igual o menor a 24 da 2 tabulaciones
     * si la cadena es  mayor a 24 da 1 tab.
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
        }else if(cadena.length()<25){
            for(int i=0;i<2;i++){
                result=result+tab;
            }
            //Si la cadena es mayor a 24, retornara 1 tabs
        }else if(cadena.length()>24){
            for(int i=0;i<1;i++){
                result=result+tab;
            }
        }
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

            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase = elements[2].getClassName();
            String metodo = elements[2].getMethodName();
            System.out.println("clase: " + clase + " metodo: " + metodo);

            //Rutas de archivos
            String ruta ="C:/Reportes/Logs/Log"+".txt";
            //Verifica si existe la carpeta Logs, si no existe, la Crea
            File directorio = new File("C:/Reportes/Logs");
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("*");
                    System.out.println("Crea el directorio donde almacenara el Log de la prueba");
                    System.out.println("*");
                }
            }
            
            /////Esta seccion se encarga de Crear y escribir en el Log/////
            File fichero = new File(ruta);

            /*Si es un nuevo Test se ejecuta el siguiente codigo, tomando en cuenta que sea el primer
             * TestCase del Test actual*/

            //Si el fichero no Existe, lo creara y agregara el siguiente texto
            if(!fichero.exists()){
                BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
                //System.out.println(convertir_fecha()+"    "+Texto);
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write("*"+ "\n");
                bw.write(convertir_fecha()+getTabs(convertir_fecha())+getUsuario()+getTabs(getUsuario())+clase+getTabs(clase)+metodo+getTabs(metodo)+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
                bw.close();
            }else{
                if(getLogtext()==1){
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fichero.getAbsoluteFile(), true));
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write(convertir_fecha()+getTabs(convertir_fecha())+getUsuario()+getTabs(getUsuario())+clase+getTabs(clase)+metodo+getTabs(metodo)+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");                    bw.close();
                }else{
                    //Agrega en el fichero el Log
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fichero.getAbsoluteFile(), true));
                    //System.out.println(convertir_fecha()+"    "+Texto);
                    bw.write(convertir_fecha()+getTabs(convertir_fecha())+getUsuario()+getTabs(getUsuario())+clase+getTabs(clase)+metodo+getTabs(metodo)+nivelLog+getTabs(nivelLog.toString())+Texto+ "\n");
                    bw.close();
                }
            }
        }catch (Exception ex){
            System.out.println("Excepcion capturada al escribir el log del Text " +nivelLog.toString()+": "+ex.getMessage());
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
}
