package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.bo.FormulaMedicamento;
import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;
import com.thinkupstudios.anmat.vademecum.listeners.DarkenerButtonTouchListener;

import java.util.List;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;

/**
 * A fragment representing a single DetalleMedicamento detail screen.
 * This fragment is either contained in a {@link DetalleMedicamentoListActivity}
 * in two-pane mode (on tablets) or a {@link DetalleMedicamentoDetailActivity}
 * on handsets.
 */
public class DetalleMedicamentoDetailFragment extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private MedicamentoBO medicamento;
    private Button recomendados;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DetalleMedicamentoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(MedicamentoBO.MEDICAMENTOBO)) {
            this.medicamento = (MedicamentoBO) getArguments().getSerializable(MedicamentoBO.MEDICAMENTOBO);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detallemedicamento_detail, container, false);

        this.setValores(rootView, this.medicamento, container);
        Resources res = getResources();

        TabHost tabs = (TabHost) rootView.findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.detalle);
        //spec.setIndicator("Detalle");
        spec.setIndicator("", res.getDrawable(R.drawable.detalle));
        tabs.addTab(spec);
        tabs.setHorizontalScrollBarEnabled(true);

        spec = tabs.newTabSpec("");
        spec.setContent(R.id.formula);
        //spec.setIndicator("Formula");
        spec.setIndicator("", res.getDrawable(R.drawable.formula));

        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);
            }
        });


        this.recomendados = (Button) rootView.findViewById(R.id.btn_recomentados);
        recomendados.setOnTouchListener(new DarkenerButtonTouchListener());
        recomendados.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DetalleMedicamentoDetailFragment.this.getActivity(),
                        DetalleMedicamentoListActivity.class);
                i.putExtra("COMERCIAL_RECOMENDADO", medicamento.getNombreComercial());
                FormularioBusqueda f = new FormularioBusqueda();
                f.setNombreGenerico(medicamento.getNombreGenerico());
                i.putExtra(FormularioBusqueda.FORMULARIO_MANUAL, f);
                startActivity(i);
                DetalleMedicamentoDetailFragment.this.getActivity().
                        overridePendingTransition(fade_in, fade_out);
            }
        });

        return rootView;
    }


    public void setValores(View rootView, MedicamentoBO m, ViewGroup container) {


        ((TextView) rootView.findViewById(R.id.condicion_de_expendioValor)).setText(m.getCondicionExpendio());
        ((TextView) rootView.findViewById(R.id.condicion_de_trazabilidadValor)).setText(m.getCondicionTrazabilidad());
        ((TextView) rootView.findViewById(R.id.forma_farmaceuticaValor)).setText(m.getForma());
        ((TextView) rootView.findViewById(R.id.gtinValor)).setText(m.getGtin());
        ((TextView) rootView.findViewById(R.id.laboratorioValor)).setText(m.getLaboratorio());
        ((TextView) rootView.findViewById(R.id.nombre_comercialValor)).setText(m.getNombreComercial());
        ((TextView) rootView.findViewById(R.id.nombre_generico_Valor)).setText(m.getNombreGenerico());
        ((TextView) rootView.findViewById(R.id.pais_industriaValor)).setText(m.getPaisIndustria());
        ((TextView) rootView.findViewById(R.id.nroCertificadoValor)).setText(m.getNumeroCertificado());
        ((TextView) rootView.findViewById(R.id.presentacionValor)).setText(m.getPresentacion());
        ((TextView) rootView.findViewById(R.id.precioValor)).setText(m.getPrecio());
        ((TextView) rootView.findViewById(R.id.troquelValor)).setText(m.getTroquel());
        this.cargarSolapaFormula(m.getFormula(), container, rootView);
    }

    private void cargarSolapaFormula(List<FormulaMedicamento> formulaLista, ViewGroup container, View rootView) {
        LayoutInflater mInflater = (LayoutInflater) this.getActivity()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        LinearLayout tablaFormula = (LinearLayout) rootView.findViewById(R.id.formula_content);

        View formulaRow = mInflater.inflate(R.layout.formula, container, false);
        int position = 0;
        for (FormulaMedicamento formula : formulaLista) {
            position++;
            formulaRow = mInflater.inflate(R.layout.formula, container, false);
            ((TextView) formulaRow.findViewById(R.id.ifa)).setText(formula.getIfa());
            ((TextView) formulaRow.findViewById(R.id.cant)).setText(formula.getCantidad());
            ((TextView) formulaRow.findViewById(R.id.unidadDeMedida)).setText(formula.getUnidad());
            formulaRow.setOnClickListener(this);
            format(position, formulaRow);
            tablaFormula.addView(formulaRow);

        }


    }

    private void format(int position, View formulaRow) {
        int color = 0;
        if (position % 2 == 1) {
            formulaRow.setBackgroundResource(R.drawable.list_item_background);


        } else {
            formulaRow.setBackgroundResource(R.drawable.list_item_background2);

        }
        color = formulaRow.getResources().getColor(R.color.anmat_azul);

        ((TextView) formulaRow.findViewById(R.id.ifa)).setTextColor(color);
        ((TextView) formulaRow.findViewById(R.id.cant)).setTextColor(color);
        ((TextView) formulaRow.findViewById(R.id.unidadDeMedida)).setTextColor(color);
    }

    @Override
    public void onClick(View v) {
        String principioActivo = ((TextView) v.findViewById(R.id.ifa)).getText().toString();
        Intent i = new Intent(this.getActivity(),
                DetallePrincipioActivoActivity.class);
        i.putExtra(FormularioBusqueda.PRINCIPIO_ACTIVO, principioActivo);
        startActivity(i);
        this.getActivity()
                .overridePendingTransition(fade_in, fade_out);
    }
}
