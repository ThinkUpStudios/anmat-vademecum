package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.bo.PrincipioActivo;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.PrincipiosActivosTable;

/**
 * Created by FaQ on 29/03/2015.
 * Provider de principios activos
 */
public class PrincipioActivoProvider extends GenericProvider {

    public PrincipioActivoProvider(DatabaseHelper db) {
        super(db);
    }


    public PrincipioActivo findPrincipioActivo(String nombre) {

        String where = " where "+ PrincipiosActivosTable.COLUMNS[0] + " = '" + nombre.trim() + "' ";
        where += " or "+ PrincipiosActivosTable.COLUMNS[8] + " like '%" + nombre.trim() + "%' ";
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
