package com.thinkupstudios.anmat.vademecum.bo;

import java.io.Serializable;

/**
 * Created by FaQ on 12/02/2015.
 */
public class FormularioBusqueda implements Serializable {
    public static String FORMULARIO_MANUAL = "Formulario_Busqueda";
    public static String PRINCIPIO_ACTIVO = "Principio_Activo";
    private String laboratorio = new String();
    private String nombreGenerico = new String();
    private String nombreComercial = new String();
    private Boolean useLike = true;
    private Boolean filtrarPorFormula = false;

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

    public Boolean useLike() {
        return useLike;
    }

    public void setUseLike(Boolean useLike) {
        this.useLike = useLike;
    }

    public void setFiltrarPorFormula(Boolean filtrarPorFormula) {
        this.filtrarPorFormula = filtrarPorFormula;
    }

    public Boolean filtrarPorFormula(){
        return this.filtrarPorFormula;
    }

    public boolean isEmprty(){
        return  this.laboratorio.isEmpty()
                && this.nombreComercial.isEmpty()
                && this.nombreGenerico.isEmpty();
    }
}
