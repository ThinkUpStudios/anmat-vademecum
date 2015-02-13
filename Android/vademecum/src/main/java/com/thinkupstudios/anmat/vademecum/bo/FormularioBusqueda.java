package com.thinkupstudios.anmat.vademecum.bo;

import java.io.Serializable;

/**
 * Created by FaQ on 12/02/2015.
 */
public class FormularioBusqueda implements Serializable {
    private String laboratorio;
    private String nombreGenerico;
    private String nombreComercial;

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }
}
