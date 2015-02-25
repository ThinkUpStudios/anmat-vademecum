package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * Created by dcamarro on 24/02/2015.
 */
public class GenericProvider {

    protected DatabaseHelper helper;

    public GenericProvider(DatabaseHelper db) {
        this.helper = db;
        try {
            this.helper.createDataBase();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllCollumnName(String tabla, String[] columnas){

        List<String> resultado = new Vector<>();
        Cursor c = helper.getReadableDatabase().query(tabla, columnas, null, null, null, null, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                resultado.add(c.getString(c.getColumnIndex(columnas[0])));
            } while (c.moveToNext());
        }
        helper.close();
        return resultado;

    }

    protected Cursor getAllByWhere(String tabla, String where){
        String query = "select * from "+tabla+" "+where;
        Cursor c = helper.getReadableDatabase().rawQuery(query, null);

        return c;
    }

}
