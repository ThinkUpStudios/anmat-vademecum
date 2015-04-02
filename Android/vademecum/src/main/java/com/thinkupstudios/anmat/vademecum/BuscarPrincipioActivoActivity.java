package com.thinkupstudios.anmat.vademecum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.components.ClearableAutoCompliteEditText;

import java.util.List;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;

public class BuscarPrincipioActivoActivity extends MenuActivity implements AdapterView.OnItemClickListener {
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String principio = (String)this.principioActivo.getAdapter().getItem(position);

            Intent i = new Intent(this, DetallePrincipioActivoActivity.class);

            if(principio.isEmpty()){
                Toast.makeText(this, R.string.sin_campo_busqueda, Toast.LENGTH_LONG).show();
            }else{
                i.putExtra(FormularioBusqueda.PRINCIPIO_ACTIVO,principio);
                startActivity(i);
                overridePendingTransition(fade_in, fade_out);

            }
        }
}
