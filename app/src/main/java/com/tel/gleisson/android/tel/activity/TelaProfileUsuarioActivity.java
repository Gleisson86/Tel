package com.tel.gleisson.android.tel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.util.ClasseUtil;

public class TelaProfileUsuarioActivity extends AppCompatActivity {

    private Button sair;
    private ImageView fotoProfile;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_profile_usuario_activity);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProfileUsuario);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_usuario, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_conta_menu_profile:
                startActivity(new Intent(TelaProfileUsuarioActivity.this, EditarContaActivity.class));
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


}
