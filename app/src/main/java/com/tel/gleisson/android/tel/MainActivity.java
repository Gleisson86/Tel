package com.tel.gleisson.android.tel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private PrefManager prefManager;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.telainicial_main);


                prefManager = new PrefManager(this);
                if (prefManager.isFirstTimeLaunch()) {
                    launchHomeScreen();
                    finish();
                }


            }


    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(this, IntroActivity.class));
        finish();
    }
}
