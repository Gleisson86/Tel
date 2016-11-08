package com.tel.gleisson.android.tel.data;

import android.net.Uri;

/**
 * Created by Gleisson 05/11/2016.
 */

public class ImplementaSolucao implements Solucao {


    @Override
    public Boolean upLoadSolucao(Uri uri) {

        final int MEDIA_TYPE_IMAGE = 1;
        final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
        final int INTENT_GALERIA = 2;

        return null;
    }


    /*

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                // successfully captured the image
                // display it in image view
               // previewCapturedImage();
            }
            if(requestCode == INTENT_GALERIA){
                Uri uri = data.getData();
                StorageReference imagesRef = StorageRef.child("images");

                imagesRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                    @Override
                    public void onSuccess(TaskSnapshot taskSnapshot) {
                        Toast.makeText(CriarNovoCardSolucao_Activity.this,"upload realizado", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        else if (resultCode == RESULT_CANCELED) {
            // user cancelled Image capture
            Toast.makeText(getApplicationContext(),"Foto cancelada", Toast.LENGTH_SHORT)
                    .show();
        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(),"Desculpa! Falha ao tirar foto", Toast.LENGTH_SHORT)
                    .show();
        }
    } */

    @Override
    public Boolean deleteSolucao() {
        return null;
    }

    @Override
    public Boolean changeSolucao() {
        return null;
    }
}
