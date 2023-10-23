package io.github.josecarlosbran.LogsJB;


import io.github.josecarlosbran.JBSqlUtils.Anotations.ColumnDefined;
import io.github.josecarlosbran.JBSqlUtils.DataBase.JBSqlUtils;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Utilities.Column;
import io.github.josecarlosbran.LogsJB.Numeracion.LogsJBProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

/**
 * Clase que permite el almacenamiento de los Logs en BD's
 */
public class LogsJBDB extends JBSqlUtils {

    /**
     * Setea el nombre de la Base de Datos global a la que se conectaran los modelos que no tengan una configuración
     * personalizada.
     *
     * @param BD Nombre de la Base de Datos.
     */
    public static void setDataBaseGlobal(String BD) {
        try {
            System.setProperty(LogsJBProperties.LogsJBDBNAME.getProperty(), BD);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el nombre de la Base de Datos global: " + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Setea la Contraseña del usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param password Contraseña del usuario con el cual se conectara a la BD's.
     */
    public static void setPasswordGlobal(String password) {
        try {
            System.setProperty(LogsJBProperties.LogsJBDBPASSWORD.getProperty(), password);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea la contraseña del usuario de BD's global: " + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Setea el Usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param user Usuario con el cual se conectara a la BD's.
     */
    public static void setUserGlobal(String user) {
        try {
            System.setProperty(LogsJBProperties.LogsJBDBUSER.getProperty(), user);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el usuario de BD's global: " + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Setea el puerto global con el que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param port Puerto en el cual se encuentra escuchando la BD's a la cual se pegaran los modelos.
     */
    public static void setPortGlobal(String port) {
        try {
            System.setProperty(LogsJBProperties.LogsJBDBPORT.getProperty(), port);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el Puerto de BD's global: " + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Setea el host en el cual se encuentra la BD's global a la cual se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param host Host en el cual se encuentra la BD's a la que nos queremos conectar.
     */
    public static void setHostGlobal(String host) {
        try {
            System.setProperty(LogsJBProperties.LogsJBDBHOST.getProperty(), host);
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el Host de la BD's global: " + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }

    }

    /**
     * Setea el tipo de BD's global a la cual se estaran conectando los modelos que no tengan una configuración personalizada.
     *
     * @param dataBase Tipo de BD's a la cual se estaran los modelos que no tengan una configuración personalizada, los tipos disponibles son
     *                 MySQL,
     *                 SQLServer,
     *                 PostgreSQL,
     *                 SQLite.
     */
    public static void setDataBaseTypeGlobal(DataBase dataBase) {
        try {
            System.setProperty(LogsJBProperties.LogsJBDBTYPE.getProperty(), dataBase.name());
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el Tipo de BD's global: " + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }

    }


    /**
     * Setea las propiedades extra de conexion url DB que pueden utilizar los modelos para conectarse a BD's
     *
     * @param propertisUrl Propiedades extra para la url de conexion a BD's por ejemplo
     *                     ?autoReconnect=true
     */
    public static void setPropertisUrlConexionGlobal(String propertisUrl) {
        try {
            System.setProperty(LogsJBProperties.LogsJBDBPROPERTIESURL.getProperty(), propertisUrl);
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción disparada al setear las propiedades extra de conexión con la cual el modelo se conectara a la BD's: " + " Trace de la Excepción : " + ExceptionUtils.getStackTrace(e));
        }
    }


    /**
     * Constructor por default de la clase que se encarga de escribir los logs en BD's
     *
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las propiedades de conexión necesarias para conectarse a la BD's especificada
     */
    public LogsJBDB() throws DataBaseUndefind, PropertiesDBUndefined {
        super(false);
        this.setDataBaseType(setearDBType());
        this.setBD(setearBD());
        this.setHost(setearHost());
        this.setPort(setearPort());
        this.setUser(setearUser());
        this.setPassword(setearPassword());
        this.setPropertisURL(setearPropertisUrl());

    }

    /**
     * Constructor por default de la clase que se encarga de escribir los logs en BD's
     *
     * @param getPropetySystem Parametro que indica si obtendremos las variables del sistema desde las propiedades de JBSqlUtils
     * @throws DataBaseUndefind      Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las propiedades de conexión necesarias para conectarse a la BD's especificada
     */
    public LogsJBDB(Boolean getPropetySystem) throws DataBaseUndefind, PropertiesDBUndefined {
        super(false);
        if (getPropetySystem) {
            this.setDataBaseType(setearDBType());
            this.setBD(setearBD());
            this.setHost(setearHost());
            this.setPort(setearPort());
            this.setUser(setearUser());
            this.setPassword(setearPassword());
            this.setPropertisURL(setearPropertisUrl());
        }
    }


    /**
     * Setea el tipo de BD's al cual se estara conectando este modelo.
     *
     * @return Retorna el tipo de BD's al cual se estara conectando la BD's si esta definida
     * de lo contrario retorna NULL.
     * @throws DataBaseUndefind Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                          el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                          esta excepción, poder manejarla.
     */
    private DataBase setearDBType() throws DataBaseUndefind {
        //if (this.getGetPropertySystem()) {
        String dataBase = System.getProperty(LogsJBProperties.LogsJBDBTYPE.getProperty());
        if (stringIsNullOrEmpty(dataBase)) {
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new DataBaseUndefind("No se a seteado la DataBase que índica a que BD's deseamos se pegue JBSqlUtils");
        } else {
            if (dataBase.equals(DataBase.MySQL.name())) {
                setDataBaseType(DataBase.MySQL);
                return DataBase.MySQL;
            }
            if (dataBase.equals(DataBase.SQLite.name())) {
                setDataBaseType(DataBase.SQLite);
                return DataBase.SQLite;
            }
            if (dataBase.equals(DataBase.SQLServer.name())) {
                setDataBaseType(DataBase.SQLServer);
                return DataBase.SQLServer;
            }
            if (dataBase.equals(DataBase.PostgreSQL.name())) {
                setDataBaseType(DataBase.PostgreSQL);
                return DataBase.PostgreSQL;
            }
        }
        //}
        return null;
    }


    /**
     * Setea el Host en el cual se encuentra la BD's a la cual se conectara.
     *
     * @return Retorna el Host en el cual se encuentra la BD's, de no estar definido, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a definido el Host en el cual se encuentra la BD's, si el tipo
     *                               de BD's al cual se desea conectar es diferente a una BD's SQLite
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     */
    private String setearHost() throws PropertiesDBUndefined, DataBaseUndefind {
        //if (this.getGetPropertySystem()) {
        String host = System.getProperty(LogsJBProperties.LogsJBDBHOST.getProperty());
        if (this.getDataBaseType() != DataBase.SQLite) {
            if (stringIsNullOrEmpty(host)) {
                //Si la propiedad del sistema no esta definida, Lanza una Exepción
                throw new PropertiesDBUndefined("No se a seteado el host en el que se encuentra la BD's a la cual deseamos se pegue JBSqlUtils");
            }
        }
        return host;

        //}
    }


    /**
     * Setea el Puerto en el cual esta escuchando la BD's a la cual nos vamos a conectar.
     *
     * @return Retorna el Puerto en el cual se encuentra la BD's, de no estar definido, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a seteado el Puerto en el cual
     *                               se encuentra escuchando la BD's, si el tipo de BD's al cual se desea conectar es diferente a una BD's SQLite
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     */
    private String setearPort() throws PropertiesDBUndefined, DataBaseUndefind {
        //if (this.getGetPropertySystem()) {
        String port = System.getProperty(LogsJBProperties.LogsJBDBPORT.getProperty());
        if (this.getDataBaseType() != DataBase.SQLite) {
            if (stringIsNullOrEmpty(port)) {
                //Si la propiedad del sistema no esta definida, Lanza una Exepción
                throw new PropertiesDBUndefined("No se a seteado el puerto en el que se encuentra escuchando la BD's a la cual deseamos se pegue JBSqlUtils");
            }
        }
        return port;

        //}
    }


    /**
     * Setea el Usuario de la BD's a la cual nos conectaremos
     *
     * @return Retorna el Usuario con el cual se conectara la BD's, de no estar definido, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a seteado el Usuario con el cual
     *                               se conectara a la BD's
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     */
    private String setearUser() throws PropertiesDBUndefined, DataBaseUndefind {
        //if (this.getGetPropertySystem()) {
        String user = System.getProperty(LogsJBProperties.LogsJBDBUSER.getProperty());
        if (this.getDataBaseType() != DataBase.SQLite) {
            if (stringIsNullOrEmpty(user)) {
                //Si la propiedad del sistema no esta definida, Lanza una Exepción
                throw new PropertiesDBUndefined("No se a seteado el usuario de la BD's a la cual deseamos se pegue JBSqlUtils");
            }
        }
        return user;
        //}
    }


    /**
     * Setea el Nombre de la BD's a la cual nos conectaremos.
     *
     * @return Retorna el nombre de la BD's a la cual nos conectaremos, de no estar definido, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a seteado el Nombre de la BD's a la cual nos conectaremos.
     * @throws DataBaseUndefind      Lanza esta excepción cuando no se a configurado la BD's a la cual se conectara el modelo
     *                               el usuario de la librería es el encargado de setear el tipo de BD's a la cual se conectara el modelo, asi mismo de ser lanzada
     *                               esta excepción, poder manejarla.
     */
    private String setearBD() throws PropertiesDBUndefined {
        //if (this.getGetPropertySystem()) {
        String DB = System.getProperty(LogsJBProperties.LogsJBDBNAME.getProperty());
        //System.out.println("BD seteada en system property: " + DB);
        if (stringIsNullOrEmpty(DB)) {
            //Si la propiedad del sistema no esta definida, Lanza una Exepción
            throw new PropertiesDBUndefined("No se a seteado la BD's a la cual deseamos se pegue JBSqlUtils");
        }
        return DB;

        //}
    }

    /**
     * Setea la contraseña del usuario de la BD's a la cual nos conectaremos.
     *
     * @return Retorna la contraseña del usuario con el cual se conectara la BD's, de no estar definida, retorna NULL
     * @throws PropertiesDBUndefined Lanza esta excepción si no se a seteado la contraseña del usuario con el cual
     *                               se conectara a la BD's
     */
    private String setearPassword() throws PropertiesDBUndefined, DataBaseUndefind {
        //if (this.getGetPropertySystem()) {
        String password = System.getProperty(LogsJBProperties.LogsJBDBPASSWORD.getProperty());
        if (this.getDataBaseType() != DataBase.SQLite) {
            if (stringIsNullOrEmpty(password)) {
                //Si la propiedad del sistema no esta definida, Lanza una Exepción
                throw new PropertiesDBUndefined("No se a seteado la contraseña del usuario de la BD's a la cual deseamos se pegue JBSqlUtils");

            }
        }
        return password;
        //}
    }

    /**
     * Obtiene las propiedades de la url de conexión a la BD's
     *
     * @return Las propiedades de la url para la conexión a la BD's obtenida de las variables del sistema
     */
    private String setearPropertisUrl() {
        String property = System.getProperty(LogsJBProperties.LogsJBDBPROPERTIESURL.getProperty());
        return property;
    }


    @Getter
    @Setter
    @ColumnDefined(name = "Id", dataTypeSQL = DataType.INTEGER, constraints = {Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY})
    private Integer Id ;

    @Getter
    @Setter
    @ColumnDefined(name = "Texto", dataTypeSQL = DataType.VARCHAR)
    private String Texto ;

    @Getter
    @Setter
    @ColumnDefined(name = "NivelLog", dataTypeSQL = DataType.VARCHAR)
    private String NivelLog ;

    @Getter
    @Setter
    @ColumnDefined(name = "Clase", dataTypeSQL = DataType.VARCHAR)
    private String Clase ;


    @Getter
    @Setter
    @ColumnDefined(name = "Metodo", dataTypeSQL = DataType.VARCHAR)
    private String Metodo ;

    @Getter
    @Setter
    @ColumnDefined(name = "Fecha", dataTypeSQL = DataType.VARCHAR)
    private String Fecha ;


}
