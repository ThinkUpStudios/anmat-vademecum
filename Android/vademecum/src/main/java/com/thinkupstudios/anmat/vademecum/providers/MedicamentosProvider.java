package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.LaboratorioTable;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by FaQ on 19/02/2015.
 */
public class MedicamentosProvider extends GenericProvider {

    public MedicamentosProvider(DatabaseHelper helper){
        super(helper);
    }

    private List<MedicamentoBO> getMedicamentosDummy(FormularioBusqueda form){
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

    public List<MedicamentoBO> findMedicamentos(FormularioBusqueda form) {
        String where = "";
        if(form != null && (!form.getNombreGenerico().isEmpty() || !form.getNombreComercial().isEmpty() || !form.getLaboratorio().isEmpty())){
            where += "where  1=1 ";

            if(!form.getNombreComercial().isEmpty())
                where += " and " + MedicamentosTable.COLUMNS[5] + " like '%"+form.getNombreComercial()+"%'";
            if(!form.getNombreGenerico().isEmpty())
                where += " and " + MedicamentosTable.COLUMNS[7] + " like '%"+form.getNombreGenerico()+"%'";
            if(!form.getLaboratorio().isEmpty())
                where += " and " + MedicamentosTable.COLUMNS[2] + " like '%"+form.getLaboratorio()+"%'";
        }

        List<MedicamentoBO> medicamentoBOs = new ArrayList<MedicamentoBO>();

        Cursor cursor = this.getAllByWhere(MedicamentosTable.TABLE_NAME, where);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MedicamentoBO medicamento = cursorToMedicamentoBO(cursor);
            medicamentoBOs.add(medicamento);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return medicamentoBOs;
    }

    private MedicamentoBO cursorToMedicamentoBO(Cursor cursor) {
        MedicamentoBO medicamento = new MedicamentoBO();
        medicamento.setNumeroCertificado(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[0])));
        medicamento.setCuit(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[1])));
        medicamento.setLaboratorio(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[2])));
        medicamento.setGtin(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[3])));
        medicamento.setTroquel(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[4])));
        medicamento.setNombreComercial(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[5])));
        medicamento.setForma(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[6])));
        medicamento.setNombreGenerico(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[7])));
        medicamento.setPaisIndustria(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[8])));
        medicamento.setCondicionExpendio(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[9])));
        medicamento.setCondicionTrazabilidad(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[10])));
        medicamento.setPresentacion(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[11])));
        medicamento.setPrecio(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[12])));

        return medicamento;
    }

}
