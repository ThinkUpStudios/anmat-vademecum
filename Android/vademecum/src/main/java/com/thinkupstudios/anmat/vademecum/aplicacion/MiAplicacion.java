package com.thinkupstudios.anmat.vademecum.aplicacion;

import android.app.Application;

import java.util.List;

/**
 * Created by dcamarro on 27/02/2015.
 */
public class MiAplicacion extends Application {

    private List<String> nombresComerciales;
    private List<String> nombresGenericos;
    private List<String> laboratorios;

    public List<String> getNombresComerciales() {
        return nombresComerciales;
    }

    public void setNombresComerciales(List<String> nombresComerciales) {
        this.nombresComerciales = nombresComerciales;
    }

    public List<String> getNombresGenericos() {
        return nombresGenericos;
    }

    public void setNombresGenericos(List<String> nombresGenericos) {
        this.nombresGenericos = nombresGenericos;
    }

    public List<String> getLaboratorios() {
        return laboratorios;
    }

    public void setLaboratorios(List<String> laboratorios) {
        this.laboratorios = laboratorios;
    }

    @Override
    public void onCreate() {
        FontsOverride.overrideFont(getApplicationContext(), "SERIF", "fonts/mi_fuente.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }
}
