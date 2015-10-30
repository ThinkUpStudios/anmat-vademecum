package com.thinkupstudios.anmat.vademecum;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;


/**
 * Created by dcamarro on 02/03/2015.
 * Actividad que muestra la pantalla de Información
 */
public class DetalleHTMLActivity extends NoMenuActivity {

    public static final String DETAIL_CONTENT = "DETAIL_CONTENT";
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView viewInfo;
        setContentView(R.layout.activity_detalle_html);

        restoreContent(savedInstanceState);

        viewInfo = (TextView) findViewById(R.id.txt_detalle);
        viewInfo.setText(Html.fromHtml(content));

    }

    private void restoreContent(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(DETAIL_CONTENT)) {
            content = savedInstanceState.getString(DETAIL_CONTENT);
        } else if (this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey(DETAIL_CONTENT)) {
            content = this.getIntent().getExtras().getString(DETAIL_CONTENT);

        } else {
            content = "Sin contenido";
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DETAIL_CONTENT, content);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MiAplicacion) this.getApplication()).updateCache(new DatabaseHelper(this), false);
    }
}
