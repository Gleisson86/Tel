package com.tel.gleisson.android.tel.data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.activity.DetalheCardActivity;

/**
 * Created by Gleisson 15/11/2016.
 */

public class ViewHolderRecuperaSolucao extends ViewHolder {

  //  public String id;
    public TextView nome;
    public TextView titulo;
  //  public TextView palavraChave;
   // public TextView tipoSolucao;
    public TextView descricao;
    //public String uri;


    public ViewHolderRecuperaSolucao(View itemView) {
        super(itemView);

        titulo = (TextView) itemView.findViewById(R.id.titulo_card);
        nome = (TextView) itemView.findViewById(R.id.titular_da_solucao);
        //descricao = (TextView) itemView.findViewById(R.id.texto_card);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetalheCardActivity.class);
                intent.putExtra(DetalheCardActivity.EXTRA_POSITION, getAdapterPosition());
                context.startActivity(intent);
            }
        });

    }
}