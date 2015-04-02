package com.thinkupstudios.anmat.vademecum;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.components.ClearableAutoCompliteEditText;
import com.thinkupstudios.anmat.vademecum.listeners.DarkenerButtonTouchListener;

import java.util.List;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;


public class BusquedaMedicamentoActivity extends MenuActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FormularioBusqueda formualario = new FormularioBusqueda();
    private ClearableAutoCompliteEditText nombreComercial;
    private ClearableAutoCompliteEditText laboratorio;
    private ClearableAutoCompliteEditText nombreGenerico;
    private Button buscarBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_medicamento);
        buscarBtn = (Button) findViewById(R.id.btn_form_busqueda_buscar);
        buscarBtn.setOnClickListener(this);
        buscarBtn.setOnTouchListener(new DarkenerButtonTouchListener());
        //buscarBtn.setImeOptions(EditorInfo.IME_ACTION_DONE);


        this.configEditTextLabs();

        this.configEditTextGenericos();

        this.configEditTextComerciales();

    }


    @Override
    public void onClick(View v) {

        Button b = (Button) v;
        Intent i = new Intent(this, DetalleMedicamentoListActivity.class);
        this.formualario.setNombreGenerico(this.nombreGenerico.getText().toString());
        this.formualario.setNombreComercial(this.nombreComercial.getText().toString());
        this.formualario.setLaboratorio(this.laboratorio.getText().toString());
        if(this.formualario.isEmprty()){
            Toast.makeText(this, R.string.sin_campo_busqueda,Toast.LENGTH_LONG).show();
        }else{
            i.putExtra(FormularioBusqueda.FORMULARIO_MANUAL,this.formualario);
            startActivity(i);
            overridePendingTransition(fade_in, fade_out);

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //this.formualario.setLaboratorio(((TextView)view).getText().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class MyEditorActionListener implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                buscarBtn.performClick();
                return true;
            }
            return false;
        }
    }

    private void configEditTextGenericos(){

        MiAplicacion app = (MiAplicacion) this.getApplicationContext();

        this.nombreGenerico = (ClearableAutoCompliteEditText) findViewById(R.id.txt_nombre_generico);
        List<String> genericos = app.getNombresGenericos();
        ArrayAdapter adapterGen = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,genericos);
        adapterGen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.nombreGenerico.setAdapter(adapterGen);

    }

    private void configEditTextComerciales(){

        MiAplicacion app = (MiAplicacion) this.getApplicationContext();

        this.nombreComercial = (ClearableAutoCompliteEditText) findViewById(R.id.txt_nombre_comercial);
        List<String> comerciales = app.getNombresComerciales();
        ArrayAdapter adapterCom = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,comerciales);
        adapterCom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.nombreComercial.setAdapter(adapterCom);
    }

    private void configEditTextLabs(){

        MiAplicacion app = (MiAplicacion) this.getApplicationContext();

        this.laboratorio = (ClearableAutoCompliteEditText) findViewById(R.id.txt_laboratorio);
        List<String> laboratorios = app.getLaboratorios();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, laboratorios);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        laboratorio.setAdapter(dataAdapter);

    }




}