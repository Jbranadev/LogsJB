package com.josebran.LogsJB;

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

    /***
     * Metodo para escribir en el Log, lo que esta sucediendo dentro de la prueba,
     * imprime en consola el texto que sera agregado al Log
     * @param Texto Texto que deseamos que almacene en el Log
     */
    public static void writeLog(String nivelLog,String Texto){
        try{

            //Aumenta la Cantidad de Veces que se a escrito el Log
            setLogtext(getLogtext()+1);
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase = elements[2].getClassName();
            String metodo = elements[2].getMethodName();

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
                bw.write(convertir_fecha()+"    "+"    "+getUsuario()+"    "+clase+"    "+metodo+"    "+"    "+nivelLog+"    "+"    "+"    "+Texto+ "\n");
                bw.close();
            }else{
                if(getLogtext()==1){
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fichero.getAbsoluteFile(), true));
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write("*"+ "\n");
                    bw.write(convertir_fecha()+"    "+"    "+getUsuario()+"    "+clase+"    "+metodo+"    "+"    "+nivelLog+"    "+"    "+"    "+Texto+ "\n");
                    bw.close();
                }else{
                    //Agrega en el fichero el Log
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fichero.getAbsoluteFile(), true));
                    //System.out.println(convertir_fecha()+"    "+Texto);
                    bw.write(convertir_fecha()+"    "+"    "+getUsuario()+"    "+clase+"    "+metodo+"    "+"    "+nivelLog+"    "+"    "+"    "+Texto+ "\n");
                    bw.close();
                }
            }
        }catch (Exception ex){
            System.out.println("Excepcion capturada al escribir el log del Text " +nivelLog+": "+ex.getMessage());
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
