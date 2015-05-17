package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;
import com.thinkupstudios.anmat.vademecum.bo.ResultadoAdapter;
import com.thinkupstudios.anmat.vademecum.bo.comparadores.ComparadorFormaFarmaceutica;
import com.thinkupstudios.anmat.vademecum.bo.comparadores.ComparadorNombreComercial;
import com.thinkupstudios.anmat.vademecum.bo.comparadores.ComparadorNombreGenerico;
import com.thinkupstudios.anmat.vademecum.bo.comparadores.ComparadorPrecios;
import com.thinkupstudios.anmat.vademecum.providers.MedicamentosProvider;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * A list fragment representing a list of ResultadosMedicamentos. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link DetalleMedicamentoDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class DetalleMedicamentoListFragment extends ListFragment implements DialogInterface.OnClickListener{
private AlertDialog orderDialog;
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private List<MedicamentoBO> resultados = new Vector<>();

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(MedicamentoBO id, View item) {
        }
    };
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;
    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DetalleMedicamentoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new BuscarResultadosTask().execute();


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //getListView().setSelector(android.R.drawable.share_pack_holo_dark);


        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(getListView(), view, position, id);

        if (resultados.size() > 0) {
            MedicamentoBO bo = resultados.get(position);
            mCallbacks.onItemSelected(bo, view);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    public void ordenar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Elija un Ordenamiento");
        final CharSequence[] items = {" Precio ", " Forma Farmacológica ", " Nombre Genérico ", " Nombre Comercial "};
        builder.setSingleChoiceItems(items, -1, this);
        orderDialog = builder.create();
        orderDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnu_ordenar) {
            this.ordenar();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int item) {
        switch (item) {
            case 0:
                Collections.sort(this.resultados, new ComparadorPrecios());
                setListAdapter(new ResultadoAdapter(this.getActivity(),this.resultados));
                break;
            case 1:
                // Your code when 2nd  option seletced
                Collections.sort(this.resultados, new ComparadorFormaFarmaceutica());
                setListAdapter(new ResultadoAdapter(this.getActivity(),this.resultados));
                break;
            case 2:
                // Your code when 3rd option seletced
                Collections.sort(this.resultados, new ComparadorNombreGenerico());
                setListAdapter(new ResultadoAdapter(this.getActivity(),this.resultados));
                break;
            case 3:
                // Your code when 4th  option seletced
                Collections.sort(this.resultados, new ComparadorNombreComercial());
                setListAdapter(new ResultadoAdapter(this.getActivity(),this.resultados));
                break;

        }



        orderDialog.dismiss();
    }
    public class BuscarResultadosTask extends AsyncTask<Integer, Integer, List<MedicamentoBO>> {
        @Override
        protected List<MedicamentoBO> doInBackground(Integer... params) {
            DatabaseHelper dh = null;
            List<MedicamentoBO> resultados = null;
            try {
                dh = new DatabaseHelper(DetalleMedicamentoListFragment.this.getActivity());
                MedicamentosProvider provider = new MedicamentosProvider(dh);
                resultados = provider.findMedicamentos((FormularioBusqueda)
                                        DetalleMedicamentoListFragment.this.getActivity().getIntent().getExtras()
                                                .getSerializable(FormularioBusqueda.FORMULARIO_MANUAL)
                                    );
            }finally {
                if(dh != null)
                    dh.close();
            }
            return resultados;
        }


        @Override
        protected void onPostExecute(List<MedicamentoBO> resultados) {
            super.onPostExecute(resultados);
            actualizarResultados(resultados);
        }
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(MedicamentoBO medicamento, View item);
    }

    public void actualizarResultados(List<MedicamentoBO> resultados){
        this.resultados = resultados;
        setListAdapter(new ResultadoAdapter(this.getActivity(),this.resultados));
    }
}
