package com.tel.gleisson.android.tel.data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.tel.gleisson.android.tel.activity.DetalheCardActivity;

/**
 * Created by Gleisson on 21/11/2016.
 */

public class AdapterSolucaoEquipamento extends FirebaseRecyclerAdapter<SolucaoObjeto, ViewHolderRecuperaSolucao> {
    Context context;
    SolucaoObjeto solucaoObjeto;

    public AdapterSolucaoEquipamento(Context context, Class<SolucaoObjeto> modelClass, int modelLayout, Class<ViewHolderRecuperaSolucao> viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);

        this.context = context;
    }

    @Override
    protected void populateViewHolder(ViewHolderRecuperaSolucao viewHolder, SolucaoObjeto model, final int position) {

        viewHolder.titulo.setText(model.getTitulo());
        String text = model.getTitulo();
        viewHolder.nome.setText(model.getNome());
      //  PicassoClient.downloaImage(context, model.getFoto(), viewHolder.imagemSolucao);
        PicassoClient.downloaImage(context, model.getFoto(), viewHolder.imagemSolucao);
       // PicassoClient.imageDownload(context,model.getFoto());
        ArraySolucoes.equipamento.add(position,model);
     //   ArraySolucoes.imagensSolucoes.add(position,model.getFoto());
    }


    @Override
    public void onBindViewHolder(ViewHolderRecuperaSolucao viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                solucaoObjeto = new SolucaoObjeto();
                solucaoObjeto = ArraySolucoes.equipamento.get(position);
               // String imagensSolucao = solucaoObjeto.getFoto();
                Bundle bundle = new Bundle();
                bundle.putString("url",solucaoObjeto.getFoto());
                bundle.putInt("posicao", position);
                bundle.putString("nome", solucaoObjeto.getNome());
                bundle.putString("titulo", solucaoObjeto.getTitulo());
                bundle.putString("palavraChave", solucaoObjeto.getPalavraChave());
                bundle.putString("descricao", solucaoObjeto.getDescricao());
                Intent intent = new Intent(context.getApplicationContext(), DetalheCardActivity.class);
                intent.putExtra("EXTRAS_SOLUCAO", bundle);
                context.startActivity(intent);
            }
        });


    }

/*
    public void setFilter(List<CountryModel> countryModels){
        mCountryModel = new ArrayList<>();
        mCountryModel.addAll(countryModels);
        notifyDataSetChanged();
    }
*/

}

