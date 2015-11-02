package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;

/**
 * Created by dcamarro on 30/10/2015.
 */
public class EmbarazoActivity extends Activity {

    public static final String DETAIL_CONTENT = "DETAIL_CONTENT";
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.medicamentos_embarazos);

        WebView myWebView = (WebView) this.findViewById(R.id.webView);

        restoreContent(savedInstanceState);

        WebSettings settings = myWebView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        myWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);

    }

    private void restoreContent(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(DETAIL_CONTENT)) {
            content = savedInstanceState.getString(DETAIL_CONTENT);
        } else if (this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey(DETAIL_CONTENT)) {
            content = this.getIntent().getExtras().getString(DETAIL_CONTENT);

        } else {
            content = "Sin contenido";
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DETAIL_CONTENT, content);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MiAplicacion) this.getApplication()).updateCache(new DatabaseHelper(this), false);
    }

}
