package com.tel.gleisson.android.tel.data;

/**
 * Created by Gleisson e Rosy on 07/11/2016.
 */

public class SolucaoObjeto {

    private String id;
    private String titulo;
    private String palavraChave;
    private String tipoSolucao;
    private String descricao;
    private String[] uri;

    public SolucaoObjeto(String id, String titulo, String palavraChave, String tipoSolucao, String descricao, String[] uri) {
        this.id = id;
        this.titulo = titulo;
        this.palavraChave = palavraChave;
        this.tipoSolucao = tipoSolucao;
        this.descricao = descricao;
        this.uri = uri;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoSolucao() {
        return tipoSolucao;
    }

    public void setTipoSolucao(String tipoSolucao) {
        this.tipoSolucao = tipoSolucao;
    }

    public String getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(String palavraChave) {
        this.palavraChave = palavraChave;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
