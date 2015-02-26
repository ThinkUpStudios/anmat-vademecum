package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallemedicamento_list);
        if(getIntent().getExtras() != null && getIntent().getStringExtra("COMERCIAL_RECOMENDADO")!= null){
            this.setTitle("Comercial: "+getIntent().getStringExtra("COMERCIAL_RECOMENDADO"));
        }
        if (findViewById(R.id.detallemedicamento_detail_container) != null) {
            mTwoPane = true;
            DetalleMedicamentoListFragment fragment =
                    ((DetalleMedicamentoListFragment) getFragmentManager()
                            .findFragmentById(R.id.detallemedicamento_list));

                    fragment.setActivateOnItemClick(true);
            Bundle arguments = new Bundle();

            arguments.putSerializable(FormularioBusqueda.FORMULARIO_MANUAL,
                    this.getIntent().getExtras().getSerializable(FormularioBusqueda.FORMULARIO_MANUAL));
            fragment.setArguments(arguments);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /**
     * Callback method from {@link DetalleMedicamentoListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(MedicamentoBO m) {

        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putSerializable(MedicamentoBO.MEDICAMENTOBO, m);
            DetalleMedicamentoDetailFragment fragment = new DetalleMedicamentoDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.detallemedicamento_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, DetalleMedicamentoDetailActivity.class);
            detailIntent.putExtra(MedicamentoBO.MEDICAMENTOBO, m);
            startActivity(detailIntent);
            overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnu_buscar:
                startActivity(new Intent(this, BusquedaMedicamentoActivity.class));
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
 }
