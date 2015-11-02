package com.thinkupstudios.anmat.vademecum.bo;

/**
 * Created by dcamarro on 31/10/2015.
 */
public class PrincipioClasificacion {

    private String principio ="";
    private String medicamentos= "";
    private String clasificacion= "";

    public String getPrincipio() {
        if(principio == null || principio.isEmpty()){
            return "-";
        }
        return principio;
    }

    public void setPrincipio(String principio) {
        this.principio = principio;
    }

    public String getMedicamentos() {
        if(medicamentos == null || medicamentos.isEmpty()){
            return "-";
        }
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getClasificacion() {
        if(clasificacion == null || clasificacion.isEmpty()){
            return "-";
        }
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {

        this.clasificacion = clasificacion;
    }
}
