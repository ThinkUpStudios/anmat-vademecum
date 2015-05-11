package com.thinkupstudios.anmat.vademecum.providers;

import android.database.Cursor;

import com.thinkupstudios.anmat.vademecum.bo.Component;
import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;
import com.thinkupstudios.anmat.vademecum.bo.comparadores.ComparadorPrecios;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by FaQ on 19/02/2015.
 * Provider de Medicamentos
 */
public class MedicamentosProvider extends GenericProvider {


    public MedicamentosProvider(DatabaseHelper helper){
        super(helper);
    }

    public List<MedicamentoBO> findMedicamentos(FormularioBusqueda form) {
        String where = "";
        Cursor cursor = null;

        try {
            if (form != null && (!form.getNombreGenerico().isEmpty() || !form.getNombreComercial().isEmpty() || !form.getLaboratorio().isEmpty())) {
                where += "where  1=1 ";

                if (form.getNombreComercial() != null && !form.getNombreComercial().isEmpty())
                    where += " and " + MedicamentosTable.COLUMNS[6] + " like '%" + form.getNombreComercial() + "%'";
                if (!form.getNombreGenerico().isEmpty()) {
                    where += this.armarWhereANDyOR(form.getNombreGenerico(), form.useLike(), MedicamentosTable.COLUMNS[8]);

                }

                if (!form.getLaboratorio().isEmpty())
                    where += " and " + MedicamentosTable.COLUMNS[3] + " like '%" + form.getLaboratorio() + "%'";
            }

            List<MedicamentoBO> medicamentoBOs = new ArrayList<>();

            String orderBy = "order by " + MedicamentosTable.COLUMNS[13] + " asc ";

            cursor = this.getAllByWhere(MedicamentosTable.TABLE_NAME, where, orderBy);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MedicamentoBO medicamento = cursorToMedicamentoBO(cursor);
                medicamentoBOs.add(medicamento);
                cursor.moveToNext();
            }

            if (form != null && form.filtrarPorFormula()) {
                medicamentoBOs = this.filtrarPorFormula(medicamentoBOs, form.getNombreGenerico());
            }

            return this.ordenarMedicamentos(medicamentoBOs);
        }
        finally {
            // make sure to close the cursor
            if(cursor != null)
                cursor.close();
        }

    }

    private String armarWhereANDyOR(String campo, Boolean useLike, String column) {
        String where = "";
        String[] filtros = campo.split("\\?");
        if(filtros.length > 1){
            int i = 0;
            for(String dentroDelOR : filtros){
                String whereAnd = this.armarWhereAnd(dentroDelOR, useLike, column);
                if(i == 0)
                    where += " and "+ whereAnd;
                else where += whereAnd;
                i++;
                if(filtros.length != i){
                    where += " or ";
                }

            }
        }else{
            where = " and "+ this.armarWhereAnd(campo, useLike, column);
        }

        return where;
    }

    private String armarWhereAnd(String campo, Boolean useLike, String column) {
        String whereAnd = "";
        String[] ands = campo.split("#");
        String aux = " ";
        if(ands.length > 1){
            int i = 0;

            for(String valor : ands ){
                if(i != 0){
                    aux += " and ";
                }
                if(useLike) {
                    String like = "'%" + valor.trim() + "%'";
                    whereAnd += aux + this.matarCaracteresEspeciales(column) + " like " + this.matarCaracteresEspeciales(like) + " ";
                }else{
                    String campoString = "'" + campo.trim() + "'";
                    whereAnd += aux + this.matarCaracteresEspeciales(column) + " = " + this.matarCaracteresEspeciales(campoString) + " ";
                }
                i++;
            }
        }
        else{
            if(useLike) {
                String like = "'%" + campo.trim() + "%'";
                whereAnd += aux + this.matarCaracteresEspeciales(column) + " like " + this.matarCaracteresEspeciales(like) + "";
            }else{
                String campoString = "'" + campo.trim() + "'";
                whereAnd += aux + this.matarCaracteresEspeciales(column) + " = " + this.matarCaracteresEspeciales(campoString) + " ";
            }
        }
        return whereAnd;
    }


    private List<MedicamentoBO> ordenarMedicamentos(List<MedicamentoBO> medicamentoBOs) {
       /* List<MedicamentoBO> medOrdenados = new Vector<>();
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
        medOrdenados.addAll(medSinPrecio);*/
        Collections.sort(medicamentoBOs, new ComparadorPrecios());
        return medicamentoBOs;

    }




    private List<MedicamentoBO> filtrarPorFormula(List<MedicamentoBO> medicamentoBOs, String principioActivo) {
        List<MedicamentoBO> resultadosFiltrados = new Vector<>();

        String[] principiosActivos = principioActivo.split("\\?");
        List<String> principios = Arrays.asList(principiosActivos);

        for (MedicamentoBO medicamento : medicamentoBOs) {
            boolean existe = false;
            for(Component compuesto : medicamento.getFormula().getComponents()) {
                if(principios.contains(compuesto.getActiveComponent())){
                    existe = true;
                }
            }
            if (existe) {
                resultadosFiltrados.add(medicamento);
            }
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
        medicamento.setEsRemediar(cursor.getInt(cursor.getColumnIndex(MedicamentosTable.COLUMNS[15])));
        return medicamento;
    }

}
