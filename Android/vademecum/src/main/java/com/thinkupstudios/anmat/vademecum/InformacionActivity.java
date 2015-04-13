package com.thinkupstudios.anmat.vademecum;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by dcamarro on 02/03/2015.
 * Actividad que muestra la pantalla de Información
 */
public class InformacionActivity extends MenuActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView viewInfo;
        setContentView(R.layout.activity_info);

        viewInfo = (TextView) findViewById(R.id.txt_informacion);
        viewInfo.setText(
                Html.fromHtml("<h3> Información General </h3>\n" +
                        "<b>Nombre genérico</b> es la denominación de un principio activo, monodroga  o de una asociación\n" +
                        "de principios activos a dosis fijas adoptada por la autoridad sanitaria (ANMAT)  o, en su\n" +
                        "defecto, la denominación común internacional recomendada por la Organización Mundial de\n" +
                        "la Salud.\n" +
                        "Segùn la ley 25.649 es obligatorio que <b>toda receta médica exprese el nombre genérico del\n" +
                        "medicamento o denominación común internacional</b>, seguida de forma farmacéutica y dosis/\n" +
                        "unidad, con detalle del grado de concentración.\n" +
                        "De esta forma, el farmacéutico debe ofrecer todas las opciones disponibles.\n" +
                        "La receta puede indicar, además del nombre genérico, el nombre o marca comercial. En ese\n" +
                        "caso, si el consumidor lo solicita, el farmacéutico debe sustituir la misma por una especialidad\n" +
                        "medicinal de menor precio que contenga los mismos principios activos, concentración, forma\n" +
                        "farmacéutica y similar cantidad de unidades.\n" +
                        "La libertad de prescripción y de dispensa está garantizada por la elección del principio activo y\n" +
                        "no sobre especialidades de referencia o de marca.\n" +
                        "<br/>\n" +
                        "<br/>\n" +
                        "<b>El uso de nombre genérico es obligatorio en: </b><br/>\n" +
                        "<br/>\n" +
                        "a) todo envase, rótulo, prospecto o cualquier documento utilizado por la industria\n" +
                        "farmacéutica para información médica o promoción de las especialidades medicinales;<br/>\n" +
                        "<br/>\n" +
                        " b) todos los textos normativos, inclusive registros y autorizaciones relativas a la elaboración,\n" +
                        "fraccionamiento, comercialización, exportación e importación de medicamentos;<br/>\n" +
                        "<br/>\n" +
                        "c) toda publicidad o propaganda dirigida al público en general.")
                );

    }

}
