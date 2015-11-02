package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.bo.GrupoPrincipiosEmbarazo;
import com.thinkupstudios.anmat.vademecum.bo.PrincipioClasificacion;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.GrupoPrincipioEmbTable;
import com.thinkupstudios.anmat.vademecum.providers.tables.GrupoTable;

import java.util.List;
import java.util.Vector;

/**
 * Created by dcamarro on 30/10/2015.
 */
public class DetalleEmbarazoProvider extends GenericProvider {


    public DetalleEmbarazoProvider(DatabaseHelper db) {
        super(db);
    }

    public String getDetalleHTML() {
        String html = this.buildCabecera();

        List<GrupoPrincipiosEmbarazo> grupos = this.findGrupo();
        String grupoHtml = this.crearGrupos(grupos);

        html = html + grupoHtml;
        return html;
    }

    private String buildCabecera() {
        return "<!-- Cabecera -->\n" +
                "<div style='background-color:#CCCCCC;padding-top:20px ' >\n" +
                "  <span style='color:#1B5687; '>Principios activos con su factor de riesgo según la FDA, durante el embarazo</span>  \n" +
                "</div>\n" +
                "<hr style=\"color: #CCCCCC;margin:0px\" />\n" +
                "<div style='color:#848484;padding-top:5px; margin-bottom: 10px'  >\n" +
                "  <span style='color:#000000; '>\n" +
                "  La FDA según el riesgo clasifica los fármacos en las siguientes categorías:\n" +
                "Categoría A: Estudios adecuados y bien controlados no han demostrado riesgo para el feto en el primer trimestre de embarazo y no existen evidencias de riesgo en trimestres posteriores. La posibilidad de daño fetal parece remota.\n" +
                "Categoría B: Indica una de las siguientes posibilidades:\n" +
                "- Estudios en animales no indican riesgo teratogénico fetal, pero esto no ha sido confirmado en embarazadas\n" +
                "- Estudios en animales muestran cierto potencial teratógeno, pero estudios bien controlados con gestantes no han demostrado riesgos para el feto en el primer trimestre y no existen evidencias para el feto en trimestres posteriores\n" +
                "Categoría C: Se suele asignar a fármacos cuya administración solo debe considerarse si el beneficio esperado justifica el potencial riesgo para el feto. Indica una de las sig. posibilidades:\n" +
                "- Estudios sobre animales han detectado efecto teratógeno o embriocida del fármaco, pero aún no se ha ensayado en la mujer.\n" +
                "- No se dispone de estudios ni en animales ni en mujeres.\n" +
                "Categoría D: Se dispone de evidencia de efectos teratógenos sobre el feto humano y por tanto de la existencia de un claro riesgo. Sin embargo el beneficio obtenido con estos medicamentos puede superar el riesgo esperado y hacer aconsejable su uso(situaciones límites de posible muerte materna, afecciones graves en las que no es posible usar alternativas más seguras o éstas son ineficaces…)\n" +
                "Categoría X: Estudios en animales o en  humanos han demostrado anomalías congénitas manifiestas, existen evidencias de riesgo fetal basadas enla experiencia en embarazadas y los riesgos superan claramente cualquier posible beneficio a obtener, por lo que los fármacos están absolutamente contraindicados.\n" +
                "  </span>  \n" +
                "</div>";
    }


    private String crearGrupos(List<GrupoPrincipiosEmbarazo> grupos) {
        String gruposHTML = "";
        for (GrupoPrincipiosEmbarazo g : grupos) {
            String grupoHTML = this.buildGrupoHTML(g.getNombreGrupo());
            for (PrincipioClasificacion principio : g.getPrincipios()) {
                String itemHTML = this.buildItemHTML(principio);
                grupoHTML = grupoHTML.concat(itemHTML);
            }
            gruposHTML = gruposHTML.concat(grupoHTML);
        }
        return gruposHTML;
    }

    private String buildItemHTML(PrincipioClasificacion principio) {
        String itemHTML = "<hr style=\"color: #CCCCCC;margin:0px\" />\n" +
                "<div style='padding-top:5px'>\n" +
                "<!-- Principio -->\n" +
                "<div>\n" +
                "  <span style='color:#1B5687; '>#PRINCIPIO#</span>    \n" +
                "</div>\n" +
                "<!-- Medicamentos -->\n" +
                "<div style='padding-top:7px'>\n" +
                "  <span style='color:66000000'>#MEDICAMENTOS#</span>\n" +
                "</div>\n" +
                "<div style='color:#848484;padding-top:5px'  >\n" +
                "  <span>#CLASIFICACION#</span>  \n" +
                "</div>\n" +
                "</div>\n";
        itemHTML = itemHTML.replace("#PRINCIPIO#", principio.getPrincipio());
        itemHTML = itemHTML.replace("#MEDICAMENTOS#", principio.getMedicamentos());
        itemHTML = itemHTML.replace("#CLASIFICACION#", principio.getClasificacion());
        return itemHTML;
    }

    private String buildGrupoHTML(String nombreGrupo) {
        String grupoTemplate = "<div style='background-color:#CCCCCC;padding-top:30px;margin:0px' >\n" +
                "  <span style='color:#1B5687; '>#GRUPO#</span>    \n" +
                "</div>";
        grupoTemplate = grupoTemplate.replace("#GRUPO#", nombreGrupo);
        return grupoTemplate;
    }


    private List<GrupoPrincipiosEmbarazo> findGrupo() {

        Cursor cursor = null;
        List<GrupoPrincipiosEmbarazo> grupos = new Vector<>();

        try {

            cursor = this.getAllByWhere(GrupoTable.TABLE_NAME, null, null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                GrupoPrincipiosEmbarazo g = this.crearGrupo(cursor);
                grupos.add(g);
                cursor.moveToNext();
            }
        } finally {
            // make sure to close the cursor
            cursor.close();
        }

        return grupos;
    }

    private GrupoPrincipiosEmbarazo crearGrupo(Cursor cursor) {

        Cursor cursorPrincipios = null;
        GrupoPrincipiosEmbarazo g = new GrupoPrincipiosEmbarazo();
        g.setNombreGrupo(cursor.getString(cursor.getColumnIndex(GrupoTable.COLUMNS[1])));

        try {
            String where = " where " + GrupoPrincipioEmbTable.COLUMN_GRUPO_ID + " = " + cursor.getString(cursor.getColumnIndex(GrupoTable.COLUMNS[0]));
            cursorPrincipios = this.getAllByWhere(GrupoPrincipioEmbTable.TABLE_NAME, where, null);

            cursorPrincipios.moveToFirst();
            PrincipioClasificacion p;
            while (!cursorPrincipios.isAfterLast()) {
                p = this.crearPrincipioClasificacion(cursorPrincipios);
                g.addPrincipio(p);
                cursorPrincipios.moveToNext();
            }
        } finally {
            // make sure to close the cursor
            cursorPrincipios.close();
        }
        return g;
    }

    private PrincipioClasificacion crearPrincipioClasificacion(Cursor cursorPrincipios) {
        PrincipioClasificacion p = new PrincipioClasificacion();
        p.setPrincipio(cursorPrincipios.getString(cursorPrincipios.getColumnIndex(GrupoPrincipioEmbTable.COLUMNS[1])));
        p.setMedicamentos(cursorPrincipios.getString(cursorPrincipios.getColumnIndex(GrupoPrincipioEmbTable.COLUMNS[2])));
        p.setClasificacion(cursorPrincipios.getString(cursorPrincipios.getColumnIndex(GrupoPrincipioEmbTable.COLUMNS[3])));

        return p;
    }

}
