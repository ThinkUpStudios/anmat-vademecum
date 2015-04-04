package com.thinkupstudios.anmat.vademecum.aplicacion;

import android.app.Application;

import com.thinkupstudios.anmat.vademecum.providers.GenericProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;
import com.thinkupstudios.anmat.vademecum.providers.tables.PrincipiosActivosTable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dcamarro on 27/02/2015.
 */
public class MiAplicacion extends Application {

    private List<String> nombresComerciales;
    private List<String> nombresGenericos;
    private List<String> laboratorios;
    private List<String> principiosActivos;
    private GenericProvider genericProvider;

    public List<String> getNombresComerciales() {
        return nombresComerciales;
    }

    public List<String> getNombresGenericos() {
        return nombresGenericos;
    }

    public List<String> getLaboratorios() {
        return laboratorios;
    }

    @Override
    public void onCreate() {
        this.genericProvider = new GenericProvider(new DatabaseHelper(this));
        FontsOverride.overrideFont(getApplicationContext(), "SERIF", "fonts/mi_fuente.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        this.nombresComerciales = this.genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_COMERCIAL);
        this.laboratorios = this.genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_LABORATORIO);
        this.nombresGenericos = this.genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_GENERICO);
        this.principiosActivos = this.genericProvider.getDistinctColumns(PrincipiosActivosTable.TABLE_NAME, PrincipiosActivosTable.COLUMN_NAME);


    }

    public List<String> getPrincipiosActivos() {
        return this.principiosActivos;
    }
}
