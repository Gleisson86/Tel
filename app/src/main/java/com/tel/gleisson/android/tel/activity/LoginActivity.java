package com.tel.gleisson.android.tel.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.util.ClasseUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputSenha;
    private FirebaseAuth auth;
    private Button btnSignup, btnLogin, btnReset;
    private ClasseUtil classeUtil;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        classeUtil = new ClasseUtil(LoginActivity.this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // set the view now
        setContentView(R.layout.login_activity);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputSenha = (EditText) findViewById(R.id.senha);
       // progressDialog = (ProgressBar) findViewById(R.id.progressBarLogin);
        btnSignup = (Button) findViewById(R.id.cria_conta);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.botao_reset_senha_login);



        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });




        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CriarContaActivity.class));

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                final String password = inputSenha.getText().toString().trim();




                if (TextUtils.isEmpty(email)) {
                 classeUtil = new ClasseUtil(getApplicationContext());
                    classeUtil.chamaDialogo(getString(R.string.entreComEmailMensagem),getString(R.string.tentenovamente));
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    classeUtil.chamaDialogo(getString(R.string.entreComSenhaTitulo),getString(R.string.tentenovamente));
                    return;
                }

                if (password.length()<6) {

                    classeUtil.chamaDialogo(getString(R.string.senhaCurtaTitulo),getString(R.string.senhaCurtaMensagem));
                    //Toast.makeText(getApplicationContext(), getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);
                        classeUtil.chamaProgess(getString(R.string.processandoTitulo),getString(R.string.aguardeMensagem));


                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                       //         progressBar.setVisibility(View.GONE);
                                classeUtil.chamaProgresFim();
                                if (task.isSuccessful())
                                {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    classeUtil.chamaDialogo(getString(R.string.falhaAutenticacaoTitulo),getString(R.string.falhaAutenticacaoMensagem));
                                  //  Toast.makeText(LoginActivity.this, getString(R.string.falhaAutenticacaoTitulo), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

   @Override
    protected void onResume() {
        super.onResume();

        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}