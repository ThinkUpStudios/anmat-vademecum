package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.thinkupstudios.anmat.vademecum.listeners.DarkenerButtonTouchListener;


public class MainMenuActivity extends Activity implements View.OnClickListener {
    private Button btnBuscar;
    private Button btnInfo;
    private Button btnPrincipioActivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.btnBuscar =(Button) findViewById(R.id.btn_busqueda);
        this.btnBuscar.setOnTouchListener(new DarkenerButtonTouchListener());
        this.btnBuscar.setOnClickListener(this);


        this.btnInfo =(Button) findViewById(R.id.btn_informacion);
        this.btnInfo.setOnTouchListener(new DarkenerButtonTouchListener());
        this.btnInfo.setOnClickListener(this);

        this.btnPrincipioActivo = (Button) findViewById(R.id.btn_principio_activo);
        this.btnInfo.setOnTouchListener(new DarkenerButtonTouchListener());
        this.btnInfo.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.btn_busqueda:
                startActivity(new Intent(this, BusquedaMedicamentoActivity.class));
                return true;
            case R.id.btn_informacion:
                startActivity(new Intent(this, InformacionActivity.class));
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        switch (b.getId()) {
            case R.id.btn_busqueda:
                startActivity(new Intent(this, BusquedaMedicamentoActivity.class));
                break;
            case R.id.btn_informacion:
                startActivity(new Intent(this, InformacionActivity.class));
                break;
            case R.id.btn_principio_activo:
                startActivity(new Intent(this, BuscarPrincipioActivoActivity.class));
                break;
         }

    }
}
