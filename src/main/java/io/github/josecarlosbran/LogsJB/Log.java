package io.github.josecarlosbran.LogsJB;

import lombok.Getter;
import lombok.Setter;

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
