package com.tel.gleisson.android.tel.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Gleisson 21/10/2016.
 */

public class ClasseUtil extends AppCompatActivity {


    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private AlertDialog.Builder builder;
    private static Context contexto = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    int SELECT_FILE = 1;
    private ProgressDialog progressDialog;
    private String userChoosenTask;
    private String retorno;

    public ClasseUtil(Context context) {

        builder = new AlertDialog.Builder(context);
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

    // metodo abre dialogo para o usuário selecionar em um popup, em qual lugar virá a imagem
    // que será adicionada na solução

    public String SelecionaOpcaoDeImagem() {

        final CharSequence[] itens = {"Tirar foto", "Galeria", "Cancelar"};
        builder.setTitle("Add Photo!");
        builder.setItems(itens, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
            //    boolean result = verificaPermissao();
                if (itens[item].equals("Tirar foto")) {
                    userChoosenTask = "Tirar foto";
                 retorno = "Tirar foto";
                } else if (itens[item].equals("Galeria")) {
                    userChoosenTask = "Choose from Library";
                    retorno = "Galeria";
                } else if (itens[item].equals("Cancelar")) {
                    dialog.dismiss();
                    retorno = "Cancelar";
                }
            }
        });
        builder.show();
        return retorno;
    }

}