package com.tel.gleisson.android.tel.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.tel.gleisson.android.tel.util.PrefUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gleisson 05/11/2016.
 */

public class ImplementaSolucao extends AppCompatActivity implements Solucao {

    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
    String retornoUpImagemOK = null;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private StorageReference imagesRef;
    private Context context;
    private Map<Uri, UploadTask> uploads;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private FirebaseUser user;
    public static User usuario;
    private User retornoUser;
    private PrefUser prefUser;


    public ImplementaSolucao(Context context) {
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReferenceFromUrl("gs://apptel-84297.appspot.com");
        this.context = context;
        this.uploads = new HashMap<>();
        progressDialog = new ProgressDialog(context);
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        prefUser = new PrefUser(context);
    }

    public ImplementaSolucao() {

    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }


    //-----------------------------------------------------------------------
    public void saveUserFirebase(FirebaseUser user) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUid()).setValue(usuario);
    }


    //----------------------------------------------------------------------------------

    @Override
    public void upLoadSolucao(String id
            , String data
            , String nome
            , String tituloSolucao
            , String palavraChaveSolucao
            , String tipoDaSolucao
            , String descricaoDaSolucao
            , String foto) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("Soluções").push().getKey();
        SolucaoObjeto solucaoObjeto = new SolucaoObjeto(id,data, nome, tituloSolucao, palavraChaveSolucao, tipoDaSolucao, descricaoDaSolucao, retornoUpImagemOK);
        Map<String, Object> postValues = solucaoObjeto.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Soluções/" + tipoDaSolucao + "/" + key, postValues);
        childUpdates.put("/Soluções_do_usuário/" + id + "/" + tipoDaSolucao + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

    }

//--------------------------------------------------------------------------------------------------------


    @Override
    public String upImagem(byte[] data, Context context) {

        enviaFotoFirebase(data, context);
        return retornoUpImagemOK;
    }


    public void enviaFotoFirebase(byte[] foto, Context context) {

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

//------------------------------------------------------------------------------------------------------------

    @Override
    public void getUsuarioFirebase(FirebaseUser user) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference refUsuario = mDatabase.child("users").child(user.getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nome = dataSnapshot.getValue(User.class).getNome().toString();
                prefUser.salvaUsuario(nome);
            }

  @Override
  public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(CriarNovoCardSolucao_Activity.this, getString(R.string), Toast.LENGTH_LONG).show();
            }
        };
        refUsuario.addValueEventListener(postListener);
    }



 //-------------------------------------------------------------------------------------------------------------------
    @Override
    public Boolean deleteSolucao() {
        return null;
    }

    @Override
    public Boolean changeSolucao() {
        return null;
    }


}
