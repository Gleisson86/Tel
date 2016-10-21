package com.tel.gleisson.android.tel;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputNome, inputEmail, inputSenha;
    private Button  btnRegistrar;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

    //    btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnRegistrar = (Button) findViewById(R.id.registrar);
        inputNome = (EditText) findViewById(R.id.input_nome);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputSenha = (EditText) findViewById(R.id.input_senha);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String senha = inputSenha.getText().toString().trim();
                String nome  = inputNome.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.entreComEmail), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(senha)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.entreComUmaSenha), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(nome)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.entreComUmNome), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (senha.length() < 6) {
                    Toast.makeText(getApplicationContext(), getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user

                auth.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();

                                } else {
                                    Toast.makeText(SignupActivity.this, "Falha ao criar a conta ",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

    }
}
