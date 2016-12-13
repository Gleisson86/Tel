package com.tel.gleisson.android.tel.data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.activity.DetalheCardActivity;

import java.util.ArrayList;

/**
 * Created by Gleisson e Rosy on 10/12/2016.
 */

public class AdpterHolderQuinta extends RecyclerView.Adapter<ViewHolderRecuperaSolucao>{
    Context context;
    private ArrayList<SolucaoObjeto> solutions;


    public AdpterHolderQuinta(Context context, ArrayList<SolucaoObjeto> solutions){
        this.context = context;
        this.solutions = solutions;
    }

    @Override
    public ViewHolderRecuperaSolucao onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.solucoes_card, parent, false);
        ViewHolderRecuperaSolucao viewHolder = new ViewHolderRecuperaSolucao(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderRecuperaSolucao viewHolder, final int position) {

        final SolucaoObjeto solucaoObjeto = solutions.get(position);

        //viewHolder.descricao.setText(solucaoObjeto.getDescricao());
        //fazer para os outros componentes da cÃ©lula. Ã‰ a mesma coisa que vc antes fazia no populateViewHolder

        viewHolder.titulo.setText(solucaoObjeto.getTitulo());
        viewHolder.data.setText(solucaoObjeto.getData());
        String text = solucaoObjeto.getTitulo();
        viewHolder.nome.setText(solucaoObjeto.getNome());
        //  PicassoClient.downloaImage(context, model.getFoto(), viewHolder.imagemSolucao);
        PicassoClient.downloaImage(context, solucaoObjeto.getFoto(), viewHolder.imagemSolucao);
        // PicassoClient.imageDownload(context,model.getFoto());
        ArraySolucoes.equipamento.add(position,solucaoObjeto);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String imagensSolucao = solucaoObjeto.getFoto();
                Bundle bundle = new Bundle();
                bundle.putString("url",imagensSolucao);
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


    @Override
    public int getItemCount() {
        return solutions.size();
    }


}



