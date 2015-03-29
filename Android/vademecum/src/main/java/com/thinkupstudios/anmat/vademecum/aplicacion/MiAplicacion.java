package com.thinkupstudios.anmat.vademecum.aplicacion;

import android.app.Application;

import com.thinkupstudios.anmat.vademecum.providers.GenericProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Created by dcamarro on 27/02/2015.
 */
public class MiAplicacion extends Application {

    private List<String> nombresComerciales;
    private List<String> nombresGenericos;
    private List<String> laboratorios;
    private GenericProvider genericProvider;

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
        this.genericProvider = new GenericProvider(new DatabaseHelper(this));
        FontsOverride.overrideFont(getApplicationContext(), "SERIF", "fonts/mi_fuente.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        this.nombresComerciales = this.genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_COMERCIAL);
        this.laboratorios = this.genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_LABORATORIO);
        this.nombresGenericos = this.genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_GENERICO);

    }

    public List<String> getPrincipiosActivos() {

        String s = "CARBONATO DE CALCIO + FOSFATO DE MAGNESIO % + CARBOXIMETILCISTEINA + SULFATO FERROSO + CLORURO DE MAGNESIO + DEXTROSA + CIANOCOBALAMINA + ACIDO FOLICO + VITAMINA K1 + IODURO DE POTASIO + CLORURO DE POTASIO + CITRATO DE POTASIO + RETINOL + BETACAROTENO + RIBOFLAVINA + NIACINAMIDA + BIOTINA + PANTOTENATO DE CALCIO + ACIDO ASCORBICO + VITAMINA D3 + TOCOFEROL + L-CARNITINA + FOSFATO TRICALCICO + SULFATO DE MANGANESO + CARBOXIMETILCELULOSA SODICA + SULFATO DE COBRE + ZINC SULFATO + CLORURO DE CROMO + AGUA + ACIDO CITRICO + LECITINA + MOLIBDATO DE SODIO + CASEINATO DE CALCIO + TAURINA + CLORURO DE COLINA + CASEINATO DE SODIO + ACEITE DE MAIZ + ACEITE DE CANOLA + AISLADO DE PROTEINA DE SOJA + TIAMINA CLORHIDRATO + PIRIDOXINA CLORHIDRATO + FOSFATO DIBASICO DE POTASIO + SELENATO DE SODIO + DEXTRINAS Y MALTOSA + ACEITE DE COCO + HIDROXIDO DE POTASIO + CITRATO DE SODIO + DL ALFATOCOFEROL ACETATO + ACEITE DE GIRASOL + FRUCTOOLIGASACARIDO + MALTODEXTRINA DE18 + FIBRA DE SOJA FIBRIM 300";

        return Arrays.asList(s.split("\\+"));




    }
}
