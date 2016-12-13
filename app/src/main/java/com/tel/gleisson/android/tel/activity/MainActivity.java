package com.tel.gleisson.android.tel.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView.OnQueryTextListener;

import com.crashlytics.android.Crashlytics;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.activityContaUsuario.ContaActivity;
import com.tel.gleisson.android.tel.activitySolucoesUsuario.SolucoesUsuarioActivity;
import com.tel.gleisson.android.tel.data.ImplementaSolucao;
import com.tel.gleisson.android.tel.fragment.Acesso_fragment;
import com.tel.gleisson.android.tel.fragment.Equipamento_fragment;
import com.tel.gleisson.android.tel.fragment.Infra_fragment;
import com.tel.gleisson.android.tel.util.ClasseUtil;
import com.tel.gleisson.android.tel.util.PrefManager;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

import static com.tel.gleisson.android.tel.R.style.AlertaPadraoTel;


public class MainActivity extends AppCompatActivity implements OnQueryTextListener {

    private PrefManager prefManager;
    private FirebaseAuth auth;
    private ClasseUtil classeUtil;
    private FirebaseUser user;
    FloatingActionButton fab;
    private Button sair;
    private Button deletarConta;
    private Firebase firebase;

    private static Bundle mBundleRecyclerViewState = null;
    private RecyclerView recyclerView;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private AlertDialog.Builder builder;
    private ImplementaSolucao implementaSolucao;

    //  SharedPreference regra para viu ou não viu a intro. Dentro da on create e também
    // no onResume.
    //------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    //    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //   getActionBar().hide();




        Fabric.with(this, new Crashlytics());
        builder = new AlertDialog.Builder(this, AlertaPadraoTel);

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

    //    final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
    //    SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
    //    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

    /*    final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView =  MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
*/
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

            case R.id.menuConta:
                startActivity(new Intent(MainActivity.this, ContaActivity.class));
                return true;

            case R.id.menuSair:

             //  new ImplementaSolucao(this).setUsuario(null);
             //   classeUtil.chamaPopupSairFinalizaActivity(this, LoginActivity.class,auth);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                             case DialogInterface.BUTTON_POSITIVE:

                                 auth.signOut();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                               startActivity(intent);
                               finish();

                                break;

                             case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                builder.setMessage("Realmente você deseja sair?");
                builder.setPositiveButton("Sim", dialogClickListener);
                builder.setNegativeButton("Não", dialogClickListener);
                builder.show();

               return true;
            case R.id.menuSolucaoEditar:
                startActivity(new Intent(MainActivity.this, SolucoesUsuarioActivity.class));


                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
       // inicio();

        if (mBundleRecyclerViewState != null) {
            Parcelable recyclerState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerState);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();

  /*      // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
*/
    }

    @Override
    public boolean onQueryTextSubmit(String s) {



        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }


    static class Adapter extends FragmentPagerAdapter{

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
