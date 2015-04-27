package com.thinkupstudios.anmat.vademecum;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.bo.VersionBo;
import com.thinkupstudios.anmat.vademecum.providers.VersionProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

/**
 * Created by dcamarro on 02/03/2015.
 * Pantalla de ACerca de
 */
public class AcercaDeActivity extends NoMenuActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper(this);
            VersionProvider vp = new VersionProvider(dbHelper);

            VersionBo version = vp.getVersionBo();

            setContentView(R.layout.activity_acerca_de);
            TextView viewAcercaDe;
            viewAcercaDe = (TextView) findViewById(R.id.txt_acerca_de);
            viewAcercaDe.setText(
                    Html.fromHtml("<h3> Acerca de Vademecum Nacional de Medicamentos </h3>\n" +
                            "<p>\n" +
                            "ANMAT Vademecum se creó para proveer información sobre medicamentos: \n" +
                            "sus principios activos, nombres comerciales, laboratorios y precios oficiales, entre otros. \n" +
                            "</p>\n" +
                            "<p>\n" +
                            "Esta app fue desarrollada a partir de información brindada por el Ministerio de Salud de la Nación – ANMAT.\n" +
                            "</p>\n" +
                            "<p>\n" +
                            "Los datos están actualizados al " + version.getUltimaActualizacion() + " .\n" +
                            "<p>\n" +

                            "Si tenes dudas de cualquier tipo referidas a medicamentos, alimentos, productos médicos, " +
                            "cosméticos, domisanitarios, reactivos de diagnóstico, faltantes, trámites " +
                            "o legislación comunicate ANMAT Responde  al 0800-333-1234 o vía mail a responde@anmat.gov.ar" +
                            "</p>\n" +

                            "<i>Copyright: Ministerio de Salud de la Nación – ANMAT.</i>\n" +
                            "</p>\n" +
                            "<p >\n" +
                            "<font color=#77000000>\n" +
                            "<i>Versión: 2.0.</i>\n" +
                            "</font>\n" +
                            "</p>")
            );
        }finally {
            if(dbHelper != null)
                dbHelper.close();
        }

    }


}
