package com.thinkupstudios.anmat.vademecum;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.PrincipioActivo;
import com.thinkupstudios.anmat.vademecum.components.CollapsibleContent;
import com.thinkupstudios.anmat.vademecum.providers.PrincipioActivoProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;


public class DetallePrincipioActivoActivity extends MenuActivity {



    private PrincipioActivo principioActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        PrincipioActivoProvider provider;
        CollapsibleContent accionTerapeutica;
        CollapsibleContent indicaciones;
        CollapsibleContent presentacion;
        CollapsibleContent posologia;
        CollapsibleContent duracion;
        CollapsibleContent contraindicaciones;
        CollapsibleContent observacion;
        TextView txtNoResultados;
        TextView txtPrincipioActivo;
        setContentView(R.layout.activity_detalle_principio_activo);
        txtPrincipioActivo = (TextView) findViewById(R.id.txt_principio);
        accionTerapeutica = (CollapsibleContent) findViewById(R.id.col_accion_terapeutica);
        indicaciones = (CollapsibleContent) findViewById(R.id.col_indicaciones);
        presentacion = (CollapsibleContent) findViewById(R.id.col_presentacion);
        posologia = (CollapsibleContent) findViewById(R.id.col_posologia);
        duracion = (CollapsibleContent) findViewById(R.id.col_duracion);
        contraindicaciones = (CollapsibleContent) findViewById(R.id.col_contraindicaciones);
        observacion = (CollapsibleContent) findViewById(R.id.col_observaciones);
        txtNoResultados = (TextView) findViewById(R.id.txt_no_resultados);




        provider = new PrincipioActivoProvider(new DatabaseHelper(this));
        if (getIntent().getExtras() != null && getIntent().getStringExtra(FormularioBusqueda.PRINCIPIO_ACTIVO) != null) {
            principioActivo = provider.findPrincipioActivo(getIntent().getStringExtra(FormularioBusqueda.PRINCIPIO_ACTIVO));

            if (principioActivo != null) {
                txtNoResultados.setVisibility(View.INVISIBLE);

                accionTerapeutica.setVisibility(View.VISIBLE);
                indicaciones.setVisibility(View.VISIBLE);
                presentacion.setVisibility(View.VISIBLE);
                posologia.setVisibility(View.VISIBLE);
                duracion.setVisibility(View.VISIBLE);
                contraindicaciones.setVisibility(View.VISIBLE);
                observacion.setVisibility(View.VISIBLE);
                txtPrincipioActivo.setVisibility(View.VISIBLE);
                txtPrincipioActivo.setText(principioActivo.getNombre());
                accionTerapeutica.setContent(principioActivo.getAccionTerapeutica());
                indicaciones.setContent(principioActivo.getIndicaciones());
                presentacion.setContent(principioActivo.getPresentacion());
                posologia.setContent(principioActivo.getPosologia());
                duracion.setContent(principioActivo.getDuracion());
                contraindicaciones.setContent(principioActivo.getContraindicaciones());
                observacion.setContent(principioActivo.getObservaciones());
            } else {

                int color = getResources().getColor(R.color.anmat_azul);
                txtNoResultados.setVisibility(View.VISIBLE);
                txtNoResultados.setText("Sin Resultados");
                txtNoResultados.setTextColor(color);

                accionTerapeutica.setVisibility(View.INVISIBLE);
                indicaciones.setVisibility(View.INVISIBLE);
                presentacion.setVisibility(View.INVISIBLE);
                posologia.setVisibility(View.INVISIBLE);
                duracion.setVisibility(View.INVISIBLE);
                contraindicaciones.setVisibility(View.INVISIBLE);
                observacion.setVisibility(View.INVISIBLE);
                txtPrincipioActivo.setVisibility(View.INVISIBLE);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pa_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnu_mismo_comp) {
            if (principioActivo != null) {


                Intent i = new Intent(DetallePrincipioActivoActivity.this,
                        DetalleMedicamentoListActivity.class);
                i.putExtra("COMERCIAL_RECOMENDADO", principioActivo.getNombre());
                FormularioBusqueda f = new FormularioBusqueda();
                String campoBusquedaCompleta = principioActivo.getNombre();
                if(principioActivo.getOtrosNombres() != null && !principioActivo.getOtrosNombres().isEmpty()) {
                    campoBusquedaCompleta += "?" + principioActivo.getOtrosNombres();
                }
                f.setNombreGenerico(campoBusquedaCompleta);
                f.setFiltrarPorFormula(true);
                f.setUseLike(true);
                i.putExtra(FormularioBusqueda.FORMULARIO_MANUAL, f);
                startActivity(i);
                DetallePrincipioActivoActivity.this.
                        overridePendingTransition(fade_in, fade_out);
                return true;
            }


        }
        return super.onOptionsItemSelected(item);
    }
}
