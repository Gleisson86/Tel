package com.tel.gleisson.android.tel.util;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tel.gleisson.android.tel.data.SolucaoObjeto;

import java.util.ArrayList;

/**
 * Created by Gleisson e Rosy on 10/12/2016.
 */

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved=null;
   final static ArrayList<SolucaoObjeto> solucaoObjetos = new ArrayList<SolucaoObjeto>();


    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

   public ArrayList<SolucaoObjeto> buscaSolucoes(){
       solucaoObjetos.clear();


       db.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot Arraysolucoes: dataSnapshot.getChildren()) {

                   SolucaoObjeto solucao = Arraysolucoes.getValue(SolucaoObjeto.class);
                   solucaoObjetos.add(solucao);

               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               // Getting Post failed, log a message
           }
       });


       return solucaoObjetos;
   }



    public ArrayList<SolucaoObjeto> retrieve(){

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                fetchData(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return solucaoObjetos;
    }


    private void fetchData(DataSnapshot dataSnapshot)
    {

/*
        db.addChildEventListener()
   //     for (DataSnapshot ds : dataSnapshot.getChildren()){

            SolucaoObjeto solucao =  new SolucaoObjeto();

          //  solucao.setNome(ds.getValue().toString());
          //  solucao.setData(ds.getValue(SolucaoObjeto.class).getData());
           // solucao.setFoto(ds.getValue(SolucaoObjeto.class).getFoto());


            solucao = dataSnapshot.getValue(SolucaoObjeto.class);


            System.out.println("SAIDA = "+ solucao.getTitulo()+" to UI after ");
            solucaoObjetos.add(solucao);
    //   }

   */
        solucaoObjetos.clear();


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Arraysolucoes: dataSnapshot.getChildren()) {

                    SolucaoObjeto solucao = Arraysolucoes.getValue(SolucaoObjeto.class);
                    solucaoObjetos.add(solucao);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

    }
}
