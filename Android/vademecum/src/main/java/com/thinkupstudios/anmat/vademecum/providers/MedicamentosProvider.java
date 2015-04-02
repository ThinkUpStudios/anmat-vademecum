package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FaQ on 19/02/2015.
 */
public class MedicamentosProvider extends GenericProvider {

    public MedicamentosProvider(DatabaseHelper helper){
        super(helper);
    }

    public List<MedicamentoBO> findMedicamentos(FormularioBusqueda form) {
        String where = "";
        if(form != null && (!form.getNombreGenerico().isEmpty() || !form.getNombreComercial().isEmpty() || !form.getLaboratorio().isEmpty())){
            where += "where  1=1 ";

            if(!form.getNombreComercial().isEmpty())
                where += " and " + MedicamentosTable.COLUMNS[6] + " like '%"+form.getNombreComercial()+"%'";
            if(!form.getNombreGenerico().isEmpty())
                where += " and " + MedicamentosTable.COLUMNS[8] + " like '%"+form.getNombreGenerico()+"%'";
            if(!form.getLaboratorio().isEmpty())
                where += " and " + MedicamentosTable.COLUMNS[3] + " like '%"+form.getLaboratorio()+"%'";
        }

        List<MedicamentoBO> medicamentoBOs = new ArrayList<MedicamentoBO>();

        String orderBy = "order by "+ MedicamentosTable.COLUMNS[13] + " desc ";

        Cursor cursor = this.getAllByWhere(MedicamentosTable.TABLE_NAME, where, orderBy);

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
        medicamento.setNumeroCertificado(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[1])));
        medicamento.setCuit(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[2])));
        medicamento.setLaboratorio(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[3])));
        medicamento.setGtin(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[4])));
        medicamento.setTroquel(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[5])));
        medicamento.setNombreComercial(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[6])));
        medicamento.setForma(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[7])));
        medicamento.setNombreGenerico(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[8])));
        medicamento.setPaisIndustria(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[9])));
        medicamento.setCondicionExpendio(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[10])));
        medicamento.setCondicionTrazabilidad(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[11])));
        medicamento.setPresentacion(cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[12])));
        String precio = cursor.getString(cursor.getColumnIndex(MedicamentosTable.COLUMNS[13]));
        medicamento.setPrecio(precio);

        return medicamento;
    }

}
