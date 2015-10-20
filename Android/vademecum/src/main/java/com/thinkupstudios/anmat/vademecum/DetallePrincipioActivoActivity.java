package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.PairHeadDetail;
import com.thinkupstudios.anmat.vademecum.bo.PrincipioActivo;
import com.thinkupstudios.anmat.vademecum.providers.PrincipioActivoProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;


public class DetallePrincipioActivoActivity extends Activity {


    private PrincipioActivo principioActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        PrincipioActivoProvider provider;
        setContentView(R.layout.activity_detalle_principio_activo);
        TextView txtNoResultados = (TextView) findViewById(R.id.txt_no_resultados);
        TextView txtPrincipioActivo = (TextView) findViewById(R.id.txt_principio);
        RelativeLayout lyClasificacionTerapeutica = (RelativeLayout) findViewById(R.id.ly_clasificacion_terapeutica);
        RelativeLayout lyContraindicaciones = (RelativeLayout) findViewById(R.id.ly_contraindicaciones);
        RelativeLayout lyBibliografia = (RelativeLayout) findViewById(R.id.ly_bibliografia);
        RelativeLayout lyFarmacologia = (RelativeLayout) findViewById(R.id.ly_farmacologia);
        RelativeLayout lyIndicacionesPosologia = (RelativeLayout) findViewById(R.id.ly_indicaciones_posologia);
        RelativeLayout lyInfoAdicional = (RelativeLayout) findViewById(R.id.ly_info_adicional);
        RelativeLayout lyInteracciones = (RelativeLayout) findViewById(R.id.ly_interacciones);
        RelativeLayout lyOtrosNombres = (RelativeLayout) findViewById(R.id.ly_otros_nombres);
        RelativeLayout lyReaccionesAdversas = (RelativeLayout) findViewById(R.id.ly_reacciones_adversas);
        RelativeLayout lyReferencias = (RelativeLayout) findViewById(R.id.ly_referencias);


        DatabaseHelper dh = null;

        try {
            dh = new DatabaseHelper(this);
            provider = new PrincipioActivoProvider(dh);
            if (getIntent().getExtras() != null && getIntent().getStringExtra(FormularioBusqueda.PRINCIPIO_ACTIVO) != null) {
                String nombre = getIntent().getStringExtra(FormularioBusqueda.PRINCIPIO_ACTIVO);
                txtPrincipioActivo.setText(nombre);
                principioActivo = provider.findPrincipioActivo(nombre);

                if (principioActivo != null) {
                    txtNoResultados.setVisibility(View.GONE);
                    lyClasificacionTerapeutica.setOnClickListener(new CategoryClickHandler(
                            new PairHeadDetail("Clasificaci\u00f3n Terap\u00e9utica", principioActivo.getClasificacionTerapeutica())
                    ));

                    lyContraindicaciones.setOnClickListener(new CategoryClickHandler(
                            new PairHeadDetail("Contraindicaciones", principioActivo.getContraindicaciones())
                    ));

                    lyBibliografia.setOnClickListener(new CategoryClickHandler(
                            new PairHeadDetail("Bibliograf\u00eda", principioActivo.getBibliografia())
                    ));

                    lyFarmacologia.setOnClickListener(new CategoryClickHandler(
                            new PairHeadDetail("Descripci\u00f3n", principioActivo.getDescripcionFarmacologia()),
                            new PairHeadDetail("Mecanismo de Acci\u00f3n", principioActivo.getMecanismoAccion()),
                            new PairHeadDetail("Farmacocinetica", principioActivo.getDescripcionFarmacologia())
                    ));

                    lyIndicacionesPosologia.setOnClickListener(new CategoryClickHandler(
                            new PairHeadDetail("Indicaciones", principioActivo.getIndicaciones()),
                            new PairHeadDetail("Posolog\u00eda", principioActivo.getPosologia())
                    ));

                    lyInfoAdicional.setOnClickListener(new CategoryClickHandler(
                            new PairHeadDetail("Informaci\u00f3n Adicional", principioActivo.getInformacionAdicional())
                    ));

                    lyInteracciones.setOnClickListener(new CategoryClickHandler(
                            new PairHeadDetail("Interacciones", principioActivo.getInteracciones())
                    ));

                    lyOtrosNombres.setOnClickListener(new CategoryClickHandler(
                            new PairHeadDetail("Otros Nombres", principioActivo.getOtrosNombres())
                    ));

                    lyReaccionesAdversas.setOnClickListener(new CategoryClickHandler(
                            new PairHeadDetail("Reacciones Adversas", principioActivo.getReaccionesAdversas())
                    ));
                    lyReferencias.setOnClickListener(new CategoryClickHandler(
                            new PairHeadDetail("Referencias", principioActivo.getReferencias())
                    ));
                } else {

                    int color = getResources().getColor(R.color.anmat_azul);
                    txtNoResultados.setVisibility(View.VISIBLE);
                    txtNoResultados.setText("Sin Detalle");
                    txtNoResultados.setTextColor(color);

                }
            }
        } finally {
            if (dh != null)
                dh.close();
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
            TextView txtPrincipioActivo = (TextView) findViewById(R.id.txt_principio);
            if (!txtPrincipioActivo.getText().toString().isEmpty()) {
                Intent i = new Intent(DetallePrincipioActivoActivity.this,
                        DetalleMedicamentoListActivity.class);

                if (principioActivo != null) {
                    i.putExtra("COMERCIAL_RECOMENDADO", principioActivo.getIfa());
                } else {
                    i.putExtra("COMERCIAL_RECOMENDADO", txtPrincipioActivo.getText().toString());
                }

                FormularioBusqueda f = new FormularioBusqueda();

                String campoBusquedaCompleta = txtPrincipioActivo.getText().toString();

                if (principioActivo != null && principioActivo.getOtrosNombres() != null && !principioActivo.getOtrosNombres().isEmpty()) {
                    campoBusquedaCompleta = principioActivo.getIfa() + "?" + principioActivo.getOtrosNombres();
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

    @Override
    protected void onResume() {
        super.onResume();
        ((MiAplicacion) this.getApplication()).updateCache(new DatabaseHelper(this), false);
    }

    private class CategoryClickHandler implements View.OnClickListener{

        private PairHeadDetail[] toRender;

        public CategoryClickHandler(PairHeadDetail... toRender) {
            this.toRender = toRender;
        }

        @Override
        public void onClick(View view) {
            Bundle b = new Bundle();
            b.putSerializable("toRender",this.toRender);
            Intent i = new Intent(DetallePrincipioActivoActivity.this,DetalleCategoriaActivity.class);
            i.putExtras(b);
            DetallePrincipioActivoActivity.this.startActivity(i);

        }
    }

}
