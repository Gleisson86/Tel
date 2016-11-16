package com.tel.gleisson.android.tel.data;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Gleisson 15/11/2016.
 */

public class AdapterRecuperaSolucao extends FirebaseRecyclerAdapter <SolucaoObjeto, ViewHolderRecuperaSolucao>  {


    public AdapterRecuperaSolucao(Class<SolucaoObjeto> modelClass, int modelLayout, Class<ViewHolderRecuperaSolucao> viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(ViewHolderRecuperaSolucao viewHolder, SolucaoObjeto model, int position) {

            viewHolder.titulo.setText(model.getTitulo());
            String text =   model.getTitulo();
            viewHolder.nome.setText(model.getNome());

    }
}
