package com.thinkupstudios.anmat.vademecum.bo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.R;

import java.util.List;

/**
 * Created by FaQ on 19/02/2015.
 */
public class ResultadoAdapter extends BaseAdapter{

    /*********** Declare Used Variables *********/
    private Context activity;
    private List<MedicamentoBO> data;
    private static LayoutInflater inflater=null;
    public Resources res;
    private MedicamentoBO tempValues = null;

    int i=0;

    public ResultadoAdapter(Context activity, List<MedicamentoBO> data) {
        this.activity = activity;
        this.data = data;
    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        int color = 0;
        if(convertView==null){

            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) this.activity
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.list_item_layout, null);
            }

        }  else {
            holder = (ViewHolder) convertView.getTag();
        }
            /****** View Holder Object to contain tabitem.xml file elements ******/

        if(position % 2 == 1){
            convertView.setBackgroundResource(R.drawable.list_item_background);


        }else{
            convertView.setBackgroundResource(R.drawable.list_item_background2);

        }
            color = convertView.getResources().getColor(R.color.anmat_azul);
            holder = new ViewHolder();
            holder.nombreComercial = (TextView) convertView.findViewById(R.id.txt_nombre_comercial);
            holder.nombreComercial.setTextColor(color);
            holder.nombreGenerico=(TextView)convertView.findViewById(R.id.txt_nombre_generico);
            holder.nombreGenerico.setTextColor(color);
            holder.precio=(TextView)convertView.findViewById(R.id.txt_precio);
            holder.precio.setTextColor(color);
            holder.forma=(TextView)convertView.findViewById(R.id.txt_forma);
            holder.forma.setTextColor(color);
            holder.laboratorio=(TextView)convertView.findViewById(R.id.txt_laboratorio);
            holder.laboratorio.setTextColor(color);
            holder.presentacion=(TextView)convertView.findViewById(R.id.txt_presentacion);
            holder.presentacion.setTextColor(color);
            holder.numeroCertificado=(TextView)convertView.findViewById(R.id.txt_numero_certificado);
            holder.numeroCertificado.setTextColor(color);

            /************  Set holder with LayoutInflater ************/
            convertView.setTag( holder );

        if(data.size()<=0){
            holder.nombreGenerico.setText("Sin Resultados");
            holder.nombreComercial.setText(" ");
            holder.numeroCertificado.setText(" ");
            holder.laboratorio.setText(" ");
            holder.presentacion.setText(" ");
            holder.forma.setText(" ");
            holder.precio.setText(" ");
        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( MedicamentoBO ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.nombreGenerico.setText( tempValues.getNombreGenerico());
            holder.nombreComercial.setText( tempValues.getNombreComercial());
            holder.numeroCertificado.setText(tempValues.getNumeroCertificado());
            holder.presentacion.setText(tempValues.getPresentacion());
            holder.laboratorio.setText(tempValues.getLaboratorio());
            holder.forma.setText(tempValues.getForma());
            holder.precio.setText(tempValues.getPrecio());
            /******** Set Item Click Listner for LayoutInflater for each row *******/


        }
        convertView.setSelected(true);
        return convertView;
    }
    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView nombreGenerico;
        public TextView nombreComercial;
        public TextView laboratorio;
        public TextView presentacion;
        public TextView precio;
        public TextView forma;
        public TextView numeroCertificado;
    }

}
