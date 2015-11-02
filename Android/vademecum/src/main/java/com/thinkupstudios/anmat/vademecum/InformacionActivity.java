package com.thinkupstudios.anmat.vademecum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;


/**
 * Created by dcamarro on 02/03/2015.
 * Actividad que muestra la pantalla de Información
 */
public class InformacionActivity extends NoMenuActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info);

        View lyNavigate = findViewById(R.id.ly_prescricpcion_nom_gen);
        lyNavigate.setOnClickListener(this);
        lyNavigate = findViewById(R.id.ly_uso_racional);
        lyNavigate.setOnClickListener(this);
        lyNavigate = findViewById(R.id.ly_sist_nac_traza);
        lyNavigate.setOnClickListener(this);
        lyNavigate = findViewById(R.id.ly_medicamentos_embarazo);
        lyNavigate.setOnClickListener(this);
        lyNavigate = findViewById(R.id.ly_efectos_adversos);
        lyNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlEA = getString(R.string.url_efectos_adversos);
                Intent iEA = new Intent(Intent.ACTION_VIEW);
                iEA.setData(Uri.parse(urlEA));
                startActivity(iEA);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MiAplicacion) this.getApplication()).updateCache(new DatabaseHelper(this), false);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        Bundle b = new Bundle();
        String strContent = "";
        switch (v.getId()) {

            case R.id.ly_prescricpcion_nom_gen: {
                i = new Intent(this, DetalleHTMLActivity.class);
                strContent = "<p class='c0'><b>&iquest;Qu&eacute; es el nombre gen&eacute;rico?</b></p>" +
                        "<p class='c0'><b>Nombre gen&eacute;rico</b>" +
                        "<span class='c1'>&nbsp;es la denominaci&oacute;n de un principio activo, monodroga &nbsp;o de una " +
                        "asociaci&oacute;n de principios activos a dosis fijas adoptada por la autoridad sanitaria (ANMAT)" +
                        " &nbsp;o, en su defecto, la denominaci&oacute;n com&uacute;n internacional recomendada por la Organizaci&oacute;n " +
                        "Mundial de la Salud.</span></p><p class='c0'><span class='c1'>Seg&ugrave;n la ley 25.649 es obligatorio que </span>" +
                        "<b>toda receta m&eacute;dica exprese el nombre gen&eacute;rico del medicamento o denominaci&oacute;n com&uacute;n" +
                        " internacional</b><span class='c1'>, seguida de forma farmac&eacute;utica y dosis/ unidad, con detalle del grado de" +
                        " concentraci&oacute;n. &nbsp;</span></p><p class='c0'><span class='c1'>De esta forma, el farmac&eacute;utico debe " +
                        "ofrecer todas las opciones disponibles.</span></p><p class='c0'><span class='c1'>La receta puede indicar, " +
                        "adem&aacute;s del nombre gen&eacute;rico, el nombre o marca comercial. En ese caso, si el consumidor lo solicita, " +
                        "el farmac&eacute;utico debe sustituir la misma por una especialidad medicinal de menor precio que contenga " +
                        "los mismos principios activos, concentraci&oacute;n, forma farmac&eacute;utica y similar cantidad de unidades. " +
                        "</span></p><p class='c0'><span class='c1'>La libertad de prescripci&oacute;n y de dispensa est&aacute; " +
                        "garantizada por la elecci&oacute;n del principio activo y no sobre especialidades de referencia o de marca." +
                        " </span></p><p class='c0'><span class='c1'>El uso de nombre gen&eacute;rico es obligatorio en:</span></p>" +
                        "<p class='c0'><span class='c1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a) Todo envase, r&oacute;tulo," +
                        " prospecto o cualquier documento utilizado por la industria farmac&eacute;utica para informaci&oacute;n " +
                        "m&eacute;dica o promoci&oacute;n de las especialidades medicinales;</span></p><p class='c0'>" +
                        "<span class='c1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b) Todos los textos normativos, " +
                        "inclusive registros y autorizaciones relativas a la elaboraci&oacute;n, fraccionamiento, comercializaci&oacute;n, " +
                        "exportaci&oacute;n e importaci&oacute;n de medicamentos;</span></p><p class='c0'><span class='c1'>&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;c) Toda publicidad o propaganda dirigida al p&uacute;blico en general.</span>" +
                        "</p><p class='c0 c5'><span class='c1'></span></p>";

                b.putString(DetalleHTMLActivity.DETAIL_CONTENT, strContent);
                break;
            }

            case R.id.ly_uso_racional: {
                i = new Intent(this, DetalleHTMLActivity.class);
                break;
            }
            case R.id.ly_sist_nac_traza: {
                i = new Intent(this, DetalleHTMLActivity.class);

                strContent = "<p class='c0'><b>&iquest;Qu&eacute; es el sistema de Trazabilidad?</b>" +
                        "</p><p class='c0'><span class='c1'>Es un sistema de control que consiste en la </span><b>identificaci&oacute;n </b>" +
                        "<span class='c1'>de cada uno de los medicamentos que van a ser comercializados y su </span><b>seguimiento " +
                        "</b><span class='c1'>a trav&eacute;s de toda la </span><b>cadena de distribuci&oacute;n</b>" +
                        "<span class='c1'>&nbsp;(laboratorios, distribuidoras, operadores log&iacute;sticos, farmacias, " +
                        "establecimientos asistenciales) &nbsp;para erradicar los que sean ileg&iacute;timos.</span>" +
                        "</p><p class='c0 c5'><span class='c1'></span></p><p class='c0'>";
                b.putString(DetalleHTMLActivity.DETAIL_CONTENT, strContent);

                break;
            }

            case R.id.ly_medicamentos_embarazo: {
                i = new Intent(this, EmbarazoActivity.class);
                MiAplicacion app = (MiAplicacion) this.getApplicationContext();
                strContent = app.getHtmlEmbarazo();
                b.putString(DetalleHTMLActivity.DETAIL_CONTENT, strContent);
                break;
            }
            default:
                break;
        }
        if(i!=null){
            i.putExtras(b);
            startActivity(i);
        }

    }
}
