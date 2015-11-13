package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.bo.GrupoPrincipiosEmbarazo;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoEmbarazoBO;
import com.thinkupstudios.anmat.vademecum.bo.PairHeadDetail;
import com.thinkupstudios.anmat.vademecum.bo.ToRenderWrapper;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;


public class DetalleMedicamentoEmbarazoActivity extends Activity {


    private MedicamentoEmbarazoBO medicamentoEmbarazoBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MiAplicacion app = (MiAplicacion) this.getApplicationContext();
        this.medicamentoEmbarazoBO = app.getMedicamentoEmbarazoBO();


        setContentView(R.layout.activity_detalle_medicamento_embarazo);
        TextView txtCabecera = (TextView) findViewById(R.id.txt_medicamento_embarazo);
        txtCabecera.setText(Html.fromHtml("<p><b>La FDA según el riesgo clasifica los fármacos en las siguientes categorías:</b></p>" +
                "<p><b>Categoría A:</b></p>" +
                "<p>Estudios adecuados y bien controlados no han demostrado riesgo para el feto en el primer trimestre de embarazo y no existen evidencias de riesgo en trimestres posteriores. La posibilidad de daño fetal parece remota.</p>" +
                "<p><b>Categoría B:</b></p>" +
                "<p>Indica una de las siguientes posibilidades:</p>" +
                "<p> • Estudios en animales no indican riesgo teratogénico fetal, pero esto no ha sido confirmado en embarazadas</p>" +
                "<p> • Estudios en animales muestran cierto potencial teratógeno, pero estudios bien controlados con gestantes no han demostrado riesgos para el feto en el primer trimestre y no existen evidencias para el feto en trimestres posteriores</p>" +
                "<p><b>Categoría C:</b></p>" +
                "<p>Se suele asignar a fármacos cuya administración solo debe considerarse si el beneficio esperado justifica el potencial riesgo para el feto.</p>" +
                "<p>Indica una de las sig. posibilidades:</p>" +
                "<p> • Estudios sobre animales han detectado efecto teratógeno o embriocida del fármaco, pero aún no se ha ensayado en la mujer.</p>" +
                "<p> • No se dispone de estudios ni en animales ni en mujeres.</p>" +
                "<p><b>Categoría D:</b></p>" +
                "<p>Se dispone de evidencia de efectos teratógenos sobre el feto humano y por tanto de la existencia de un claro riesgo. Sin embargo el beneficio obtenido con estos medicamentos puede superar el riesgo esperado y hacer aconsejable su uso(situaciones límites de posible muerte materna, afecciones graves en las que no es posible usar alternativas más seguras o éstas son ineficaces…)</p>  " +
                "<p><b>Categoría X:</b></p>" +
                "<p>Estudios en animales o en  humanos han demostrado anomalías congénitas manifiestas, existen evidencias de riesgo fetal basadas enla experiencia en embarazadas y los riesgos superan claramente cualquier posible beneficio a obtener, por lo que los fármacos están absolutamente contraindicados.</p>"));


        for (GrupoPrincipiosEmbarazo grupo : this.medicamentoEmbarazoBO.getGrupos()) {
            this.armarGrupo(grupo);
        }


        ScrollView scrollContent = (ScrollView) findViewById(R.id.scroll_content);


    }

    private void armarGrupo(final GrupoPrincipiosEmbarazo grupo) {
        LinearLayout contenedor = (LinearLayout) findViewById(R.id.relative_contenedor);
        LayoutInflater inflater = LayoutInflater.from(this);
        View laViewInflada = inflater.inflate(R.layout.grupos_item_list, null);
        TextView nombreGrupo = (TextView) laViewInflada.findViewById(R.id.txt_nombre_grupo);
        nombreGrupo.setText(grupo.getNombreGrupo());
        contenedor.addView(laViewInflada);
        laViewInflada.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle b = new Bundle();
                /*
                PairHeadDetail[] toRender = new PairHeadDetail[1];
                toRender[0] = new PairHeadDetail(grupo.getNombreGrupo(), grupo.getPrincipiosHTML());

                b.putSerializable("toRender", new ToRenderWrapper(toRender));
                Intent i = new Intent(DetalleMedicamentoEmbarazoActivity.this, DetalleCategoriaActivity.class);
                i.putExtras(b);
                DetalleMedicamentoEmbarazoActivity.this.startActivity(i);
                */
                String strContent = grupo.getPrincipiosHTML();
                Intent i = new Intent(DetalleMedicamentoEmbarazoActivity.this, EmbarazoActivity.class);

                b.putString(DetalleHTMLActivity.DETAIL_CONTENT, strContent);
                i.putExtras(b);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        ((MiAplicacion) this.getApplication()).updateCache(new DatabaseHelper(this), false);
    }

    private class CategoryClickHandler implements View.OnClickListener {

        private PairHeadDetail[] toRender;

        public CategoryClickHandler(PairHeadDetail[] toRender) {
            this.toRender = toRender;
        }

        @Override
        public void onClick(View view) {
            Bundle b = new Bundle();
            b.putSerializable("toRender", new ToRenderWrapper(this.toRender));
            Intent i = new Intent(DetalleMedicamentoEmbarazoActivity.this, DetalleCategoriaActivity.class);
            i.putExtras(b);
            DetalleMedicamentoEmbarazoActivity.this.startActivity(i);

        }
    }

}
