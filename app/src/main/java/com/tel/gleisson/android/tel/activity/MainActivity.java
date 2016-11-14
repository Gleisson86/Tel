package com.tel.gleisson.android.tel.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.fragment.Acesso_fragment;
import com.tel.gleisson.android.tel.fragment.Equipamento_fragment;
import com.tel.gleisson.android.tel.fragment.Infra_fragment;
import com.tel.gleisson.android.tel.util.ClasseUtil;
import com.tel.gleisson.android.tel.util.PrefManager;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity  {

    private PrefManager prefManager;
    private FirebaseAuth auth;
    private ClasseUtil classeUtil;
    private FirebaseUser user;
    FloatingActionButton fab;

    private Button sair;
    private Button deletarConta;

    //  SharedPreference regra para viu ou não viu a intro. Dentro da on create e também
    // no onResume.
    //------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        auth = FirebaseAuth.getInstance();
        prefManager = new PrefManager(this);
        classeUtil = new ClasseUtil(this);
        setContentView(R.layout.tela_inicial_activity);


        user = FirebaseAuth.getInstance().getCurrentUser();

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(MainActivity.this, CriarNovoCardSolucao_Activity.class);
                startActivity(intent);
            }
        });


        // Checking for first time launch - before calling setContentView()
        if (prefManager.isFirstTimeLaunch()) {
            launchIntroScreen();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new Equipamento_fragment(),"Equipamento");
        adapter.addFragment(new Acesso_fragment(),"Acesso");
        adapter.addFragment(new Infra_fragment(),"Infra");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void launchIntroScreen() {
        startActivity(new Intent(MainActivity.this, IntroActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search1:
                // User chose the "Settings" item, show the app settings UI...
              return true;

            case R.id.action_favorite:
                startActivity(new Intent(MainActivity.this, TelaProfileUsuarioActivity.class));
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        }
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
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

}
