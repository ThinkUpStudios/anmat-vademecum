package com.thinkupstudios.anmat.vademecum.bo;

import java.util.List;
import java.util.Vector;

/**
 * Created by dcamarro on 31/10/2015.
 */
public class GrupoPrincipiosEmbarazo {

    private String nombreGrupo;
    private List<PrincipioClasificacion> principios;
    private String principiosHTML;

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public List<PrincipioClasificacion> getPrincipios() {
        return principios;
    }

    public void addPrincipio(PrincipioClasificacion p){
        if(this.principios == null){
            this.principios = new Vector<>();
        }
        this.principios.add(p);
    }

    public String getPrincipiosHTML() {
        return principiosHTML;
    }

    public void setPrincipiosHTML(String principiosHTML) {
        this.principiosHTML = principiosHTML;
    }

}
