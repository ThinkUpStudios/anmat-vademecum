package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.PrincipioActivo;
import com.thinkupstudios.anmat.vademecum.components.CollapsibleContent;
import com.thinkupstudios.anmat.vademecum.providers.MedicamentosProvider;
import com.thinkupstudios.anmat.vademecum.providers.PrincipioActivoProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;


public class DetallePrincipioActivoActivity extends Activity {
    private PrincipioActivo principioActivo;
    private PrincipioActivoProvider provider;
    private CollapsibleContent accionTerapeutica;
    private CollapsibleContent indicaciones;
    private CollapsibleContent presentacion;
    private CollapsibleContent posologia;
    private CollapsibleContent duracion;
    private CollapsibleContent contraindicaciones;
    private CollapsibleContent observacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_principio_activo);
        this.accionTerapeutica = (CollapsibleContent)findViewById(R.id.col_accion_terapeutica);
        this.indicaciones = (CollapsibleContent)findViewById(R.id.col_indicaciones);
        this.presentacion = (CollapsibleContent)findViewById(R.id.col_presentacion);
        this.posologia = (CollapsibleContent)findViewById(R.id.col_posologia);
        this.duracion = (CollapsibleContent)findViewById(R.id.col_duracion);
        this.contraindicaciones = (CollapsibleContent)findViewById(R.id.col_contraindicaciones);
        this.observacion = (CollapsibleContent)findViewById(R.id.col_observaciones);


        this.provider = new PrincipioActivoProvider(new DatabaseHelper(this));
        if(getIntent().getExtras() != null && getIntent().getStringExtra(FormularioBusqueda.PRINCIPIO_ACTIVO)!= null){
            this.principioActivo =  provider.getPrincipioActivoByNombre(getIntent().getStringExtra(FormularioBusqueda.PRINCIPIO_ACTIVO));
            this.accionTerapeutica.setContent(principioActivo.getAccionTerapeutica());
            this.indicaciones.setContent(principioActivo.getIndicaciones());
            this.presentacion.setContent(principioActivo.getPresentacion());
            this.posologia.setContent(principioActivo.getPosologia());
            this.duracion.setContent(principioActivo.getDuracion());
            this.contraindicaciones.setContent(principioActivo.getContraindicaciones());
            this.observacion.setContent(principioActivo.getObservaciones());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnu_buscar:
                startActivity(new Intent(this, BusquedaMedicamentoActivity.class));
                return true;
            case R.id.mnu_informacion:
                startActivity(new Intent(this, InformacionActivity.class));
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
