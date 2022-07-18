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


import com.josebran.LogsJB.Mensajes.ListaMensajes;
import com.josebran.LogsJB.Mensajes.MensajeWrite;
import com.josebran.LogsJB.Numeracion.NivelLog;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.josebran.LogsJB.MethodsTxt.verificarSizeFichero;
import static com.josebran.LogsJB.MethodsTxt.writeLog;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase encargada de recuperar los mensajes de la lista compartida por el Proceso Principal
 * e iniciar un SubProceso encargado de leer los mensajes de la lista y escribirlos en el LogTxt.
 */
class Execute {

    /***
     * Lista que funciona como la cola de peticiones que llegan al Ejecutor
     */
    private static ListaMensajes listado=new ListaMensajes();



    /***
     * Se utiliza el patron Singleton, para asegurarnos que sea una unica instancia la que se encargue de
     * Llevar el control de los Logs
     */
    private static Execute instance = new Execute();

    private Execute() {

    }

    /***
     * Proporciona la instancia de la clase encargada de ejecutar las acciónes en segundo Plano.
     * @return Retorna la instancia de la Clase Execute, que estara trabajando las peticiones de la aplicación
     * en segundo plano.
     */
    protected static Execute getInstance() {
        return instance;
    }


    /***
     * Proporciona el acceso a la lista que sirve como cola de las peticiones
     * @return Retorna una lista de MensajeWrite, la cual lleva la información que se desea registrar en los Logs
     */
    protected static ListaMensajes getListado() {
        return listado;
    }


    /***
     * Metodo por medio del cual se llama la escritura de los logs
     */
    protected void run(){
        try{
            writePrincipal();
        }catch (Exception e){
            System.out.println("Exepcion capturada en el metodo Metodo por medio del cual se llama la escritura de los logs");
            System.out.println("Tipo de Excepción : "+e.getClass());
            System.out.println("Causa de la Exepción : "+e.getCause());
            System.out.println("Mensaje de la Exepción : "+e.getMessage());
            System.out.println("Trace de la Exepción : "+e.getStackTrace());
        }
    }

    /***
     * Escritor principal, es el que maneja la logica de la aplicación la cual decide si el log se almacena en una BD's,
     * un Txt Ó si se envía a un RestAPI.
     */
    private void writePrincipal(){
        try{
            Runnable EscritorPrincipal= ()->{
                String temporal="";
                boolean band=true;
                while(band){
                    NivelLog nivel=LogsJB.getGradeLog();
                    //String Mensaje=Execute.getInstance().getTexto();
                    //NivelLog logtemporal=Execute.getInstance().getNivelLog();
                    MensajeWrite mensajetemp=null;
                    mensajetemp=getListado().getDato();
                    //System.out.println("Mensaje en Execute: "+mensajetemp.getTexto()+" "+mensajetemp.getNivelLog());
                    if(Objects.isNull(mensajetemp)){
                        band=false;
                        break;
                        //return;
                    }
                    String Mensaje=mensajetemp.getTexto();
                    NivelLog logtemporal=mensajetemp.getNivelLog();
                    String Clase= mensajetemp.getClase();
                    String Metodo= mensajetemp.getMetodo();
                    String fecha= mensajetemp.getFecha();

                    //System.out.println("NivelLog definido: "+nivelaplicación);
                    //System.out.println("NivelLog temporal: "+intniveltemporal);
                    //System.out.println("Cantidad de mensajes Por limpiar: "+getListaTxt().getSize());
                    //Verifica que el nivel de Log a escribir sea igual o mayor al nivel predefinido.
                    /*if(logtemporal.getGradeLog()>=nivel.getGradeLog()){

                    }*/
                    if(!temporal.equals(Mensaje)){
                        //verificarSizeFichero();
                        //writeLog(logtemporal, Mensaje, Clase, Metodo);
                        writeTXT(logtemporal, Mensaje, Clase, Metodo, fecha);
                    }else{

                    }
                    temporal=Mensaje;


                    //System.out.println("Cantidad de mensajes Por limpiar: "+getListado().getSize());
                    if(getListado().getSize()==0){
                        band=false;
                        break;
                    }
                }
                return;
            };
            ExecutorService executorPrincipal = Executors.newFixedThreadPool(1);
            executorPrincipal.submit(EscritorPrincipal);
            executorPrincipal.shutdown();
        }catch (Exception e){
            System.out.println("Exepcion capturada en el metodo Escritor principal, es el que maneja la logica de la aplicación la cual decide si el log se almacena en una BD's,\n" +
                    "     * un Txt Ó si se envía a un RestAPI.");
            System.out.println("Tipo de Excepción : "+e.getClass());
            System.out.println("Causa de la Exepción : "+e.getCause());
            System.out.println("Mensaje de la Exepción : "+e.getMessage());
            System.out.println("Trace de la Exepción : "+e.getStackTrace());

        }
    }


    /****
     * Metodo encargado de crear los subprocesos encargados de escribir los logs en los TXT.
     * Crea un hilo que verifica el tamaño del archivo y otro que se encarga de escribir en el archivo.
     * @param logtemporal Nivel de Log especificado para el texto que se escribira.
     * @param Mensaje Texto que se desea escribir en el Log.
     * @param Clase Clase a la que pertenece el metodo el cual inicia el proceso de registro del Log.
     * @param Metodo Metodo desde que se manda a llamar el Log.
     * @param fecha fecha y hora de la escritura del Log.
     */
    private void writeTXT(NivelLog logtemporal, String Mensaje, String Clase, String Metodo, String fecha){
        try{
            //System.out.println("El valor es igual o mayor al nivel de la aplicación: "+intniveltemporal);
            //System.out.println("NivelLog 2: "+logtemporal);
            //System.out.println("Texto 2: "+Mensaje);
            //System.out.println("Temporal: "+temporal);

            Runnable verificarTxt= ()->{
                //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
                //Verifica el tamaño del archivo
                verificarSizeFichero();
            };
            ExecutorService verificarsize = Executors.newFixedThreadPool(1);
            verificarsize.submit(verificarTxt);
            verificarsize.shutdown();

            Runnable writeTxt= ()->{
                //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
                //Ejecuta la escritura en el archivo Log
                writeLog(logtemporal, Mensaje, Clase, Metodo, fecha);

            };
            ExecutorService executorTxt = Executors.newFixedThreadPool(1);
            executorTxt.submit(writeTxt);
            executorTxt.shutdown();
            return;
        }catch (Exception e){
            System.out.println("Exepcion capturada en el metodo encargado de crear los subprocesos encargados de escribir los logs en los TXT.\n" +
                    "     * Crea un hilo que verifica el tamaño del archivo y otro que se encarga de escribir en el archivo.");
            System.out.println("Tipo de Excepción : "+e.getClass());
            System.out.println("Causa de la Exepción : "+e.getCause());
            System.out.println("Mensaje de la Exepción : "+e.getMessage());
            System.out.println("Trace de la Exepción : "+e.getStackTrace());
        }
    }

}
