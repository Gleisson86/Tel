package com.tel.gleisson.android.tel.activity;


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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.data.User;
import com.tel.gleisson.android.tel.util.ClasseUtil;


/**
 * Created by Gleisson e Rosy on 14/10/2016.
 */




public class CriarContaActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button botaoCriarConta;
    private ProgressDialog progressDialog;
    private ClasseUtil classeUtil;


    //defining firebaseauth object
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;


    // Método onCreate
    //-----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_conta);

        classeUtil = new ClasseUtil(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextNome = (EditText) findViewById(R.id.name);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.senha);
        botaoCriarConta = (Button) findViewById(R.id.botao_criar_conta);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        botaoCriarConta.setOnClickListener(this);
    }


    // Metodo para criar uma conta baseada em Email e Senha
    //------------------------------------------------------

    private void registerUser() {



        //getting email and password from edit texts
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (TextUtils.isEmpty(nome)) {
            classeUtil.chamaDialogo(getString(R.string.nomeCompletoVazio), getString(R.string.tentenovamente));
            return;
        }

        if (TextUtils.isEmpty(email)) {
            classeUtil.chamaDialogo(getString(R.string.entreComEmailMensagem), getString(R.string.tentenovamente));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            classeUtil.chamaDialogo(getString(R.string.entreComSenhaTitulo), getString(R.string.tentenovamente));
            return;
        }

        if (password.length()<6) {

            classeUtil.chamaDialogo(getString(R.string.senhaCurtaTitulo),getString(R.string.senhaCurtaMensagem));
            //Toast.makeText(getApplicationContext(), getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();
            return;
        }


        //if the email and password are not empty
        //displaying a progress dialog
        classeUtil.chamaProgess(getString(R.string.processandoTitulo), getString(R.string.aguardeMensagem));

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //checking if success
                classeUtil.chamaProgresFim();

                if (task.isSuccessful()) {
                    //display some message here
                    criaProfile(task.getResult().getUser());
                    Toast.makeText(CriarContaActivity.this,getString(R.string.conta_criada_sucesso),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CriarContaActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //display some message here

                    classeUtil.chamaDialogo(getString(R.string.falhaAutenticacaoTitulo), getString(R.string.falhaAutenticacaoMensagem));
                    //    Toast.makeText(CriarContaActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    //CRIA NOVA CONTA DE USUÁRIO
    //-------------------------------------------------------------------------------------------------------

    private void criaProfile(FirebaseUser user) {

        String username = editTextNome.getText().toString().trim();
        writeNewUser(username,user.getEmail() ,user.getUid());
    }

    // [START basic_write]
    private void writeNewUser(String name, String email,String userId) {
       // classeUtil = new ClasseUtil(this);
        User user = new User(name, email , userId);
        mDatabase.child("users").child(userId).setValue(user);

    }
    // [END basic_write]

    //-------------------------------------------------------------------------------------------------------

    @Override
    public void onClick(View view) {
        //calling register method on click
        registerUser();
    }
}