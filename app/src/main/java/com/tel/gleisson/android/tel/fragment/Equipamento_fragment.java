package com.tel.gleisson.android.tel.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.data.AdapterSolucaoEquipamento;
import com.tel.gleisson.android.tel.data.SolucaoObjeto;
import com.tel.gleisson.android.tel.data.ViewHolderRecuperaSolucao;

/**
 * Created by Gleisson e Rosy on 24/10/2016.
 */

public class Equipamento_fragment extends Fragment/* implements SearchView.OnQueryTextListener*/{


    private DatabaseReference mDatareferencia;
    private AdapterSolucaoEquipamento adapter;
    private DatabaseReference RefSolucao;
    private static Bundle mBundleRecyclerViewState;
    private RecyclerView recyclerView;
    private final String KEY_RECYCLER_STATE = "recycler_state";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDatareferencia = FirebaseDatabase.getInstance().getReference().child("Soluções").child("Equipamento");
         recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
     //   RefSolucao = mDatareferencia.child ("https://apptel-84297.firebaseio.com/").child("Soluções").child("Equipamento");
      //  ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());

        setHasOptionsMenu(true);

        adapter = new AdapterSolucaoEquipamento(getContext(),SolucaoObjeto.class,R.layout.solucoes_card, ViewHolderRecuperaSolucao.class, mDatareferencia);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }



/*

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsedadapter.setFilter(mCountryModel);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        final List<CountryModel> filteredModelList = filter(mCountryModel, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<CountryModel> filter(List<CountryModel> models, String query) {
        query = query.toLowerCase();final List<CountryModel> filteredModelList = new ArrayList<>();
        for (CountryModel model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }



*/













    @Override
    public void onPause()
    {
        super.onPause();

      // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable recyclerState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerState);
        }
    }
}