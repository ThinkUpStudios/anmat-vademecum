package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.bo.FormulaMedicamento;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;
import com.thinkupstudios.anmat.vademecum.dummy.DummyContent;

import java.util.List;

/**
 * A fragment representing a single DetalleMedicamento detail screen.
 * This fragment is either contained in a {@link DetalleMedicamentoListActivity}
 * in two-pane mode (on tablets) or a {@link DetalleMedicamentoDetailActivity}
 * on handsets.
 */
public class DetalleMedicamentoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private MedicamentoBO medicamento;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DetalleMedicamentoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().containsKey(MedicamentoBO.MEDICAMENTOBO)) {
            this.medicamento = (MedicamentoBO) getArguments().getSerializable(MedicamentoBO.MEDICAMENTOBO);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detallemedicamento_detail, container, false);

        this.setValores(rootView, this.medicamento, container);

        TabHost tabs=(TabHost)rootView.findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.detalle);
        spec.setIndicator("Detalle");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.formula);
        spec.setIndicator("Formula");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);
            }
        });


        Button recomendados = (Button) rootView.findViewById(R.id.btn_recomentados);
        recomendados.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DetalleMedicamentoDetailFragment.this.getActivity(),
                        DetalleMedicamentoListActivity.class);
                i.putExtra("COMERCIAL_RECOMENDADO", medicamento.getNombreComercial());
                startActivity(i);
            }
        });

        return rootView;
    }


    public void setValores(View rootView, MedicamentoBO m, ViewGroup container){


        ((TextView)rootView.findViewById(R.id.condicion_de_expendioValor)).setText(m.getCondicionExpendio());
        ((TextView)rootView.findViewById(R.id.condicion_de_trazabilidadValor)).setText(m.getCondicionTrazabilidad());
        ((TextView)rootView.findViewById(R.id.forma_farmaceuticaValor)).setText(m.getForma());
        ((TextView)rootView.findViewById(R.id.gtinValor)).setText(m.getGtin());
        ((TextView)rootView.findViewById(R.id.laboratorioValor)).setText(m.getLaboratorio());
        ((TextView)rootView.findViewById(R.id.nombre_comercialValor)).setText(m.getNombreComercial());
        ((TextView)rootView.findViewById(R.id.nombre_generico_Valor)).setText(m.getNombreGenerico());
        ((TextView)rootView.findViewById(R.id.pais_industriaValor)).setText(m.getPaisIndustria());
        ((TextView)rootView.findViewById(R.id.nroCertificadoValor)).setText(m.getNumeroCertificado());
        ((TextView)rootView.findViewById(R.id.presentacionValor)).setText(m.getPresentacion());
        ((TextView)rootView.findViewById(R.id.precioValor)).setText(m.getPrecio());
        ((TextView)rootView.findViewById(R.id.troquelValor)).setText(m.getTroquel());
        this.cargarSolapaFormula(m.getFormula(), container, rootView);
    }

    private void cargarSolapaFormula(List<FormulaMedicamento> formulaLista, ViewGroup container, View rootView) {
        LayoutInflater mInflater = (LayoutInflater) this.getActivity()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        TableLayout tablaFormula = (TableLayout) rootView.findViewById(R.id.tableFormula);

        View formulaRow = mInflater.inflate(R.layout.formula, container, false);

        for(FormulaMedicamento formula : formulaLista){
            formulaRow = mInflater.inflate(R.layout.formula, container, false);
            ((TextView) formulaRow.findViewById(R.id.ifa)).setText(formula.getIfa());
            ((TextView) formulaRow.findViewById(R.id.cant)).setText(formula.getCantidad());
            ((TextView) formulaRow.findViewById(R.id.unidadDeMedida)).setText(formula.getUnidad());
            tablaFormula.addView(formulaRow);
        }


    }

}
