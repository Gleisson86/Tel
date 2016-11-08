package com.tel.gleisson.android.tel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.util.ClasseUtil;

public class TelaProfileUsuarioActivity extends AppCompatActivity {

    private Button sair;
    private Button editarConta;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_profile_usuario_activity);


        editarConta = (Button) findViewById(R.id.editarConta);
        sair = (Button) findViewById(R.id.sair);
        mAuth = FirebaseAuth.getInstance();
        final ClasseUtil classeUtil = new ClasseUtil(TelaProfileUsuarioActivity.this);

        sair.setOnClickListener(new View.OnClickListener() {
            FirebaseUser user = mAuth.getInstance().getCurrentUser();

            @Override
            public void onClick(View view) {
                        if (user != null) {
                            mAuth.signOut();
                            startActivity(new Intent(TelaProfileUsuarioActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            classeUtil.chamaDialogo(getString(R.string.atencao),getString(R.string.usuarioNull));
                        }
                };
        });

       editarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(TelaProfileUsuarioActivity.this, EditarContaActivity.class));
                    finish();
            };
        });
    }
}
