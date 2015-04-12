package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by FaQ on 11/04/2015.
 * Activity creada solo para splashScreen
 */
public class Splash extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,  MainMenuActivity.class));
    }



}
