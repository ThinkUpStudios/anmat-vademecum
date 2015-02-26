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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.btnBuscar =(Button) findViewById(R.id.btn_busqueda);
        this.btnBuscar.setOnTouchListener(new DarkenerButtonTouchListener());
        this.btnBuscar.setOnClickListener(this);

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
        if (id == R.id.mnu_buscar) {
            startActivity(new Intent(this, BusquedaMedicamentoActivity.class));
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
         }

    }
}
