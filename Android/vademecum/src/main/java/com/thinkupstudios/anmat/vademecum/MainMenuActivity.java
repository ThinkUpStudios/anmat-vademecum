package com.thinkupstudios.anmat.vademecum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.listeners.DarkenerButtonTouchListener;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;


public class MainMenuActivity extends ContactActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_menu);
        Button btnBuscar = (Button) findViewById(R.id.btn_busqueda);
        btnBuscar.setOnTouchListener(new DarkenerButtonTouchListener());
        btnBuscar.setOnClickListener(this);


        Button btnPrincipioActivo = (Button) findViewById(R.id.btn_principio_activo);
        btnPrincipioActivo.setOnTouchListener(new DarkenerButtonTouchListener());
        btnPrincipioActivo.setOnClickListener(this);


        Button btnInformacion = (Button) findViewById(R.id.btn_informacion);
        btnInformacion.setOnTouchListener(new DarkenerButtonTouchListener());
        btnInformacion.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        switch (b.getId()) {
            case R.id.btn_busqueda:
                startActivity(new Intent(this, BusquedaMedicamentoActivity.class));
                break;
            case R.id.btn_principio_activo:
                startActivity(new Intent(this, BuscarPrincipioActivoActivity.class));
                break;
            case R.id.btn_informacion:
                startActivity(new Intent(this, InformacionActivity.class));
                break;


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MiAplicacion) this.getApplication()).updateCache(new DatabaseHelper(this), false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnu_informacion:
                startActivity(new Intent(this, AcercaDeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
