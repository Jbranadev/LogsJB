package io.github.josecarlosbran.LogsJB;


import io.github.josecarlosbran.JBSqlUtils.Column;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.JBSqlUtils;
import lombok.Getter;
import lombok.Setter;

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
            System.setProperty("DataBaseBD", BD);
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBaseBD"));
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el nombre de la Base de Datos global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Setea la Contraseña del usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param password Contraseña del usuario con el cual se conectara a la BD's.
     */
    public static void setPasswordGlobal(String password) {
        try {
            System.setProperty("DataBasePassword", password);
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBasePassword"));
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea la contraseña del usuario de BD's global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Setea el Usuario global con la que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param user Usuario con el cual se conectara a la BD's.
     */
    public static void setUserGlobal(String user) {
        try {
            System.setProperty("DataBaseUser", user);
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBaseUser"));

        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el usuario de BD's global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Setea el puerto global con el que se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param port Puerto en el cual se encuentra escuchando la BD's a la cual se pegaran los modelos.
     */
    public static void setPortGlobal(String port) {
        try {
            System.setProperty("DataBasePort", port);
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBasePort"));

        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el Puerto de BD's global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }

    /**
     * Setea el host en el cual se encuentra la BD's global a la cual se conectaran los modelos que no tengan una configuración personalizada.
     *
     * @param host Host en el cual se encuentra la BD's a la que nos queremos conectar.
     */
    public static void setHostGlobal(String host) {
        try {
            System.setProperty("DataBaseHost", host);
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBaseHost"));
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el Host de la BD's global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
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
            System.setProperty("DataBase", dataBase.name());
            //System.out.println("SystemProperty Seteada: "+System.getProperty("DataBase"));
        } catch (Exception e) {
            LogsJB.fatal("Excepción disparada en el método que Setea el Tipo de BD's global: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }

    }


    /**
     * Setea las propiedades extra de conexion url DB que pueden utilizar los modelos para conectarse a BD's
     * @param propertisUrl Propiedades extra para la url de conexion a BD's por ejemplo
     *                     ?autoReconnect=true
     */
    public static void setPropertisUrlConexionGlobal(String propertisUrl){
        try {
            System.setProperty("DBpropertisUrl", propertisUrl);
        } catch (Exception e) {
            com.josebran.LogsJB.LogsJB.fatal("Excepción disparada al setear las propiedades extra de conexión con la cual el modelo se conectara a la BD's: " + e.toString());
            com.josebran.LogsJB.LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            com.josebran.LogsJB.LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            com.josebran.LogsJB.LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            com.josebran.LogsJB.LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
        }
    }


    /**
     * Constructor por default de la clase que se encarga de escribir los logs en BD's
     * @throws DataBaseUndefind Lanza esta excepción si en las propiedades del sistema no esta definida el tipo de BD's a la cual se conectara el modelo.
     * @throws PropertiesDBUndefined Lanza esta excepción si en las propiedades del sistema no estan definidas las propiedades de conexión necesarias para conectarse a la BD's especificada
     */
    public LogsJBDB() throws DataBaseUndefind, PropertiesDBUndefined {
        super();
    }


    @Getter @Setter
    private Column<Integer> Id=new Column<>(DataType.INTEGER, Constraint.AUTO_INCREMENT, Constraint.PRIMARY_KEY);
    @Getter @Setter
    private Column<String> Texto=new Column<>(DataType.VARCHAR);
    @Getter @Setter
    private Column<String> NivelLog=new Column<>(DataType.VARCHAR);
    @Getter @Setter
    private Column<String> Clase=new Column<>(DataType.VARCHAR);
    @Getter @Setter
    private Column<String> Metodo=new Column<>(DataType.VARCHAR);
    @Getter @Setter
    private Column<String> Fecha=new Column<>(DataType.VARCHAR);


    /*
    public Column<Integer> getId() {
        return Id;
    }

    public void setId(Column<Integer> id) {
        Id = id;
    }

    public Column<String> getTexto() {
        return Texto;
    }

    public void setTexto(Column<String> texto) {
        Texto = texto;
    }

    public Column<String> getNivelLog() {
        return NivelLog;
    }

    public void setNivelLog(Column<String> nivelLog) {
        NivelLog = nivelLog;
    }

    public Column<String> getClase() {
        return Clase;
    }

    public void setClase(Column<String> clase) {
        Clase = clase;
    }

    public Column<String> getMetodo() {
        return Metodo;
    }

    public void setMetodo(Column<String> metodo) {
        Metodo = metodo;
    }

    public Column<String> getFecha() {
        return Fecha;
    }

    public void setFecha(Column<String> fecha) {
        Fecha = fecha;
    }*/



}
