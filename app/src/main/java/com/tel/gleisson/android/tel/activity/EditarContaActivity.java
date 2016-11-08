package com.tel.gleisson.android.tel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.tel.gleisson.android.tel.R;

public class EditarContaActivity extends AppCompatActivity {

      private ImageButton botao_voltar;
      private ImageButton botao_confirmar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_conta_usuario_activity);



        botao_voltar = (ImageButton) findViewById(R.id.botao_voltar);
        botao_confirmar = (ImageButton) findViewById(R.id.botao_confirmar);


        botao_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();
            }
        });
    }
}
