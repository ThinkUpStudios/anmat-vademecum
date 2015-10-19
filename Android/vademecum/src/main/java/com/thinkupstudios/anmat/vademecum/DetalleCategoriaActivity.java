package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.bo.PairHeadDetail;


/**
 * An activity representing a single DetalleMedicamento detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link DetalleMedicamentoListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link DetalleMedicamentoDetailFragment}.
 */
public class DetalleCategoriaActivity extends Activity {

    PairHeadDetail[] toRender;
    public final static String TO_RENDER = "toRender";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_categoria);
        TextView sinDetalle  = (TextView)findViewById(R.id.sin_detalle);
        sinDetalle.setVisibility(View.GONE);
        if(savedInstanceState!= null){
            if(savedInstanceState.containsKey(TO_RENDER)){
                toRender = (PairHeadDetail[]) savedInstanceState.getSerializable(TO_RENDER);
                render();
            }
        }else{
            if(this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey(TO_RENDER)){
                this.toRender = (PairHeadDetail[]) this.getIntent().getExtras().getSerializable(TO_RENDER);
                render();
            }else{
                sinDetalle.setVisibility(View.VISIBLE);
            }
        }
    }

    private void render() {
        LinearLayout content = (LinearLayout)findViewById(R.id.ly_content);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view ;
        for(PairHeadDetail p : toRender){
            view  = inflater.inflate(R.layout.header_detail,null);
            ((TextView)view.findViewById(R.id.txt_header)).setText(p.getHeader());
            ((TextView)view.findViewById(R.id.txt_detail)).setText(p.getDetail());
            content.addView(view);
        }



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(TO_RENDER,this.toRender);
        super.onSaveInstanceState(outState);
    }


}
