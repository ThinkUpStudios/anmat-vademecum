package com.thinkupstudios.anmat.vademecum;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;

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
public class DetalleMedicamentoListActivity extends NoMenuActivity
        implements DetalleMedicamentoListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

private View selectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallemedicamento_list);
        if(getIntent().getExtras() != null && getIntent().getStringExtra("COMERCIAL_RECOMENDADO")!= null){
            this.setTitle(getIntent().getStringExtra("COMERCIAL_RECOMENDADO"));
        }
        if (findViewById(R.id.detallemedicamento_detail_container) != null) {
            mTwoPane = true;
            DetalleMedicamentoListFragment fragment =
                    ((DetalleMedicamentoListFragment) getFragmentManager()
                            .findFragmentById(R.id.detallemedicamento_list));

                    fragment.setActivateOnItemClick(true);

        }
    }
    /**
     * Callback method from {@link DetalleMedicamentoListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(MedicamentoBO m, View item) {
        if(this.selectedView!= null){
            this.selectedView.getBackground().clearColorFilter();
        }
        this.selectedView = item;

        selectedView.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.DARKEN);


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
            overridePendingTransition(fade_in, fade_out);
        }

    }
 }
