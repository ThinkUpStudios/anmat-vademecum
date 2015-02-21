package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;
import com.thinkupstudios.anmat.vademecum.bo.ResultadoAdapter;

import java.util.List;
import java.util.Vector;


/**
 * An activity representing a single DetalleMedicamento detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link DetalleMedicamentoListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link DetalleMedicamentoDetailFragment}.
 */
public class DetalleMedicamentoDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_medicamento);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            arguments.putSerializable(MedicamentoBO.MEDICAMENTOBO,
                    getIntent().getSerializableExtra(MedicamentoBO.MEDICAMENTOBO));

            DetalleMedicamentoDetailFragment fragment = new DetalleMedicamentoDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.detallemedicamento_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, DetalleMedicamentoListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private MedicamentoBO getMedicamentoDummy(){
        MedicamentoBO m = new MedicamentoBO();
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
        return m;
    }
}
