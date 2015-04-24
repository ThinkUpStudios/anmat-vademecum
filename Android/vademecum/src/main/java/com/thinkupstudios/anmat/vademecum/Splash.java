package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.providers.GenericProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.services.contract.IRemoteDBService;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;
import com.thinkupstudios.anmat.vademecum.providers.tables.PrincipiosActivosTable;


/**
 * Created by FaQ on 11/04/2015.
 * Activity creada solo para splashScreen
 */
public class Splash extends Activity {
    private IRemoteDBService dbService;
    private DatabaseHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, UpdateDBActivity.class));

    }


}
