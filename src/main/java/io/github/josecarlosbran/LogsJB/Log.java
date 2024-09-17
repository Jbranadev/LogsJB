package io.github.josecarlosbran.LogsJB;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que cumple la función de un POJO para el envío de los Logs a un RestAPI
 */
@Getter
@Setter
public class Log {

    private Integer Id;

    private String Texto;

    private String NivelLog;

    private String Clase;

    private String Metodo;

    private String Fecha;
}
