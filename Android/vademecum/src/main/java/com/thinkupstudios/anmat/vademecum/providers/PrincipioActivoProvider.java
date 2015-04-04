package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;
import com.thinkupstudios.anmat.vademecum.bo.PrincipioActivo;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;
import com.thinkupstudios.anmat.vademecum.providers.tables.PrincipiosActivosTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FaQ on 29/03/2015.
 */
public class PrincipioActivoProvider extends GenericProvider{

    public PrincipioActivoProvider(DatabaseHelper db) {
        super(db);
    }


    public PrincipioActivo getPrincipioActivoByNombre(String nombre){
        PrincipioActivo p = new PrincipioActivo();
        p.setAccionTerapeutica("Antiretroviral, inhibidor nucleosídico de la transcriptasa inversa del VIH-1 y VIH-2 ");
        p.setContraindicaciones("\"- No administrar en caso de antecedentes de insuficiencia hepática severa o en pacientes cuya intolerancia al abacavir haya obligado a la suspensión del tratamiento. \n" +
                "- Puede provocar: \n" +
                "• Reacciones de hipersensibilidad: erupciones cutáneas, trastornos digestivos (náuseas, vómitos, diarrea, dolor abdominal), tos, disnea, malestar, cefaleas, letargia, edema, linfoadenopatía, hipotensión arterial, mialgias, artralgia, insuficiencia renal; \n" +
                "• Acidosis láctica y afectación hepática. \n" +
                "En todos estos casos, suspender la administración de abacavir inmediatamente y para siempre. \n" +
                "- Embarazo: debe ser evitado, excepto si no existe alternativa\"");
        p.setDuracion("Según la eficacia y la tolerancia del abacavir. ");
        p.setIndicaciones("Infección por el VIH-1 o el VIH-2, en combinación con otros antiretrovirales ");
        p.setNombre(nombre);
        p.setObservaciones("\"- Los comprimidos son indivisibles. Si es necesario administrar medio comprimido, utilizar \n" +
                "un cutter o un cortador de comprimidos para cortar el comprimido en 2 partes iguales. \n" +
                "- Existen combinaciones a dosis fijas de abacavir-lamividuna (Epzicom®, etc.) y abacavir- \n" +
                "zidoviduna-lamividuna (Trizivir®, etc.). \n" +
                "- Conservación: temperatura inferior a 30ºC \n" +
                "Una vez abierta, la solución oral se conserva durante 2 meses, a una temperatura inferior a 30ºC. '\"");
        p.setPosologia("\"- Niños de menos de 25 kg: 16 mg/kg/día divididos en 2 tomas, sin sobrepasar 600 mg/día \n" +
                "- Niños ≥ 25 kg y adultos: 600 mg/día divididos en 2 tomas \n" +
                "\n" +
                "Peso-Solución oral de 20 mg/ml-Comprimido de 300 mg \n" +
                "3 a 5 kg-3 ml x 2--\n" +
                "6 a 9 kg-4 ml x 2--\n" +
                "10 a 13 kg-6 ml x 2--\n" +
                "14 a 19 kg ---1/2 cp x 2 \n" +
                "20 a 24 kg---1 cp mañana y 1/2 cp tarde \n" +
                "≥25 kg---1 cp x 2 \n" +
                "\"");
        p.setPresentacion("\"- Comprimido de 300 mg \n" +
                "- Solución oral de 20 mg/ml, acompañado de un dosificador graduado \n" +
                " \"");
        p.setDuracion("Según la eficacia y la tolerancia del abacavir. ");
        return p;
    }

    public PrincipioActivo findPrincipioActivo(String nombre) {

        String where = " where principio = '"+ nombre.trim() + "' ";

        Cursor cursor = this.getAllByWhere(PrincipiosActivosTable.TABLE_NAME, where, null);

        cursor.moveToFirst();

        PrincipioActivo principioActivo = null;

        if (!cursor.isAfterLast()) {
            principioActivo = cursorToPrincipioActivo(cursor);
        }
        // make sure to close the cursor
        cursor.close();
        return principioActivo;
    }

    private PrincipioActivo cursorToPrincipioActivo(Cursor cursor) {
        PrincipioActivo principioActivo = new PrincipioActivo();
        principioActivo.setNombre(cursor.getString(cursor.getColumnIndex(PrincipiosActivosTable.COLUMNS[0])));
        principioActivo.setAccionTerapeutica(cursor.getString(cursor.getColumnIndex(PrincipiosActivosTable.COLUMNS[1])));
        principioActivo.setIndicaciones(cursor.getString(cursor.getColumnIndex(PrincipiosActivosTable.COLUMNS[2])));
        principioActivo.setPresentacion(cursor.getString(cursor.getColumnIndex(PrincipiosActivosTable.COLUMNS[3])));
        principioActivo.setPosologia(cursor.getString(cursor.getColumnIndex(PrincipiosActivosTable.COLUMNS[4])));
        principioActivo.setDuracion(cursor.getString(cursor.getColumnIndex(PrincipiosActivosTable.COLUMNS[5])));
        principioActivo.setContraindicaciones(cursor.getString(cursor.getColumnIndex(PrincipiosActivosTable.COLUMNS[6])));
        principioActivo.setObservaciones(cursor.getString(cursor.getColumnIndex(PrincipiosActivosTable.COLUMNS[7])));

        return principioActivo;
    }


}
