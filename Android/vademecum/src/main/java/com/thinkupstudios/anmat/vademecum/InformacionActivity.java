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
                Html.fromHtml("<p class='c0'><b>&iquest;Qu&eacute; es el nombre gen&eacute;rico?</b></p>" +
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
                        "</p><p class='c0 c5'><span class='c1'></span></p><p class='c0'><b>&iquest;Qu&eacute; es el sistema de Trazabilidad?</b>" +
                        "</p><p class='c0'><span class='c1'>Es un sistema de control que consiste en la </span><b>identificaci&oacute;n </b>" +
                        "<span class='c1'>de cada uno de los medicamentos que van a ser comercializados y su </span><b>seguimiento " +
                        "</b><span class='c1'>a trav&eacute;s de toda la </span><b>cadena de distribuci&oacute;n</b>" +
                        "<span class='c1'>&nbsp;(laboratorios, distribuidoras, operadores log&iacute;sticos, farmacias, " +
                        "establecimientos asistenciales) &nbsp;para erradicar los que sean ileg&iacute;timos.</span>" +
                        "</p><p class='c0 c5'><span class='c1'></span></p><p class='c0'><span class='c1'>Si tenes dudas de cualquier" +
                        " tipo referidas a medicamentos, alimentos, productos m&eacute;dicos, cosm&eacute;tcos, domisanitarios, reactivos de " +
                        "diagn&oacute;stico, faltantes, tr&aacute;mites o legislaci&oacute;n comunicate ANMAT Responde al 0800-333-1234 o v&iacute;a mail a responde@anmat.gov.ar</span></p>")
        );

    }

}
