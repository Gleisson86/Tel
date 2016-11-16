package com.tel.gleisson.android.tel.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.tel.gleisson.android.tel.R;


/**
 * Created by Gleisson 21/10/2016.
 */

public class ClasseUtil extends AppCompatActivity {



    private AlertDialog.Builder builder;
    private static Context contexto = null;
    private ProgressDialog progressDialog;
    private String userChoosenTask;
    private String retorno;

    public ClasseUtil(Context context) {

        builder = new AlertDialog.Builder(context, R.style.AlertaPadraoTel);
        progressDialog = new ProgressDialog(context);
        this.contexto = context;
    }

    public void chamaProgresFim() {
        progressDialog.dismiss();
    }

    public void chamaProgess(String titulo, String mensagem) {
        progressDialog.setTitle(titulo);
        progressDialog.setMessage(mensagem);
        progressDialog.show();
    }

    public void chamaDialogo(String titulo, String mensagem) {
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        builder.show();
    }



}