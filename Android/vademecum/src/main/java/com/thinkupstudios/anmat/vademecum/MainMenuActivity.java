package com.thinkupstudios.anmat.vademecum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thinkupstudios.anmat.vademecum.listeners.DarkenerButtonTouchListener;


public class MainMenuActivity extends MenuActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnBuscar;
        Button btnInformacion;
        Button btnPrincipioActivo;
        setContentView(R.layout.activity_main_menu);
        btnBuscar =(Button) findViewById(R.id.btn_busqueda);
        btnBuscar.setOnTouchListener(new DarkenerButtonTouchListener());
        btnBuscar.setOnClickListener(this);

        btnPrincipioActivo = (Button) findViewById(R.id.btn_principio_activo);
        btnPrincipioActivo.setOnTouchListener(new DarkenerButtonTouchListener());
        btnPrincipioActivo.setOnClickListener(this);

        btnInformacion = (Button) findViewById(R.id.btn_informacion);
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
}
