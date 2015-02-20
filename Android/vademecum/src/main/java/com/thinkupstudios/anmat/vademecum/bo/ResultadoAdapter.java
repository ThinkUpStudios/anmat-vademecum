package com.thinkupstudios.anmat.vademecum.bo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.DetalleMedicamentoListActivity;
import com.thinkupstudios.anmat.vademecum.R;

import java.util.ArrayList;
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

        if(convertView==null){

            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) this.activity
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.list_item_layout, null);
            }

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.nombreComercial = (TextView) convertView.findViewById(R.id.txt_nombre_comercial);
            holder.nombreGenerico=(TextView)convertView.findViewById(R.id.txt_nombre_generico);
            holder.precio=(TextView)convertView.findViewById(R.id.txt_precio);
            holder.forma=(TextView)convertView.findViewById(R.id.txt_forma);
            holder.numeroCertificado=(TextView)convertView.findViewById(R.id.txt_numero_certificado);

            /************  Set holder with LayoutInflater ************/
            convertView.setTag( holder );
        }  else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(data.size()<=0){
            holder.nombreGenerico.setText("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( MedicamentoBO ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.nombreGenerico.setText( tempValues.getNombreGenerico() );
            holder.nombreComercial.setText( tempValues.getNombreComercial() + " - " + tempValues.getLaboratorio() );
            holder.numeroCertificado.setText(tempValues.getNumeroCertificado());
            holder.forma.setText(tempValues.getForma());
            holder.precio.setText(tempValues.getPrecio());
            /******** Set Item Click Listner for LayoutInflater for each row *******/


        }
        return convertView;
    }
    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView nombreGenerico;
        public TextView nombreComercial;
        public TextView precio;
        public TextView forma;
        public TextView numeroCertificado;
    }

}
