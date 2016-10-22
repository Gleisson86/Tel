package com.tel.gleisson.android.tel;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by Gleisson e Rosy on 21/10/2016.
 */

public class ClasseUtil {

    AlertDialog.Builder builder;
    private ProgressDialog progressDialog;


    public ClasseUtil(Context context) {

        builder = new AlertDialog.Builder(context);
        progressDialog = new ProgressDialog(context);
    }


    public void chamaProgresFim(){

        progressDialog.dismiss();
    }


    public void chamaProgess (String titulo, String mensagem){

        progressDialog.setTitle(titulo);
        progressDialog.setMessage(mensagem);
        progressDialog.show();
    }


    public void chamaDialogo (String titulo , String mensagem){

        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        builder.show();

    }

}