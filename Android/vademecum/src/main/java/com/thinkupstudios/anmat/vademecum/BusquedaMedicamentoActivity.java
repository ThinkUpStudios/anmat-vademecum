package com.thinkupstudios.anmat.vademecum;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.comparadores.ComparadorStringAutocomplete;
import com.thinkupstudios.anmat.vademecum.components.ClearableAutoCompliteEditText;
import com.thinkupstudios.anmat.vademecum.listeners.DarkenerButtonTouchListener;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

import java.util.List;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;


public class BusquedaMedicamentoActivity extends NoMenuActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FormularioBusqueda formualario = new FormularioBusqueda();
    private ClearableAutoCompliteEditText nombreComercial;
    private ClearableAutoCompliteEditText laboratorio;
    private ClearableAutoCompliteEditText nombreGenerico;
    private ClearableAutoCompliteEditText forma;
    private CheckBox checkRemediar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button buscarBtn;

        setContentView(R.layout.activity_busqueda_medicamento);

        ImageButton help = (ImageButton) findViewById(R.id.btn_input_help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new InputHelpDialog();
                newFragment.show(BusquedaMedicamentoActivity.this.getFragmentManager(), "missiles");
            }
        });
        buscarBtn = (Button) findViewById(R.id.btn_form_busqueda_buscar);
        buscarBtn.setOnClickListener(this);
        buscarBtn.setOnTouchListener(new DarkenerButtonTouchListener());
        //buscarBtn.setImeOptions(EditorInfo.IME_ACTION_DONE);

        this.configEditTextLabs();
        this.configEditTextGenericos();
        this.configEditTextComerciales();
        this.configEditTextForma();
        this.configCheckRemediar();

    }




    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, DetalleMedicamentoListActivity.class);
        this.formualario.setNombreGenerico(this.nombreGenerico.getText().toString());
        this.formualario.setNombreComercial(this.nombreComercial.getText().toString());
        this.formualario.setLaboratorio(this.laboratorio.getText().toString());
        this.formualario.setForma(this.forma.getText().toString());
        this.formualario.setEsRemediar(this.checkRemediar.isChecked());
        this.formualario.setUseLike(true);
        if (this.formualario.isEmprty()) {
            Toast.makeText(this, R.string.sin_campo_busqueda, Toast.LENGTH_LONG).show();
        } else {
            i.putExtra(FormularioBusqueda.FORMULARIO_MANUAL, this.formualario);
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


    private void configEditTextGenericos() {

        MiAplicacion app = (MiAplicacion) this.getApplicationContext();

        this.nombreGenerico = (ClearableAutoCompliteEditText) findViewById(R.id.txt_nombre_generico);
        List<String> genericos = app.getNombresGenericos();
        ArrayAdapter<String> adapterGen = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, genericos);

        adapterGen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.nombreGenerico.setAdapter(adapterGen);

    }

    private void configEditTextComerciales() {

        MiAplicacion app = (MiAplicacion) this.getApplicationContext();

        this.nombreComercial = (ClearableAutoCompliteEditText) findViewById(R.id.txt_nombre_comercial);
        List<String> comerciales = app.getNombresComerciales();
        ArrayAdapter<String> adapterCom = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, comerciales);

        adapterCom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.nombreComercial.setAdapter(adapterCom);
    }

    private void configEditTextLabs() {

        MiAplicacion app = (MiAplicacion) this.getApplicationContext();

        this.laboratorio = (ClearableAutoCompliteEditText) findViewById(R.id.txt_laboratorio);
        List<String> laboratorios = app.getLaboratorios();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, laboratorios);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        laboratorio.setAdapter(dataAdapter);

    }

    private void configEditTextForma() {

        MiAplicacion app = (MiAplicacion) this.getApplicationContext();

        this.forma = (ClearableAutoCompliteEditText) findViewById(R.id.txt_forma);
        List<String> formas = app.getFormasFarmaceuticas();
        ArrayAdapter<String> adapterGen = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, formas);

        adapterGen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.forma.setAdapter(adapterGen);

    }

    private void configCheckRemediar() {
        this.checkRemediar = (CheckBox) findViewById(R.id.checkbox_remediar);
        checkRemediar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    laboratorio.setVisibility(View.GONE);
                    nombreComercial.setVisibility(View.GONE);

                }
                else{
                    laboratorio.setVisibility(View.VISIBLE);
                    nombreComercial.setVisibility(View.VISIBLE);
                }

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        ((MiAplicacion) this.getApplication()).updateCache(new DatabaseHelper(this), false);
    }

}