package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;


public class BusquedaMedicamentoActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FormularioBusqueda formualario = new FormularioBusqueda();
    private Spinner laboratorio;
    private AutoCompleteTextView nombreComercial;
    private AutoCompleteTextView nombreGenerico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_medicamento);

        ((Button) findViewById(R.id.btn_form_busqueda_buscar)).setOnClickListener(this);
        this.laboratorio = (Spinner)findViewById(R.id.spin_laboratorio);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.laboratorios_array));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        laboratorio.setAdapter(dataAdapter);
        laboratorio.setOnItemSelectedListener(this);

        this.nombreComercial = (AutoCompleteTextView) findViewById(R.id.txt_nombre_comercial);
        String[] comerciales = getResources().
                getStringArray(R.array.comerciales_array);
        ArrayAdapter adapterCom = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,comerciales);
        this.nombreComercial.setAdapter(adapterCom);

        this.nombreGenerico = (AutoCompleteTextView) findViewById(R.id.txt_nombre_generico);
        String[] genericos = getResources().
                getStringArray(R.array.genericos_array);
        ArrayAdapter adapterGen = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,genericos);
        this.nombreGenerico.setAdapter(adapterGen);

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
        Button b = (Button) v;
        Intent i = new Intent(this, DetalleMedicamentoListActivity.class);
//        formualario.setLaboratorio(this.laboratorio.getSelectedItem().toString());
//        formualario.setNombreComercial(this.nombreComercial.getText().toString());
//        formualario.setNombreGenerico(this.nombreGenerico.getText().toString());
//        i.getExtras().putSerializable("formulario",this.formualario);
        startActivity(i);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.formualario.setLaboratorio(((TextView)view).getText().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
