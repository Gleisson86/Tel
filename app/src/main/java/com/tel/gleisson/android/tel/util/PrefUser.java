package com.tel.gleisson.android.tel.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Gleisson e Rosy on 26/11/2016.
 */

public class PrefUser {

    Context context;
    SharedPreferences user;
    SharedPreferences.Editor editorUser;

    // shared pref mode
    int PRIVATE_MODE = 1;

    // Shared preferences file name
    private static final String PREF_NAME = "salvando usuario";
    private static final String USUARIO_EXISTENTE = "usuari_existente";

    public PrefUser(Context context) {
        this.context = context;
        this.user =context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editorUser = user.edit();
    }

    public void salvaUsuario(String nome){
        editorUser.putString(USUARIO_EXISTENTE,nome);
        editorUser.commit();
    }

    public String getUsuarioLogado(){
        return user.getString(USUARIO_EXISTENTE,"nome");
    }
}
