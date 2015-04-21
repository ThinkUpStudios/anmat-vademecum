package com.thinkupstudios.anmat.vademecum.aplicacion;

import android.app.Application;

import com.thinkupstudios.anmat.vademecum.providers.GenericProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;
import com.thinkupstudios.anmat.vademecum.providers.tables.PrincipiosActivosTable;

import java.util.List;

/**
 * Created by dcamarro on 27/02/2015.
 * Clase con funionalidad global a la aplicacion
 */
public class MiAplicacion extends Application {

    private List<String> nombresComerciales;
    private List<String> nombresGenericos;
    private List<String> laboratorios;
    private List<String> principiosActivos;


    public List<String> getNombresComerciales() {
        return nombresComerciales;
    }

    public List<String> getNombresGenericos() {
        return nombresGenericos;
    }

    public List<String> getLaboratorios() {
        return laboratorios;
    }

    public void setNombresComerciales(List<String> nombresComerciales) {
        this.nombresComerciales = nombresComerciales;
    }

    public void setNombresGenericos(List<String> nombresGenericos) {
        this.nombresGenericos = nombresGenericos;
    }

    public void setLaboratorios(List<String> laboratorios) {
        this.laboratorios = laboratorios;
    }

    public void setPrincipiosActivos(List<String> principiosActivos) {
        this.principiosActivos = principiosActivos;
    }

    @Override
    public void onCreate() {

        FontsOverride.overrideFont(getApplicationContext(), "SERIF", "fonts/mi_fuente.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf



    }

    public List<String> getPrincipiosActivos() {
        return this.principiosActivos;
    }
}
