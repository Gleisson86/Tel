package com.tel.gleisson.android.tel.util;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tel.gleisson.android.tel.data.User;

/**
 * Created by Gleisson e Rosy on 16/11/2016.
 */

public class SingletonFirebase implements ValueEventListener{

    private String nome=null;


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot d : dataSnapshot.getChildren()) {
            User u = d.getValue(User.class);
            this.nome = u.getName();
            Log.i ("log", "name" + u.getName());
            }
          }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    public String retornaNome(){
        return this.nome;
    }

}
