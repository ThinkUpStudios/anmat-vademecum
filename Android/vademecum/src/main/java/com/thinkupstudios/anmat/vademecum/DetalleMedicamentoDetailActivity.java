package com.thinkupstudios.anmat.vademecum;

import android.app.ActionBar;
import android.os.Bundle;

import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;


/**
 * An activity representing a single DetalleMedicamento detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link DetalleMedicamentoListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link DetalleMedicamentoDetailFragment}.
 */
public class DetalleMedicamentoDetailActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_medicamento);

        // Show the Up button in the action bar.
        ActionBar actionBar = getActionBar();
        if(actionBar!=null) actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
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


}
