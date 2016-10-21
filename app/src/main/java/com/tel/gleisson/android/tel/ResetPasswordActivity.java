package com.tel.gleisson.android.tel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button botaoReset;
    private FirebaseAuth auth;
   private ProgressBar progressBar;
    private int indicador;
  //  private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetsenha_activity);


        indicador = 0;



        auth = FirebaseAuth.getInstance();


        inputEmail = (EditText) findViewById(R.id.email);
        botaoReset = (Button) findViewById(R.id.botao_reset_senha_reset);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        botaoReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();



                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), getString(R.string.email_registro_de_conta), Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.EmailEnviado_resetSenha), Toast.LENGTH_SHORT).show();
                                    indicador = 1;
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.enviar_email_de_resetSenha), Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);

                                if (indicador != 0) {
                                    finish();
                                }
                            }
                        });


            }
        });


    }

}