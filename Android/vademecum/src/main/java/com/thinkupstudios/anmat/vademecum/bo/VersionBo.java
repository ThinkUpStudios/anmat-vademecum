package com.thinkupstudios.anmat.vademecum.bo;

/**
 * Created by dcamarro on 23/04/2015.
 */
public class VersionBo {

    private Integer numero;
    private String ultimaActualizacion;

    public VersionBo(Integer numero, String ultimaActualizacion) {
        this.numero = numero;
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public String getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public Integer getNumero() {

        return numero;
    }


}
