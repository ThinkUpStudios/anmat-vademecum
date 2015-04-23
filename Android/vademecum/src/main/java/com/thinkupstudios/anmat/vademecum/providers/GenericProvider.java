package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * Created by dcamarro on 24/02/2015.
 * Provider general
 */
public class GenericProvider {

    protected DatabaseHelper helper;

    public GenericProvider(DatabaseHelper db) {
        this.helper = db;

        this.helper.getReadableDatabase();

    }

    public List<String> getDistinctColumns(String tabla, String[] columnas){

        List<String> resultado = new Vector<>();
        Cursor c = null;
        try {
            c = helper.getReadableDatabase().query(true, tabla, columnas, null, null, null, null, columnas[0], null);
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    resultado.add(c.getString(c.getColumnIndex(columnas[0])));
                } while (c.moveToNext());
            }
            helper.close();
            c.close();
        }finally {
            helper.close();
           if(c!= null) c.close();
        }
        return resultado;

    }

    protected Cursor getAllByWhere(String tabla, String where, String orderBy){
        String query = "select * from "+tabla+" "+where;
        if(orderBy != null){
            query += " " + orderBy;
        }

        return helper.getReadableDatabase().rawQuery(query, null);
    }

    protected String matarCaracteresEspeciales(String s){
        String sinCarEs = "REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(upper(";
        sinCarEs += s;
        sinCarEs += "),'Á','A'), 'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N')";
        return sinCarEs;
    }

}
