package com.thinkupstudios.anmat.vademecum;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.tasks.UpdateTask;

import java.io.IOException;

public class UpdateDBActivity extends NoMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_db);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        try {
            dbHelper.createIfFirstRun();

            if (mWifi.isConnected()) {
                new UpdateTask(this).execute(this);

            } else {

                ((MiAplicacion) this.getApplication()).updateCache(dbHelper);
                this.continuar();
            }
        }catch (IOException e){
            e.printStackTrace();
            //HAY QUE FINALIZAR LA APP
        }

    }
     /**
     * Continua el flujo de la aplicacion
      */
    public void continuar(){
        startActivity(new Intent(this, MainMenuActivity.class));
    }

}
