package com.tel.gleisson.android.tel.data;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
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

public class ImplementaSolucao implements Solucao {

    private StorageReference storageRef;
    private FirebaseStorage storage;
    private StorageReference imagesRef;
    private Context context;
    private Map<Uri, UploadTask> uploads;
    private DatabaseReference mDatabase;
    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
    String retornoUpImagemOK = null;
    private static String nomeUsuario = null;

    public ImplementaSolucao(Context context) {
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReferenceFromUrl("gs://apptel-84297.appspot.com/");
        this.imagesRef = storageRef.child("images/" + timeStamp);
        this.context = context;
        this.uploads = new HashMap<>();

    }
    public ImplementaSolucao() {

    }

    @Override
    public void upLoadSolucao(String id, String nome, String tituloSolucao, String palavraChaveSolucao, String tipoDaSolucao, String descricaoDaSolucao, Uri uri) {

          upLoadImagem(uri);

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


    @Override
    public void upLoadImagem(Uri uri) {


        imagesRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(TaskSnapshot taskSnapshot) {retornoUpImagemOK = taskSnapshot.getDownloadUrl().toString();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                retornoUpImagemOK = null;
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
