package com.tel.gleisson.android.tel.activityContaUsuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tel.gleisson.android.tel.R;

public class ContaActivity extends AppCompatActivity {

    private Button editConta;
    private Button apaConta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);


        editConta = (Button) findViewById(R.id.editContUsu);
        apaConta = (Button) findViewById(R.id.apagContUsu);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarContaUsuario);
        toolbar.setTitle("Conta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editConta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ContaActivity.this, EditarContaActivity.class));
            }
        });

        apaConta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContaActivity.this, ApagarContaActivity.class));
            }
        });

    }
}
