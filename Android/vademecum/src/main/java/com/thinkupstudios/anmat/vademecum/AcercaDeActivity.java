package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by dcamarro on 02/03/2015.
 */
public class AcercaDeActivity extends Activity {

    private TextView viewAcercaDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        viewAcercaDe = (TextView) findViewById(R.id.txt_acerca_de);
        viewAcercaDe.setText(
                Html.fromHtml("<h3> Acerca de Ammet Vademecum </h3>\n" +
                        "<p>\n" +
                        "ANMAT Vademecum se creó para proveer información sobre medicamentos: \n" +
                        "sus principios activos, nombres comerciales, laboratorios y precios oficiales, entre otros. \n" +
                        "</p>\n" +
                        "<p>\n" +
                        "Esta app fue desarrollada a partir de información brindada por el Ministerio de Salud de la Nación – ANMAT.\n" +
                        "</p>\n" +
                        "<p>\n" +
<<<<<<< HEAD
                        "Los datos están actualizados al 15 de febrero del 2015.\n" +
=======
                        "Los datos están actualizados al 10 de febrero del 2015.\n\n Holis" +
>>>>>>> 6917bbb7b4532d2b8be1466e940329535f124029
                        "<p>\n" +
                        "<i>Copyright: Ministerio de Salud de la Nación – ANMAT.</i>\n" +
                        "</p>\n" +
                        "<p >\n" +
                        "<font color=#77000000>\n" +
                        "<i>Versión: 2.0.</i>\n" +
                        "</font>\n" +
                        "</p>")
        );

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnu_informacion:
                startActivity(new Intent(this, AcercaDeActivity.class));
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
