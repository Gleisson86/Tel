package com.tel.gleisson.android.tel.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.tel.gleisson.android.tel.activity.LoginActivity;

import static com.tel.gleisson.android.tel.R.style.AlertaPadraoTel;


/**
 * Created by Gleisson 21/10/2016.
 */

public class ClasseUtil extends AppCompatActivity {


    private static Context contexto = null;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;
    private String userChoosenTask;
    private String retorno;
    public static String resposta = null;

    public ClasseUtil() {

    }

    public ClasseUtil(Context context) {

        builder = new AlertDialog.Builder(context, AlertaPadraoTel);
        progressDialog = new ProgressDialog(context,AlertaPadraoTel );
        this.contexto = context;
    }

    public void chamaProgresFim() {
        progressDialog.dismiss();
    }

    public void chamaProgess(String titulo, String mensagem) {


        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
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

    public void chamaPopupSairFinalizaActivity(final Activity context , final Class<LoginActivity> activity, final FirebaseAuth auth) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    auth.signOut();
                    Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                    context.startActivity(intent);
                    context.finish();

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    builder.setMessage("Realmente você deseja sair?");
    builder.setPositiveButton("Sim", dialogClickListener);
    builder.setNegativeButton("Não", dialogClickListener);
    builder.show();

}
}