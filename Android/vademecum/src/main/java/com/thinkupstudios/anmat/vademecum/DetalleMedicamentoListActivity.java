package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;


/**
 * An activity representing a list of ResultadosMedicamentos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DetalleMedicamentoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link DetalleMedicamentoListFragment} and the item details
 * (if present) is a {@link DetalleMedicamentoDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link DetalleMedicamentoListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class DetalleMedicamentoListActivity extends Activity
        implements DetalleMedicamentoListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private MedicamentoBO medicamento;
    private AlertDialog orderDialog;
    private DetalleMedicamentoDetailFragment fragmentDetalle;
    private DetalleMedicamentoListFragment fragmentLista;

    private View selectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallemedicamento_list);
        if (getIntent().getExtras() != null && getIntent().getStringExtra("COMERCIAL_RECOMENDADO") != null) {
            this.setTitle(getIntent().getStringExtra("COMERCIAL_RECOMENDADO"));
        }
        if (findViewById(R.id.detallemedicamento_detail_container) != null) {
            mTwoPane = true;
            fragmentLista =
                    ((DetalleMedicamentoListFragment) getFragmentManager()
                            .findFragmentById(R.id.detallemedicamento_list));

            fragmentLista.setActivateOnItemClick(true);

        }
    }

    /**
     * Callback method from {@link DetalleMedicamentoListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(MedicamentoBO m, View item) {
        if (this.selectedView != null) {
            this.selectedView.getBackground().clearColorFilter();
        }
        this.selectedView = item;

        selectedView.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.DARKEN);


        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            this.medicamento = m;
            arguments.putSerializable(MedicamentoBO.MEDICAMENTOBO, m);
            this.fragmentDetalle = new DetalleMedicamentoDetailFragment();
            fragmentDetalle.setArguments(arguments);

            getFragmentManager().beginTransaction()
                    .replace(R.id.detallemedicamento_detail_container, fragmentDetalle)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, DetalleMedicamentoDetailActivity.class);
            detailIntent.putExtra(MedicamentoBO.MEDICAMENTOBO, m);
            startActivity(detailIntent);
            overridePendingTransition(fade_in, fade_out);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (mTwoPane) {
            getMenuInflater().inflate(R.menu.detalle_menu, menu);
            return true;
        } else {
            getMenuInflater().inflate(R.menu.resultado_menu, menu);
            return true;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mnu_recomendados) {
            if (this.medicamento == null) {
                return false;
            }
            Intent i = new Intent(this,
                    DetalleMedicamentoListActivity.class);
            i.putExtra("COMERCIAL_RECOMENDADO", this.medicamento.getNombreComercial());
            FormularioBusqueda f = new FormularioBusqueda();
            f.setNombreGenerico(this.medicamento.getNombreGenerico());
            f.setUseLike(false);
            f.setFiltrarPorFormula(false);
            i.putExtra(FormularioBusqueda.FORMULARIO_MANUAL, f);
            startActivity(i);
            this.overridePendingTransition(fade_in, fade_out);
            return true;
        } else if (item.getItemId() == R.id.mnu_ordenar) {
            if(this.fragmentLista== null) {
                this.fragmentLista = ((DetalleMedicamentoListFragment) getFragmentManager() .findFragmentById(R.id.detallemedicamento_list));
            }
            this.fragmentLista.ordenar();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MiAplicacion) this.getApplication()).updateCache(new DatabaseHelper(this), false);
    }
}
