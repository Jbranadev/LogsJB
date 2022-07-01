package com.josebran.LogsJB.Numeracion;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;

import static java.awt.SystemColor.text;

public class Metodos {
    public String rutalog="C:\\pruebalog";
    public void readlogtxt(String texto)  {
       File archivo=new File(rutalog);

       //Comprobar si el directorio existe
        String directorio=archivo.getParent();


            System.out.println("ruta es " + archivo.getParent());


    }

}
