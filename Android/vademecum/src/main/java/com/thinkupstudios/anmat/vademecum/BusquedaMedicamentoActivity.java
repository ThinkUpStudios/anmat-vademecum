package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


public class BusquedaMedicamentoActivity extends Activity implements View.OnClickListener{
private AutoCompleteTextView laboratorioTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_medicamento);
        ((Button)findViewById(R.id.btn_form_busqueda_buscar)).setOnClickListener(this);
        laboratorioTxt = ((AutoCompleteTextView)findViewById(R.id.autoCompleteTextView));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.busqueda_medicamento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        startActivity(new Intent(this, DetalleMedicamentoListActivity.class));

    }
}
