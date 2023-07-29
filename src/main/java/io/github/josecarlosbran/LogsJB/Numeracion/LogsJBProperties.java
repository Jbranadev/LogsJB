package io.github.josecarlosbran.LogsJB.Numeracion;

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
     * ?autoReconnect=true&useSSL=false
     */
    LogsJBDBPROPERTIESURL("LogsJBDBpropertisUrl"),

    /**
     * Nombre de la BD's a la cual nos conectaremos.
     */
    LogsJBDBNAME("LogsJBDataBaseBD"),


    LogsJBUrlLogRest("LogsJBurlLogRest"),

    LogsJBKeyLogRest("LogsJBkeyLogRest"),


    LogsJBTypeAutenticatiosRest("LogsJBtypeAutenticationRest"),

    LogsJBWriteTxt("LogsJBwriteTxt"),

    LogsJBWriteRestApi("LogsJBwriteRestAPI"),


    LogsJBWriteDB("LogsJBwriteDB"),


    LogsJBSizeLog("LogsJBSizeLog"),


    LogsJBNivelLog("LogsJBNivelLog"),


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
     * @return Propiedad que posee la numeración
     */
    public String getProperty() {
        return property;
    }

    /**
     * Indica la propieda que se estara setiando
     */
    private String property;

    private LogsJBProperties(String property){
        this.setProperty(property);
    }


}
