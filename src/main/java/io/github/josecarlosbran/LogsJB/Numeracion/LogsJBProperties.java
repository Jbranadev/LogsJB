package io.github.josecarlosbran.LogsJB.Numeracion;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Numeración que sirve para indicar las propiedades de conexión.
 */
public enum LogsJBProperties {

    /**
     * Tipo de BD's a la cual se conectara.
     */
    LogsJBDBTYPE("LogsJBDataBase"),

    /**
     * Host en el cual se encuentra la BD's a la cual se conectara.
     */
    LogsJBDBHOST("LogsJBDataBaseHost"),

    /**
     * Puerto en el cual esta escuchando la BD's a la cual nos vamos a conectar.
     */
    LogsJBDBPORT("LogsJBDataBasePort"),

    /**
     * Usuario con el que se establecera conexión a la BD's
     */
    LogsJBDBUSER("LogsJBDataBaseUser"),
    /**
     * Contraseña del Usuario con el que se establecera conexión a la BD's
     */
    LogsJBDBPASSWORD("LogsJBDataBasePassword"),

    /**
     * Propiedades extra para la url de conexión a BD's por ejemplo
     * autoReconnect=true
     */
    LogsJBDBPROPERTIESURL("LogsJBDBpropertisUrl"),

    /**
     * Nombre de la BD's a la cual nos conectaremos.
     */
    LogsJBDBNAME("LogsJBDataBaseBD"),


    /**
     * Url del endpoint que estara escuchando las solicitudes de escribir el Log en un Servidor
     */
    LogsJBUrlLogRest("LogsJBurlLogRest"),

    /**
     * Clave con la cual se debera de autenticar para envíar los Logs a un RestAPI
     */
    LogsJBKeyLogRest("LogsJBkeyLogRest"),

    /**
     * Tipo de autenticación que se índica para consumir el RestAPI
     */
    LogsJBTypeAutenticatiosRest("LogsJBtypeAutenticationRest"),
    /**
     * Bandera que índica a LogsJB si se escribirá el log en el archivo TXT
     */
    LogsJBWriteTxt("LogsJBwriteTxt"),

    /**
     * Bandera que índica a LogsJB si se enviaran los logs a un RestAPI
     */
    LogsJBWriteRestApi("LogsJBwriteRestAPI"),

    /**
     * Bandera que índica a LogsJB si se escribirá el log en BD's
     */

    LogsJBWriteDB("LogsJBwriteDB"),


    /**
     * Tamaño maximo del archivo sobre el cual se estara escribiendo el Log.
     */
    LogsJBSizeLog("LogsJBSizeLog"),

    /**
     * Nivel Log desde el cual hacía arriba en la jerarquia de logs, deseamos se reporten
     */
    LogsJBNivelLog("LogsJBNivelLog"),


    /**
     * Ruta del archivo .Txt donde se desea escribir el Log.
     */
    LogsJBRutaLog("LogsJBRutaLog");


    /**
     * Setea la propiedad de la numeración
     *
     * @param property Propiedad que se setea a la numeración
     */
    private void setProperty(String property) {
        this.property = property;
    }

    /**
     * Obtiene la propiedad que posee la numeración
     *
     * @return Propiedad que posee la numeración
     */
    public String getProperty() {
        return property;
    }

    /**
     * Indica la propieda que se estara setiando
     */
    private String property;

    private LogsJBProperties(String property) {
        this.setProperty(property);
    }


}
