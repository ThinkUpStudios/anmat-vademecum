package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.components.ClearableAutoCompliteEditText;

import java.util.List;

public class BuscarPrincipioActivoActivity extends Activity implements AdapterView.OnItemClickListener {
private ClearableAutoCompliteEditText principioActivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_principio_activo);
        this.principioActivo = (ClearableAutoCompliteEditText)findViewById(R.id.txt_principio_activo);

        MiAplicacion app = (MiAplicacion) this.getApplicationContext();
        List<String> principiosActivos = app.getPrincipiosActivos();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, principiosActivos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.principioActivo.setAdapter(dataAdapter);

        this.principioActivo.setOnItemClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnu_buscar:
                startActivity(new Intent(this, BusquedaMedicamentoActivity.class));
                return true;
            case R.id.mnu_informacion:
                startActivity(new Intent(this, InformacionActivity.class));
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String principio = (String)this.principioActivo.getAdapter().getItem(position);

            Intent i = new Intent(this, DetallePrincipioActivoActivity.class);

            if(principio.isEmpty()){
                Toast.makeText(this, R.string.sin_campo_busqueda, Toast.LENGTH_LONG).show();
            }else{
                i.putExtra(FormularioBusqueda.PRINCIPIO_ACTIVO,principio);
                startActivity(i);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);

            }
        }
}
