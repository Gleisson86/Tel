package com.tel.gleisson.android.tel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity  {

    private PrefManager prefManager;
    private FirebaseAuth auth;

    //  SharedPreference regra para viu ou não viu a intro. Dentro da on create e também
    // no onResume.
    //------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        setContentView(R.layout.telainicial_main);

        // Checking for first time launch - before calling setContentView()


        if (prefManager.isFirstTimeLaunch()) {
            launchIntroScreen();
        }

        auth = FirebaseAuth.getInstance();
    }

    private void launchIntroScreen() {
        startActivity(new Intent(MainActivity.this, IntroActivity.class));
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));


        }
    }
    //---- Fim SharedPreference
    //----------------------------------------------------




}
