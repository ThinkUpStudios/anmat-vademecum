package com.thinkupstudios.anmat.vademecum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.thinkupstudios.anmat.vademecum.listeners.DarkenerButtonTouchListener;


public class MainMenuActivity extends MenuActivity implements View.OnClickListener {
    private Button btnBuscar;
    private Button btnInformacion;
    private Button btnPrincipioActivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
        this.btnBuscar =(Button) findViewById(R.id.btn_busqueda);
        this.btnBuscar.setOnTouchListener(new DarkenerButtonTouchListener());
        this.btnBuscar.setOnClickListener(this);

        this.btnPrincipioActivo = (Button) findViewById(R.id.btn_principio_activo);
        this.btnPrincipioActivo.setOnTouchListener(new DarkenerButtonTouchListener());
        this.btnPrincipioActivo.setOnClickListener(this);

        this.btnInformacion = (Button) findViewById(R.id.btn_informacion);
        this.btnInformacion.setOnTouchListener(new DarkenerButtonTouchListener());
        this.btnInformacion.setOnClickListener(this);

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
