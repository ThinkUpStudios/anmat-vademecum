package com.thinkupstudios.anmat.vademecum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.PrincipioActivo;
import com.thinkupstudios.anmat.vademecum.components.CollapsibleContent;
import com.thinkupstudios.anmat.vademecum.listeners.DarkenerButtonTouchListener;
import com.thinkupstudios.anmat.vademecum.providers.PrincipioActivoProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;


public class DetallePrincipioActivoActivity extends MenuActivity {
    private PrincipioActivo principioActivo;
    private PrincipioActivoProvider provider;
    private CollapsibleContent accionTerapeutica;
    private CollapsibleContent indicaciones;
    private CollapsibleContent presentacion;
    private CollapsibleContent posologia;
    private CollapsibleContent duracion;
    private CollapsibleContent contraindicaciones;
    private CollapsibleContent observacion;
    private Button recomendados;
    private TextView txtNoResultados;

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
        this.txtNoResultados = (TextView) findViewById(R.id.txt_no_resultados);
        this.recomendados = (Button) findViewById(R.id.btn_recomentados);

        this.setTitle(getIntent().getStringExtra(FormularioBusqueda.PRINCIPIO_ACTIVO));

        this.provider = new PrincipioActivoProvider(new DatabaseHelper(this));
        if(getIntent().getExtras() != null && getIntent().getStringExtra(FormularioBusqueda.PRINCIPIO_ACTIVO)!= null){
            this.principioActivo =  provider.findPrincipioActivo(getIntent().getStringExtra(FormularioBusqueda.PRINCIPIO_ACTIVO));

            if(principioActivo != null) {
                this.txtNoResultados.setVisibility(View.INVISIBLE);
                this.accionTerapeutica.setContent(principioActivo.getAccionTerapeutica());
                this.indicaciones.setContent(principioActivo.getIndicaciones());
                this.presentacion.setContent(principioActivo.getPresentacion());
                this.posologia.setContent(principioActivo.getPosologia());
                this.duracion.setContent(principioActivo.getDuracion());
                this.contraindicaciones.setContent(principioActivo.getContraindicaciones());
                this.observacion.setContent(principioActivo.getObservaciones());

                recomendados.setOnTouchListener(new DarkenerButtonTouchListener());
                recomendados.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(DetallePrincipioActivoActivity.this,
                                DetalleMedicamentoListActivity.class);
                        i.putExtra("COMERCIAL_RECOMENDADO", principioActivo.getNombre());
                        FormularioBusqueda f = new FormularioBusqueda();
                        f.setNombreGenerico(principioActivo.getNombre());
                        f.setUseLike(true);
                        f.setFiltrarPorFormula(true);
                        i.putExtra(FormularioBusqueda.FORMULARIO_MANUAL, f);
                        startActivity(i);
                        DetallePrincipioActivoActivity.this.
                                overridePendingTransition(fade_in, fade_out);
                    }
                });
            }
            else{

                int color = getResources().getColor(R.color.anmat_azul);
                this.txtNoResultados.setVisibility(View.VISIBLE);
                this.txtNoResultados.setText("Sin Resultados");
                this.txtNoResultados.setTextColor(color);

                this.accionTerapeutica.setVisibility(View.INVISIBLE);
                this.indicaciones.setVisibility(View.INVISIBLE);
                this.presentacion.setVisibility(View.INVISIBLE);
                this.posologia.setVisibility(View.INVISIBLE);
                this.duracion.setVisibility(View.INVISIBLE);
                this.contraindicaciones.setVisibility(View.INVISIBLE);
                this.observacion.setVisibility(View.INVISIBLE);
                this.recomendados.setVisibility(View.INVISIBLE);
            }
        }
    }


}
