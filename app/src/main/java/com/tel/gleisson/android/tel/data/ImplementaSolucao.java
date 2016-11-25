package com.tel.gleisson.android.tel.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gleisson 05/11/2016.
 */

public class ImplementaSolucao extends AppCompatActivity implements Solucao {

    private StorageReference storageRef;
    private FirebaseStorage storage;
    private StorageReference imagesRef;
    private Context context;
    private Map<Uri, UploadTask> uploads;
    private DatabaseReference mDatabase;
    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
    String retornoUpImagemOK = null;
    private static String nomeUsuario = null;
    private ProgressDialog progressDialog;



    public ImplementaSolucao(Context context) {
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReferenceFromUrl("gs://apptel-84297.appspot.com");
        this.context = context;
        this.uploads = new HashMap<>();
        progressDialog = new ProgressDialog(context);

    }
    public ImplementaSolucao() {

    }


    @Override
    public void upLoadSolucao(String id
            , String nome
            , String tituloSolucao
            , String palavraChaveSolucao
            , String tipoDaSolucao
            , String descricaoDaSolucao
            , String foto) {

       //   upLoadImagem(foto);

    //    String teste = retornoUpImagemOK;

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("Soluções").push().getKey();
        SolucaoObjeto solucaoObjeto = new SolucaoObjeto (id,nome, tituloSolucao, palavraChaveSolucao, tipoDaSolucao, descricaoDaSolucao,retornoUpImagemOK );
        Map<String, Object> postValues = solucaoObjeto.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Soluções/" +tipoDaSolucao+ "/" +key, postValues);
        childUpdates.put("/Soluções_do_usuário/" + id + "/" + tipoDaSolucao + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);

        //   mDatabase.child("Soluções").child(tipoDaSolucao).child(tituloSolucao).setValue(postValues);
    }

    @Override
    public void updateSolucao(String id, String nome, String titulo, String palavraChave, String tipoSolucao, String descricao, String uri) {

    }

 public String upImagem(byte[] data, Context context){

     upLoadImagem(data, context);
     return retornoUpImagemOK;
 }

    @Override
    public void upLoadImagem(byte[] foto, Context context) {

        progressDialog.setMessage("Salvando imagem...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        UploadTask uploadTask = storageRef.child("images/" + timeStamp).putBytes(foto);
        uploadTask.addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                  progressDialog.dismiss();
                  retornoUpImagemOK = taskSnapshot.getMetadata().getDownloadUrl().toString();
            }
        });
    }



    @Override
    public Boolean deleteSolucao() {
        return null;
    }

    @Override
    public Boolean changeSolucao() {
        return null;
    }
}
