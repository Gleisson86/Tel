package com.tel.gleisson.android.tel.data;

import android.content.Context;

/**
 * Created by Gleisson 05/11/2016.
 */


public interface Solucao {

    void upLoadSolucao(String id, String nome, String titulo, String palavraChave, String tipoSolucao, String descricao, String foto);

    void updateSolucao(String id, String nome, String titulo, String palavraChave, String tipoSolucao, String descricao, String uri);

    void upLoadImagem (byte[] foto, Context context);

    Boolean deleteSolucao();

    Boolean changeSolucao();


}
