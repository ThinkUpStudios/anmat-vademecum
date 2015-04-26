package com.thinkupstudios.anmat.vademecum.providers;

import android.content.Context;
import android.util.Base64;

import com.thinkupstudios.anmat.vademecum.exceptions.UpdateNotPosibleException;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.services.contract.IRemoteDBService;
import com.thinkupstudios.anmat.vademecum.webservice.http.HttpRequest;


import java.io.IOException;

/**
 * Created by FaQ on 20/04/2015.
 * IMplementacion para SQLite remoto
 */
public class SQLiteDBService implements IRemoteDBService {

    private DatabaseHelper dbHelper;
    private Context context;

    public SQLiteDBService(Context context) {
        dbHelper = new DatabaseHelper(context);

        this.context = context;
    }

    @Override
    public boolean isUpToDate() {

        VersionProvider vProvider = new VersionProvider(dbHelper);
        Integer versionLocal = vProvider.getVersionBo().getNumero();

        //TODO: Llamar al ws.
        String url = String.format("http://mymovieapi.com/?title=%1$s&type=json&limit=10", versionLocal);

        HttpRequest.get("https://encrypted.google.com/").trustAllCerts().trustAllHosts().accept("application/json").body();
        Integer versionServer = 10;

        return (versionLocal == versionServer);
    }

    @Override
    public boolean updateDatabase() throws UpdateNotPosibleException {


        //SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbService.getNewDataBase(), null);
        String outFileName = "/data/data/com.thinkupstudios.annmat.vademecum/databases/prueba.sqlite";
        //TODO: Llamar al ws.
        String url = String.format("http://mymovieapi.com/?title=%1$s&type=json&limit=10");
        String resultado = " ";


        try {

            dbHelper.upgrade(this.context.getAssets().open("prueba.sqlite"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}
