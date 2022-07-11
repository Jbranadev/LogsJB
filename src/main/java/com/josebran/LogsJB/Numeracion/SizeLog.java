package com.josebran.LogsJB.Numeracion;

public enum SizeLog {

    /***
     * El tamaño mas pequeño, que el pequeño,
     * Se definiria el tamaño de log en 125Mb.
     */
    Little_Little(125),

    /***
     * El tamaño pequeño del Log de una aplicación,
     * Se definiria el tamaño de log en 250Mb.
     */
    Little(250),

    /**
     * El tamaño mas pequeño que el mediano y mas grande que el pequeño del Log de una aplicación,
     * Se definiria el tamaño de log en 500Mb.
     */
    Small_Medium(500),

    /**
     * El tamaño mediano del Log de una aplicación,
     * Se definiría el tamaño de log en 1,000Mb.
     */
    Medium(1000),

    /**
     * El tamaño mas pequeño que el grande y mas grande que el mediano del Log de una aplicación,
     * Se definiria el tamaño de log en 2,000Mb
     */
    Small_Large(2000),

    /**
     * El tamaño grande del Log de una aplicación,
     * Se definiría el tamaño de log en 4,000Mb.
     */
    Large(4000);

    private int size;

    private SizeLog(int SizeLog){
        this.size=SizeLog;
    }

    /***
     * Retorna el tamaño expresado en MB definido para el fichero Log de la aplicación actual
     * @return Retorna un entero con el tamaño maximo definido para el fichero donde se escribe el Log.
     * Little_Little = 125Mb,
     * Little = 250Mb,
     * Small_Medium = 500Mb,
     * Medium = 1,000Mb,
     * Small_Large = 2,000Mb,
     * Large = 4,000Mb.
     */
    public int getSizeLog(){
        return this.size;
    }


}
