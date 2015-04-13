package com.thinkupstudios.anmat.vademecum;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;


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

    private MedicamentoBO medicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_medicamento);

        // Show the Up button in the action bar.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            this.setMedicamento(
                    (MedicamentoBO) getIntent().getSerializableExtra(MedicamentoBO.MEDICAMENTOBO));
            arguments.putSerializable(MedicamentoBO.MEDICAMENTOBO, this.getMedicamento());

            DetalleMedicamentoDetailFragment fragment = new DetalleMedicamentoDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.detallemedicamento_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detalle_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnu_recomendados) {
            Intent i = new Intent(this,
                    DetalleMedicamentoListActivity.class);
            i.putExtra("COMERCIAL_RECOMENDADO", this.getMedicamento().getNombreComercial());
            FormularioBusqueda f = new FormularioBusqueda();
            f.setNombreGenerico(getMedicamento().getNombreGenerico());
            f.setUseLike(false);
            f.setFiltrarPorFormula(false);
            i.putExtra(FormularioBusqueda.FORMULARIO_MANUAL, f);
            startActivity(i);
            this.overridePendingTransition(fade_in, fade_out);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    public MedicamentoBO getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(MedicamentoBO medicamento) {
        this.medicamento = medicamento;
    }
}
