package com.tel.gleisson.android.tel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity  {

    private FirebaseAuth firebaseAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.telainicial_main);

                firebaseAuth = FirebaseAuth.getInstance();



                if (user != null) {
                    // User is signed in

                } else {

                Intent intent = new Intent(this, CriarContaActivity.class);

                  //  Bundle conecxaoFirebase = firebaseAuth
                  //  intent.putExtra("conexao" , firebaseAuth);

                this.startActivity(intent);

                }


            }

}
