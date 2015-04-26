package com.thinkupstudios.anmat.vademecum.aplicacion;

import android.app.Application;

import com.thinkupstudios.anmat.vademecum.bo.VersionBo;
import com.thinkupstudios.anmat.vademecum.providers.GenericProvider;
import com.thinkupstudios.anmat.vademecum.providers.PrincipioActivoProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;
import com.thinkupstudios.anmat.vademecum.providers.tables.PrincipiosActivosTable;

import java.util.List;
import java.util.Vector;

/**
 * Created by dcamarro on 27/02/2015.
 * Clase con funionalidad global a la aplicacion
 */
public class MiAplicacion extends Application {

    private List<String> nombresComerciales = new Vector<>();
    private List<String> nombresGenericos = new Vector<>();
    private List<String> laboratorios = new Vector<>();
    private List<String> principiosActivos = new Vector<>();
    private VersionBo versionBo;

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

    public List<String> getPrincipiosActivos() {
        return principiosActivos;
    }

    public void setPrincipiosActivos(List<String> principiosActivos) {
        this.principiosActivos = principiosActivos;
    }

    public VersionBo getVersionBo() {
        return versionBo;
    }

    public void setVersionBo(VersionBo versionBo) {
        this.versionBo = versionBo;
    }

    @Override
    public void onCreate() {

        FontsOverride.overrideFont(getApplicationContext(), "SERIF", "fonts/mi_fuente.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

    }

    public void updateCache(DatabaseHelper dbHelper) {
        GenericProvider genericProvider = new GenericProvider(dbHelper);
        PrincipioActivoProvider principioActivoProvider = new PrincipioActivoProvider(dbHelper);
        if(this.getNombresComerciales().isEmpty()){
            this.setNombresComerciales(genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_COMERCIAL));
        }
        if(this.getLaboratorios().isEmpty()){
           this.setLaboratorios(genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_LABORATORIO));
        }
        if(this.getNombresGenericos().isEmpty()){
            this.setNombresGenericos(genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_GENERICO));
        }
        if(this.getPrincipiosActivos().isEmpty()){
            this.setPrincipiosActivos(principioActivoProvider.getDistinctPrincipiosColumns());
        }
    }
}
