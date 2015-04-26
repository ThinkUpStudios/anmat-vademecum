package com.thinkupstudios.anmat.vademecum.providers;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.thinkupstudios.anmat.vademecum.exceptions.UpdateNotPosibleException;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.services.contract.IRemoteDBService;
import com.thinkupstudios.anmat.vademecum.webservice.contract.AnmatData;
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
        try {
            String url = String.format("http://anmatmanager.cloudapp.net/anmatdataservice/AnmatDataService.svc/isnewdataavailable?version=", versionLocal);

            String resultado = HttpRequest.get(url).bufferSize(Integer.MAX_VALUE).connectTimeout(5000).readTimeout(120000).accept("application/json").body();
            Boolean reultadoBool = !Boolean.valueOf(resultado);
            return reultadoBool;
        }catch (HttpRequest.HttpRequestException e){
            return true;
        }
    }

    @Override
    public boolean updateDatabase() throws UpdateNotPosibleException {


        //SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbService.getNewDataBase(), null);
        String outFileName = "/data/data/com.thinkupstudios.annmat.vademecum/databases/prueba.sqlite";

        try {

            String url = String.format("http://anmatmanager.cloudapp.net/anmatdataservice/AnmatDataService.svc/getdata");

            String resultado = HttpRequest.get(url).bufferSize(Integer.MAX_VALUE).connectTimeout(5000).readTimeout(120000).accept("application/json").body();
            Gson gson = new Gson();
            AnmatData anmatData = gson.fromJson(resultado, AnmatData.class);

            if(anmatData.getContent().length() == anmatData.getContentSize()) {
                byte[] data = Base64.decode(anmatData.getContent(), Base64.DEFAULT);
                dbHelper.upgrade(this.context.getAssets().open("prueba.sqlite"));

                return true;
            }
            else{
                return false;
            }
        }catch (HttpRequest.HttpRequestException e){
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
