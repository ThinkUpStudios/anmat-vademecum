package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.bo.PrincipioActivo;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.PrincipiosActivosTable;

import java.util.List;
import java.util.Vector;

/**
 * Created by FaQ on 29/03/2015.
 * Provider de principios activos
 */
public class PrincipioActivoProvider extends GenericProvider {

    public PrincipioActivoProvider(DatabaseHelper db) {
        super(db);
    }


    public PrincipioActivo findPrincipioActivo(String nombre) {

        String where = " where "+ this.matarCaracteresEspeciales(PrincipiosActivosTable.COLUMNS[0]) + " = " + this.matarCaracteresEspeciales("'"+nombre.trim() +"'") + " ";
        where += " or "+ this.matarCaracteresEspeciales(PrincipiosActivosTable.COLUMNS[8]) + " like " + this.matarCaracteresEspeciales("'%"+ nombre.trim() + "%' ");
        Cursor cursor = null;
        PrincipioActivo principioActivo = null;
        try {
            cursor = this.getAllByWhere(PrincipiosActivosTable.TABLE_NAME, where, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                principioActivo = cursorToPrincipioActivo(cursor);
            }
        }finally {
            // make sure to close the cursor
            cursor.close();
        }

        return principioActivo;
    }

    public List<String> getDistinctPrincipiosColumns(){

        List<String> resultado = new Vector<>();
        Cursor cursor = null;
        try {
            cursor = this.getAllByWhere(PrincipiosActivosTable.TABLE_NAME, null, null);

            cursor.moveToFirst();

            PrincipioActivo principioActivo = null;

            while (!cursor.isAfterLast()) {
                principioActivo = cursorToPrincipioActivo(cursor);
                resultado.add(principioActivo.getNombre());
                if (principioActivo.getOtrosNombres() != null && !principioActivo.getOtrosNombres().isEmpty()) {
                    String[] otrosNombre = principioActivo.getOtrosNombres().split("\\?");
                    for (String otroNombre : otrosNombre) {
                        resultado.add(otroNombre.trim());
                    }
                }
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultado;
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
        principioActivo.setOtrosNombres(cursor.getString(cursor.getColumnIndex(PrincipiosActivosTable.COLUMNS[8])));

        return principioActivo;
    }


}
