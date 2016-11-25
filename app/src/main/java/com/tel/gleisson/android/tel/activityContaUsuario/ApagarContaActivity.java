package com.tel.gleisson.android.tel.activityContaUsuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tel.gleisson.android.tel.R;

public class ApagarContaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apagar_conta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarApagContContUsu);
        toolbar.setTitle("Apagar Conta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
