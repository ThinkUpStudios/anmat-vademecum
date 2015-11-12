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
        txtCabecera.setText(Html.fromHtml(medicamentoEmbarazoBO.getCabecera()));


        for (GrupoPrincipiosEmbarazo grupo:this.medicamentoEmbarazoBO.getGrupos()) {
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
            public void onClick(View v)
            {
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
