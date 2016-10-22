package com.tel.gleisson.android.tel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button botaoReset;
    private FirebaseAuth auth;
    private int indicador;

    //  private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetsenha_activity);


     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //   getSupportActionBar().setHomeButtonEnabled(true);



        indicador = 0;
        auth = FirebaseAuth.getInstance();
        inputEmail = (EditText) findViewById(R.id.email);
        botaoReset = (Button) findViewById(R.id.botao_reset_senha_reset);


        botaoReset.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                final ClasseUtil classeUtil = new ClasseUtil(ResetPasswordActivity.this);


                if (TextUtils.isEmpty(email)) {
                    classeUtil.chamaDialogo(getString(R.string.atencao), getString(R.string.entreComEmailMensagem));
                    // Toast.makeText(getApplication(), getString(R.string.email_registro_de_conta), Toast.LENGTH_SHORT).show();
                    return;
                }

                classeUtil.chamaProgess(getString(R.string.aguardeTitulo), getString(R.string.processandoTitulo));
                //    progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                classeUtil.chamaProgresFim();
                                if (!task.isSuccessful()) {
                                    classeUtil.chamaDialogo(getString(R.string.falhaEnviarEmailResetTitulo), getString(R.string.falhaEnviarEmailResetMensagem));
                                    //     Toast.makeText(ResetPasswordActivity.this, getString(R.string.emailEnviado_resetSenha), Toast.LENGTH_SHORT).show();
                                } else {
                                    // classeUtil.chamaDialogo(getString(R.string.okTitulo), getString(R.string.emailEnviado_resetSenha));
                                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.emailEnviado_resetSenha), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }
        });
    }

}