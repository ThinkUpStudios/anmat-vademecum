package com.thinkupstudios.anmat.vademecum.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 * Created by FaQ on 19/02/2015.
 * amended by dario 20/02/2015
 */
public class MedicamentoBO implements Serializable {

    public static String MEDICAMENTOBO = "MEDICAMENTO_BO";
    private String nombreComercial = "-";
    private String nombreGenerico= "-";
    private String numeroCertificado= "-";
    private String precio= "-";
    private String laboratorio= "-";
    private String forma= "-";
    private String paisIndustria= "-";
    private String condicionExpendio= "-";
    private String condicionTrazabilidad= "-";
    private String presentacion= "-";
    private String gtin= "-";
    private String troquel= "-";
    private String cuit= "-";

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        if(!cuit.isEmpty())
        this.cuit = cuit;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        if(!nombreComercial.isEmpty())

            this.nombreComercial = nombreComercial;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        if(!nombreGenerico.isEmpty())
        this.nombreGenerico = nombreGenerico;
    }

    public String getNumeroCertificado() {
        return numeroCertificado;
    }

    public void setNumeroCertificado(String numeroCertificado) {
        if(!numeroCertificado.isEmpty())
        this.numeroCertificado = numeroCertificado;
    }

    public String getPrecio() {
        if(!"-".equals(precio)){
            return ("$ " +precio);
        }
        return precio;
    }

    public void setPrecio(String precio) {
        if(!precio.isEmpty())
        this.precio = precio;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        if(!laboratorio.isEmpty())
        this.laboratorio = laboratorio;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        if(!forma.isEmpty())
        this.forma = forma;
    }

    public String getPaisIndustria() {
        return paisIndustria;
    }

    public void setPaisIndustria(String paisIndustria){
        if(!paisIndustria.isEmpty())
        this.paisIndustria = paisIndustria;
    }

    public String getCondicionExpendio() {
        return condicionExpendio;
    }

    public void setCondicionExpendio(String condicionExpendio) {
        if(!condicionExpendio.isEmpty())
        this.condicionExpendio = condicionExpendio;
    }

    public String getCondicionTrazabilidad() {
        return condicionTrazabilidad;
    }

    public void setCondicionTrazabilidad(String condicionTrazabilidad) {
        if(!condicionTrazabilidad.isEmpty())
        this.condicionTrazabilidad = condicionTrazabilidad;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        if(!presentacion.isEmpty())
        this.presentacion = presentacion;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        if(!gtin.isEmpty())
        this.gtin = gtin;
    }

    public String getTroquel() {
        return troquel;
    }

    public void setTroquel(String troquel) {
        if(!troquel.isEmpty())
        this.troquel = troquel;
    }

    public List<FormulaMedicamento> getFormula() {
        List<FormulaMedicamento> formulaLista = new Vector<>();

        String[] formulas = this.getNombreGenerico().split("\\+");
        for(String formulaTemporal : formulas){

            FormulaMedicamento formulaMedicamento = this.parsearFormula(formulaTemporal.trim());
            formulaLista.add(formulaMedicamento);

        }
        return formulaLista;
    }

    private FormulaMedicamento parsearFormula(String formulaTemporal) {

        FormulaMedicamento formulaMedicamento = new FormulaMedicamento();

        int i = 0;

        boolean continuar = true;
        //Primero busco la droga.
        String droga = new String();

            while (continuar && i <formulaTemporal.length()) {
                char caracter = formulaTemporal.charAt(i);
                if (!Character.isDigit(caracter) && (caracter != ',')) {
                    droga += caracter;
                    i++;

                } else continuar = false;
            }
        formulaMedicamento.setIfa(droga);

        //Ahora busca la cantidad.
        continuar = true;
        String cantidad = new String();
        while (continuar && i < formulaTemporal.length()){
            char caracter = formulaTemporal.charAt(i);
            if(Character.isDigit(caracter) || (caracter == ',')){
                cantidad += caracter;
                i++;
            }
            else continuar = false;
        }
        formulaMedicamento.setCantidad(cantidad);

        formulaMedicamento.setUnidad(formulaTemporal.substring(i,formulaTemporal.length()));

        return formulaMedicamento;
    }
}
