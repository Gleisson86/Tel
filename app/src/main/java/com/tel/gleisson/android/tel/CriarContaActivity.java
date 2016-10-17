package com.tel.gleisson.android.tel;


import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Gleisson e Rosy on 14/10/2016.
 */

public class CriarContaActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;



    // Método onCreate
    //-----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_conta);

      //  chamaIntro();

        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById (R.id.editTextEmailCriarConta);
        editTextPassword = (EditText) findViewById (R.id.editTextSenhaCriarConta);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
    }


    //--Chama Introdução
    //-------------------------------------------------------------------------
    private void chamaIntro(){
        Intent intent = new Intent(this, IntroActivity.class);
        this.startActivity(intent);
    }



    // Metodo para criar uma conta baseada em Email e Senha
    //------------------------------------------------------

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //checking if success

                if(task.isSuccessful()){
                    //display some message here
                    Toast.makeText(CriarContaActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                    startActivity( new Intent(CriarContaActivity.this, IntroActivity.class));
                   finish();
                }else{
                    //display some message here
                    Toast.makeText(CriarContaActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                    finish();
                }
                progressDialog.dismiss();
            }
        });

    }



    // Método que chama o Criar Nova Conta assim que o usuário
    //clica no botão Criar
    //----------------------------------------------------------------

    @Override
    public void onClick(View view) {
        //calling register method on click
     registerUser();
    }
}