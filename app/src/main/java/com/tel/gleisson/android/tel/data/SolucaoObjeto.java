package com.tel.gleisson.android.tel.data;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gleisson e Rosy on 07/11/2016.
 */

public class SolucaoObjeto {

    public String id;
    public String nome;
    public String titulo;
    public String palavraChave;
    public String tipoSolucao;
    public String descricao;
    String uri;
    public Map<String, Boolean> stars = new HashMap<>();


    public SolucaoObjeto (){

    }

    public SolucaoObjeto(String id, String nome, String titulo, String palavraChave, String tipoSolucao, String descricao, String uri) {
        this.id = id;
        this.nome = nome;
        this.titulo = titulo;
        this.palavraChave = palavraChave;
        this.tipoSolucao = tipoSolucao;
        this.descricao = descricao;
        this.uri = uri;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("nome", nome);
        result.put("titulo", titulo);
        result.put("palavraChave", palavraChave);
        result.put("tipoSolucao", tipoSolucao);
        result.put("descricao", descricao);
        result.put("foto",uri);

        return result;
    }


}
