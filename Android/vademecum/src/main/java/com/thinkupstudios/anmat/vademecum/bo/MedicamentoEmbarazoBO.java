package com.thinkupstudios.anmat.vademecum.bo;

import java.util.List;

/**
 * Created by dcamarro on 12/11/2015.
 */
public class MedicamentoEmbarazoBO {

    private String cabecera;
    private List<GrupoPrincipiosEmbarazo> grupos;

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public List<GrupoPrincipiosEmbarazo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoPrincipiosEmbarazo> grupos) {
        this.grupos = grupos;
    }
}
