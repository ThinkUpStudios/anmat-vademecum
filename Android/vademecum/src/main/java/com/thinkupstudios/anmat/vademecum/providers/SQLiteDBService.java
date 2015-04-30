package com.thinkupstudios.anmat.vademecum.providers;

import android.content.Context;
import android.util.Base64;
import android.util.Base64InputStream;

import com.google.gson.Gson;
import com.thinkupstudios.anmat.vademecum.exceptions.UpdateNotPosibleException;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.services.contract.IRemoteDBService;
import com.thinkupstudios.anmat.vademecum.webservice.contract.AnmatData;
import com.thinkupstudios.anmat.vademecum.webservice.http.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

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
            String url = "http://anmatmanager.cloudapp.net/anmatdataservice/AnmatDataService.svc/isnewdataavailable?version=" + versionLocal.intValue();

            String resultado = HttpRequest.get(url).connectTimeout(5000).readTimeout(120000).accept("application/json").body();

            return !Boolean.valueOf(resultado);
        } catch (HttpRequest.HttpRequestException e) {
            return true;
        }
    }

    @Override
    public boolean updateDatabase() throws UpdateNotPosibleException {
        try {
            String url = String.format("http://anmatmanager.cloudapp.net/anmatdataservice/AnmatDataService.svc/getdata");

            String resultado = HttpRequest.get(url).connectTimeout(5000).readTimeout(120000).accept("application/json").body();

            Long contentSize = Long.valueOf(resultado.split(",")[1].split(":")[1].replace("}", "").replace("\"", ""));
            try {

                byte[] buffer = new byte[3000];
                ByteArrayOutputStream os = new ByteArrayOutputStream();
               InputStream fin = new Base64InputStream(new ByteArrayInputStream(resultado.split(",")[0].split(":")[1].replace("}", "").replace("\"", "").getBytes()),Base64.DEFAULT );

                while (fin.read(buffer) >= 0) {
                    os.write(buffer);
                }


                String s = new String(os.toByteArray(),"UTF-8");
                if (s.length() == contentSize) {

                    dbHelper.upgrade(s.getBytes());

                    return true;
                } else {
                    return false;
                }


            } catch (HttpRequest.HttpRequestException e) {
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }


        } catch (HttpRequest.HttpRequestException e) {
            return false;
        }


    }

    @Override
    public void closeHelper() {
        if (this.dbHelper != null) {
            this.dbHelper.close();
        }
    }

}
