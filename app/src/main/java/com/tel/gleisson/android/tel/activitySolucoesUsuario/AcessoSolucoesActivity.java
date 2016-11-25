package com.tel.gleisson.android.tel.activitySolucoesUsuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tel.gleisson.android.tel.R;

public class AcessoSolucoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_solucoes);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAcessoSoluEdit);
        toolbar.setTitle("Acesso");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


      }
}
