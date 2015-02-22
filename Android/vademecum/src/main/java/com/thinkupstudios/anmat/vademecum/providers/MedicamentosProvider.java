package com.thinkupstudios.anmat.vademecum.providers;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;

import java.util.List;
import java.util.Vector;

/**
 * Created by FaQ on 19/02/2015.
 */
public class MedicamentosProvider {

    public List<MedicamentoBO> getMedicamentos(FormularioBusqueda form){
        List<MedicamentoBO> list = new Vector<>();
        MedicamentoBO m = new MedicamentoBO();
        m.setLaboratorio("LABORATORIOS BETA S.A.");
        m.setNombreGenerico("PAROXETINA 10 MG + ROSUVASTATINA 10 MG ");
        m.setForma("COMPRIMIDO RECUBIERTO");
        m.setNombreComercial("PSICOASTEN 10 mg");
        m.setNumeroCertificado("47191");
        m.setPrecio(" $  139.19 ");
        list.add(m);

        m = new MedicamentoBO();
        m.setLaboratorio("ROEMMERS S A I C F");
        m.setNombreGenerico("PAROXETINA 10 MG + ROSUVASTATINA 10 MG");
        m.setForma("COMPRIMIDO RECUBIERTO");
        m.setNombreComercial("ROVARTAL 10");
        m.setNumeroCertificado("33439");
        m.setPrecio(" $  88.48 ");
        list.add(m);

        m = new MedicamentoBO();
        m.setLaboratorio("INVESTI FARMA S A");
        m.setNombreGenerico("GLIBENCLAMIDA 5 MG");
        m.setForma("COMPRIMIDO RECUBIERTO");
        m.setNombreComercial("EUGLUCON");
        m.setNumeroCertificado("51205");
        m.setPrecio(" $  30.80 ");
        list.add(m);

        m = new MedicamentoBO();
        m.setCondicionExpendio("BAJO RECETA");
        m.setCondicionTrazabilidad("1831/12 Anexo");
        m.setForma("COMPRIMIDO RECUBIERTO");
        m.setGtin("07795345012681");
        m.setLaboratorio("LABORATORIO DOMINGUEZ S A");
        m.setNombreComercial("NULITE");
        m.setNombreGenerico("BROMURO DE PINAVERIO 100 MG");
        m.setPaisIndustria("Argentina");
        m.setNumeroCertificado("47191");
        m.setPresentacion("BLISTER por 10 UNIDADES");
        m.setPrecio("49,94");
        m.setTroquel("515460");
        list.add(m);
        return list;


    }
}
