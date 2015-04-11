package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.bo.Component;
import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;

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

    public List<MedicamentoBO> findMedicamentos(FormularioBusqueda form) {
        String where = "";
        if(form != null && (!form.getNombreGenerico().isEmpty() || !form.getNombreComercial().isEmpty() || !form.getLaboratorio().isEmpty())){
            where += "where  1=1 ";

            if(!form.getNombreComercial().isEmpty())
                where += " and " + MedicamentosTable.COLUMNS[6] + " like '%"+form.getNombreComercial()+"%'";
            if(!form.getNombreGenerico().isEmpty()){
                if(form.useLike()) {
                    where += " and " + MedicamentosTable.COLUMNS[8] + " like '%" + form.getNombreGenerico() + "%'";
                }
                else{
                    where += " and " + MedicamentosTable.COLUMNS[8] + " = '" + form.getNombreGenerico() + "'";
                }
            }


            if(!form.getLaboratorio().isEmpty())
                where += " and " + MedicamentosTable.COLUMNS[3] + " like '%"+form.getLaboratorio()+"%'";
        }

        List<MedicamentoBO> medicamentoBOs = new ArrayList<MedicamentoBO>();

        String orderBy = "order by "+ MedicamentosTable.COLUMNS[13] + " asc ";

        Cursor cursor = this.getAllByWhere(MedicamentosTable.TABLE_NAME, where, orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MedicamentoBO medicamento = cursorToMedicamentoBO(cursor);
            medicamentoBOs.add(medicamento);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        if(form.filtrarPorFormula()){
            medicamentoBOs = this.filtrarPorFormula(medicamentoBOs, form.getNombreGenerico());
        }
        List<MedicamentoBO> medicamentosOrdenados = this.ordenarMedicamentos(medicamentoBOs);
        return medicamentosOrdenados;
    }

    private List<MedicamentoBO> ordenarMedicamentos(List<MedicamentoBO> medicamentoBOs) {
        List<MedicamentoBO> medOrdenados = new Vector<>();
        List<MedicamentoBO> medUsoHospitalario = new Vector<>();
        List<MedicamentoBO> medSinPrecio = new Vector<>();
        for (MedicamentoBO medicamento : medicamentoBOs){
            if(medicamento.getPrecio().equals(MedicamentoBO.UH)){
                medUsoHospitalario.add(medicamento);
            }
            else if(medicamento.getPrecio().equals(MedicamentoBO.SIN_PRECIO)){
                medSinPrecio.add(medicamento);
            }
            else {
                medOrdenados.add(medicamento);
            }
        }
        medOrdenados.addAll(medUsoHospitalario);
        medOrdenados.addAll(medSinPrecio);
        return medOrdenados;
    }


    private List<MedicamentoBO> filtrarPorFormula(List<MedicamentoBO> medicamentoBOs, String principioActivo) {
        List<MedicamentoBO> resultadosFiltrados = new Vector<>();
        Component c = new Component();
        c.setActiveComponent(principioActivo);
        for(MedicamentoBO medicamento : medicamentoBOs){

            if(medicamento.getFormula().getComponents().contains(c))
                resultadosFiltrados.add(medicamento);
        }
        return resultadosFiltrados;
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
        medicamento.setEsUsoHospitalario(cursor.getInt(cursor.getColumnIndex(MedicamentosTable.COLUMNS[14])));
        return medicamento;
    }

}
