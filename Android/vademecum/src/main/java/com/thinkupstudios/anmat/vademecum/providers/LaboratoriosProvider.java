package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * Created by FaQ on 24/02/2015.
 */
public class LaboratoriosProvider {

    private DatabaseHelper helper;

   public  LaboratoriosProvider(DatabaseHelper db){
    this.helper = db;
       try {
           this.helper.createDataBase();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public List<String> getAll(){

        String selectQuery = "select * from Laboratorios";
        List<String> resultado = new Vector<>();
        Cursor c = helper.getReadableDatabase().rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                resultado.add(c.getString(c.getColumnIndex("nombre")));
            } while (c.moveToNext());
        }
        helper.close();
        return resultado;

    }
}
