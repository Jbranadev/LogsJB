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

package com.josebran.LogsJB.Numeracion;

public enum NivelLog {
    /**
     * DEBUG , REALIZA LA DEPURACION DE LA APLICACION.
     */
    DEBUG,
    /**
     * INFO, BRINDA INFORMACION DEL PROGESO Y ESTADO DE LA APLICACION.
     */
    INFO,
    /**
     * WARNING. BRINDA  UNA ADVERTENCIA DE UN EVENTO INESPERADO DE LA APLICACION
     */
    WARNING,
    /**
     * ERROR, NOTIFICA UN  ERROR GRAVE QUE DEBE VERIFICARSE.
     */
    ERROR,
    /**
     * FATAL, NOTIFICA UN FUNCIONAMIENTO ERRONEO EN LA APLICACION.
     */
    FATAL,
    /**
     * TRACE, NOTIFICA O MUESTRA UN DETALLE MAYOR QUE DEBUG.
     */
    TRACE

}
