package com.tel.gleisson.android.tel.activitySolucoesUsuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tel.gleisson.android.tel.R;

public class SolucoesUsuarioActivity extends AppCompatActivity {

    private Button equipo;
    private Button acesso;
    private Button infra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solucoes_editar);

        equipo = (Button) findViewById(R.id.equipoSoluEdit);
        acesso = (Button) findViewById(R.id.acessoSoluEdit);
        infra = (Button) findViewById(R.id.infraSoluEdit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSolucaoUsuario);
        toolbar.setTitle("Soluções/Editar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        equipo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SolucoesUsuarioActivity.this, EquipamentoSolucoesActivity.class));

            }
        });

        acesso.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SolucoesUsuarioActivity.this, AcessoSolucoesActivity.class));
            }
        });


        infra.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SolucoesUsuarioActivity.this, InfraestruturaSolucoesActivity.class));
            }
        });




    }
}
