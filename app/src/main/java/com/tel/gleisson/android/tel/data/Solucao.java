package com.tel.gleisson.android.tel.data;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Gleisson 05/11/2016.
 */


public interface Solucao {

    void upLoadSolucao(String id,String data,String nome, String titulo, String palavraChave, String tipoSolucao, String descricao, String foto);

    String upImagem(byte[] data, Context context);

    Boolean deleteSolucao();

    Boolean changeSolucao();

    void getUsuarioFirebase(FirebaseUser user);


}
