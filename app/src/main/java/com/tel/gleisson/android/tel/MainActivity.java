package com.tel.gleisson.android.tel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    private PrefManager prefManager;
    private FirebaseAuth auth;
    private ClasseUtil classeUtil;
    private FirebaseUser user;

    private Button sair;
    private Button deletarConta;

    //  SharedPreference regra para viu ou não viu a intro. Dentro da on create e também
    // no onResume.
    //------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        prefManager = new PrefManager(this);
        classeUtil = new ClasseUtil(this);
        setContentView(R.layout.telainicial_main);

      //  sair = (Button) findViewById(R.id.sair);
     //   deletarConta = (Button) findViewById(R.id.deletarConta);
        user = FirebaseAuth.getInstance().getCurrentUser();


        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        // Checking for first time launch - before calling setContentView()
        if (prefManager.isFirstTimeLaunch()) {
            launchIntroScreen();
        }

        /*
        sair.setOnClickListener(new View.OnClickListener() {

            @Override
                public void onClick(View v) {

           //     classeUtil.chamaDialogo(null,getString(R.string.voceRealmenteSair));
                    auth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        deletarConta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                   //  classeUtil.chamaDialogo(null,getString(R.string.deletarContaMensagem));
                     classeUtil.chamaProgess(getString(R.string.aguardeTitulo),getString(R.string.processandoTitulo));
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                 classeUtil.chamaProgresFim();
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this,getString(R.string.contaExcluidaMensagem),Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(MainActivity.this, getString(R.string.falhaAoDeletarConta), Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });      */





    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return  mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
      //  adapter.addFragment(new ListContentFragment(), "List");
      //  adapter.addFragment(new TileContentFragment(), "Tile");
        adapter.addFragment(new CardFraguimentoEquipamento(), "Equipamento");
        viewPager.setAdapter(adapter);
    }


    private void launchIntroScreen() {
        startActivity(new Intent(MainActivity.this, IntroActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));


        }
    }
    //---- Fim SharedPreference
    //----------------------------------------------------

}
