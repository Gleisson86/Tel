package com.tel.gleisson.android.tel.data;

/**
 * Created by Gleisson on 24/10/2016.
 */
public class User {

    String nome;
    String email;
    String idUser;

    public User(String username, String email, String idUser) {

        this.nome = username;
        this.idUser = idUser;
        this.email = email;
    }


    public String getIdUser() {
        return idUser;
    }

    public String getName() {
        return nome;
    }

    public String get_email() {
        return email;
    }
}
