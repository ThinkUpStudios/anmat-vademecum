package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by FaQ on 26/04/2015.
 */
public abstract class NoMenuActivity extends Activity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }
}
