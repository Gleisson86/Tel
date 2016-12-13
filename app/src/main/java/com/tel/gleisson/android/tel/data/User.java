package com.tel.gleisson.android.tel.data;

/**
 * Created by Gleisson on 24/10/2016.
 */
public class User {

    String nome;
    String email;
    String idUser;

    public User(){

    }


    public User(String nome, String email, String idUser) {
        this.nome = nome;
        this.email = email;
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
