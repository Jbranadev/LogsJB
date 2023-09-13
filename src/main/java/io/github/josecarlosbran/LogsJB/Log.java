package io.github.josecarlosbran.LogsJB;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que cumple la función de un POJO para el envío de los Logs a un RestAPI
 */
public class Log {

    @Getter
    @Setter
    private Integer Id;

    @Getter
    @Setter
    private String Texto;

    @Getter
    @Setter
    private String NivelLog;

    @Getter
    @Setter
    private String Clase;

    @Getter
    @Setter
    private String Metodo;

    @Getter
    @Setter
    private String Fecha;

}
