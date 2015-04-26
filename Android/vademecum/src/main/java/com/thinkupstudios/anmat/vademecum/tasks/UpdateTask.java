package com.thinkupstudios.anmat.vademecum.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.thinkupstudios.anmat.vademecum.UpdateDBActivity;
import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.exceptions.UpdateNotPosibleException;
import com.thinkupstudios.anmat.vademecum.providers.GenericProvider;
import com.thinkupstudios.anmat.vademecum.providers.PrincipioActivoProvider;
import com.thinkupstudios.anmat.vademecum.providers.SQLiteDBService;
import com.thinkupstudios.anmat.vademecum.providers.VersionProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.services.contract.IRemoteDBService;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;
import com.thinkupstudios.anmat.vademecum.providers.tables.PrincipiosActivosTable;

import java.io.IOException;

/**
 * Created by dcamarro on 22/04/2015.
 */
public class UpdateTask extends AsyncTask<Activity, Long, String> {

    public static final String OK = "OK";
    private IRemoteDBService dbService;
    private DatabaseHelper dbHelper;
    private UpdateDBActivity updateActivity;

    public UpdateTask(Context context) {
        super();
        this.dbService = new SQLiteDBService(context);
        this.dbHelper = new DatabaseHelper(context);
        updateActivity = (UpdateDBActivity) context;
    }


    @Override
    protected String doInBackground(Activity... params) {
        try {
        dbHelper.createIfFirstRun();
        if (!dbService.isUpToDate()) {
            //this.updateActivity.updateStatus("Bajando actualización");
            dbService.updateDatabase();
            //this.updateActivity.updateStatus("Bajando actualización");

        }
        } catch (UpdateNotPosibleException | IOException e) {
            e.printStackTrace();
            return "No se pudo actualizar";

        }
        DatabaseHelper dbHelper = new DatabaseHelper(params[0]);
        ((MiAplicacion)params[0].getApplication()).updateCache(dbHelper);

        return OK;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        updateActivity.continuar();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.updateActivity.updateStatus("Buscando actualizaciones...");

    }

}
