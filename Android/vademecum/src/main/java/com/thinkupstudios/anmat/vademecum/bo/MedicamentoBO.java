package com.thinkupstudios.anmat.vademecum.bo;

import java.io.Serializable;

/**
 * Created by FaQ on 19/02/2015.
 * amended by dario 20/02/2015
 */
public class MedicamentoBO implements Serializable {

    public static String MEDICAMENTOBO = "MEDICAMENTO_BO";
    public static String UH = "U.H.";
    public static String USO_HOSPITALARIO = "Producto de Uso Hospitalario";
    public static String SIN_PRECIO = "$ - ";
    private String nombreComercial = "-";
    private String nombreGenerico = "-";
    private String numeroCertificado = "-";
    private String precio;
    private String laboratorio = "-";
    private String forma = "-";
    private String paisIndustria = "-";
    private String condicionExpendio = "-";
    private String condicionTrazabilidad = "-";
    private String presentacion = "-";
    private String gtin = "-";
    private String troquel = "-";
    private String cuit = "-";
    private boolean esUsoHospitalario = false;
    private Formula formula;

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        if (!cuit.isEmpty())
            this.cuit = cuit;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        if (!nombreComercial.isEmpty())

            this.nombreComercial = nombreComercial;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        if (!nombreGenerico.isEmpty())
            this.nombreGenerico = nombreGenerico;
    }

    public String getNumeroCertificado() {
        return numeroCertificado;
    }

    public void setNumeroCertificado(String numeroCertificado) {
        if (!numeroCertificado.isEmpty())
            this.numeroCertificado = numeroCertificado;
    }

    public String getPrecio() {
        if (this.precio == null || this.precio.isEmpty()) {
            if (this.isEsUsoHospitalario()) {
                return (MedicamentoBO.UH);
            } else {
                return (MedicamentoBO.SIN_PRECIO);
            }
        } else {

            return ("$ " + precio);
        }
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        if (!laboratorio.isEmpty())
            this.laboratorio = laboratorio;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        if (!forma.isEmpty())
            this.forma = forma;
    }

    public String getPaisIndustria() {
        return paisIndustria;
    }

    public void setPaisIndustria(String paisIndustria) {
        if (!paisIndustria.isEmpty())
            this.paisIndustria = paisIndustria;
    }

    public String getCondicionExpendio() {
        return condicionExpendio;
    }

    public void setCondicionExpendio(String condicionExpendio) {
        if (!condicionExpendio.isEmpty())
            this.condicionExpendio = condicionExpendio;
    }

    public String getCondicionTrazabilidad() {
        return condicionTrazabilidad;
    }

    public void setCondicionTrazabilidad(String condicionTrazabilidad) {
        if (!condicionTrazabilidad.isEmpty())
            this.condicionTrazabilidad = condicionTrazabilidad;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        if (!presentacion.isEmpty())
            this.presentacion = presentacion;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        if (!gtin.isEmpty())
            this.gtin = gtin;
    }

    public String getTroquel() {
        return troquel;
    }

    public void setTroquel(String troquel) {
        if (!troquel.isEmpty())
            this.troquel = troquel;
    }

    public Formula getFormula(){
       if(this.formula == null){
           this.formula =  FormulaParser.parse(this.getNombreGenerico());
       }
       return this.formula;
    }

    public void setEsUsoHospitalario(Integer esUsoHospitalario) {
        if(esUsoHospitalario != null){
            this.esUsoHospitalario = esUsoHospitalario == 0 ? false : true;
        }
        else this.esUsoHospitalario = false;
    }

    public boolean isEsUsoHospitalario() {
        return esUsoHospitalario && this.precio== null;
    }

    public String getUsoHospitalarioLabel(){
        if(this.getPrecio().equals(UH)){
            return USO_HOSPITALARIO;
        }else{
            return " ";
        }
    }
}
