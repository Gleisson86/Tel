package com.tel.gleisson.android.tel.data;

import android.net.Uri;

/**
 * Created by Gleisson 05/11/2016.
 */


public interface Solucao {

    void upLoadSolucao(String id, String nome, String titulo, String palavraChave, String tipoSolucao, String descricao, Uri uri);

    void updateSolucao(String id, String nome, String titulo, String palavraChave, String tipoSolucao, String descricao, String uri);

    void upLoadImagem (Uri uri);

    Boolean deleteSolucao();

    Boolean changeSolucao();


}
