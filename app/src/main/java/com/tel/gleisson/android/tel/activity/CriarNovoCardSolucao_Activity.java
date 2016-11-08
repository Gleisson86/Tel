package com.tel.gleisson.android.tel.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.data.ImplementaSolucao;
import com.tel.gleisson.android.tel.data.SolucaoObjeto;
import com.tel.gleisson.android.tel.util.MarshMallowPermissao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class CriarNovoCardSolucao_Activity extends AppCompatActivity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int INTENT_GALERIA = 2;
    public ImplementaSolucao implementaSolucao;
    boolean result;
    MarshMallowPermissao marshMallowPermission;
    int i = 0;
    String[] uri = new String[5];

    private ImageButton botaoVoltarSolucao;
    private ImageButton botaoConfirmarSolucao;
    private ImageButton botaoAddFotoGaleriaOuCamera;
    private EditText titulo;
    private EditText palavraChave;
    private EditText descricao;
    private EditText tipoSolucao;
    private ImageView image1solucao;
    private ImageView image2solucao;
    private ImageView image3solucao;
    private ImageView image4solucao;
    private ImageView image5solucao;
    private Uri fileUri; // file url to store image/video
    private ImageView imgPreview;
    private BaseActivity base;
    private AlertDialog.Builder builder;
    private StorageReference StorageRef;
    private DatabaseReference mDatabase;
    Uri uriIImagemSalva;
    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_novo_card_solucao_activity);


        tipoSolucao = (EditText) findViewById(R.id.tipoSolucao);
        titulo = (EditText) findViewById(R.id.tituloSolucao);
        palavraChave = (EditText) findViewById(R.id.palavraChaveSolucao);
        descricao = (EditText) findViewById(R.id.descricaoSolucao);
        implementaSolucao = new ImplementaSolucao();
        StorageRef = FirebaseStorage.getInstance().getReference();
        builder = new AlertDialog.Builder(this);
        image1solucao = (ImageView) findViewById(R.id.image1Solucao);
        image2solucao = (ImageView) findViewById(R.id.image2Solucao);
        image3solucao = (ImageView) findViewById(R.id.image3Solucao);
        image4solucao = (ImageView) findViewById(R.id.image4Solucao);
        image5solucao = (ImageView) findViewById(R.id.image5Solucao);
        botaoAddFotoGaleriaOuCamera = (ImageButton) findViewById(R.id.botao_add_foto_CameraOuGaleria);
        botaoVoltarSolucao = (ImageButton) findViewById(R.id.botao_voltar_solucao);
        botaoConfirmarSolucao = (ImageButton) findViewById(R.id.botao_confirmar_solucao);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        botaoAddFotoGaleriaOuCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                result = VerificapermissaoParaCameraAndStorage();
                final CharSequence[] itens = {"Camera", "Galeria", "Cancelar"};
                builder.setTitle("Add Photo!");
                builder.setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (itens[item].equals("Camera")) {
                            if (result)
                                SalvaUmaFotoNaSolucao();
                        } else if (itens[item].equals("Galeria")) {
                            if (result)
                                galeriaIntent();
                        } else if (itens[item].equals("Cancelar")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }


            public void galeriaIntent() {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, INTENT_GALERIA);
            }


            public void SalvaUmaFotoNaSolucao() {
                if (isDeviceSupportCamera()) {
                    captureImage();
                }
            }


            public boolean isDeviceSupportCamera() {
                if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    // this device has a camera
                    return true;
                } else {
                    // no camera on this device
                    return false;
                }
            }


            public void captureImage() {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                // start the image capture Intent
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }


            private Uri getOutputMediaFileUri(int type) {
                return Uri.fromFile(getOutputMediaFile(type));
            }


            public File getOutputMediaFile(int type) {
                // To be safe, you should check that the SDCard is mounted
                // using Environment.getExternalStorageState() before doing this.


                /* Checks if external storage is available for read and write */
                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), "Tel");
                // This location works best if you want the created images to be shared
                // between applications and persist after your app has been uninstalled.
                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        Log.d("el", "falha ao criar o diretório");
                        return null;
                    }
                }
                // Create a media file name

                File mediaFile;
                if (type == MEDIA_TYPE_IMAGE) {
                    mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                            "IMG_" + timeStamp + ".jpg");
                } else if (type == MEDIA_TYPE_VIDEO) {
                    mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                            "VID_" + timeStamp + ".mp4");
                } else {
                    return null;
                }
                return mediaFile;
            }


            public Boolean VerificapermissaoParaCameraAndStorage() {

                marshMallowPermission = new MarshMallowPermissao(CriarNovoCardSolucao_Activity.this);

                if (!marshMallowPermission.checkPermissionForCamera()) {
                    marshMallowPermission.requestPermissionForCamera();
                }
                if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                    marshMallowPermission.requestPermissionForExternalStorage();
                } else {
                    return true;
                }
                return false;
            }

        });


        botaoVoltarSolucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        botaoConfirmarSolucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adpterNovaSolucao(FirebaseAuth.getInstance().getCurrentUser());
            }
        });

    }


    public void adpterNovaSolucao(FirebaseUser user) {

        String tituloSolucao = titulo.getText().toString().trim();
        String palavraChaveSolucao = palavraChave.getText().toString().trim();
        String tipoDaSolucao = tipoSolucao.getText().toString().trim();
        String descricaoDaSolucao = descricao.getText().toString().trim();

        escreveNovaSolucao(user.getUid(), tituloSolucao, palavraChaveSolucao, tipoDaSolucao, descricaoDaSolucao);
    }


    // [START basic_write]
    private void escreveNovaSolucao(String id, String tituloSolucao, String palavraChaveSolucao, String tipoDaSolucao, String descricaoDaSolucao) {

        SolucaoObjeto solucaoObjeto = new SolucaoObjeto(id, tituloSolucao, palavraChaveSolucao, tipoDaSolucao, descricaoDaSolucao, uri);
        mDatabase.child("Soluções").child(tipoDaSolucao).setValue(solucaoObjeto);
        Toast.makeText(CriarNovoCardSolucao_Activity.this, "upload realizado", Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                //     mImageLabel.setImageBitmap(imageBitmap);
                //    encodeBitmapAndSaveToFirebase(imageBitmap);


                previewCapturedImage();
            }
            if (requestCode == INTENT_GALERIA) {
                uriIImagemSalva = data.getData();
                StorageReference imagesRef = StorageRef.child("images/"+timeStamp);

                imagesRef.putFile(uriIImagemSalva).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                    @Override
                    public void onSuccess(TaskSnapshot taskSnapshot) {
                        Toast.makeText(CriarNovoCardSolucao_Activity.this, "upload realizado", Toast.LENGTH_LONG).show();
                        armazenaUriDeFotosStorage(taskSnapshot.getDownloadUrl().toString());
                        previewCapturedImage();

                    }
                });
            }
        } else if (resultCode == RESULT_CANCELED) {
            // user cancelled Image capture
            Toast.makeText(getApplicationContext(),
                    "Foto cancelada", Toast.LENGTH_SHORT)
                    .show();
        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(),
                    "Desculpa! Falha ao tirar foto", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    private void previewCapturedImage() {

        if (i < 5) {

            switch(i) {

                case 1:

                    try {
                        // hide video previe
                        // videoPreview.setVisibility(View.GONE);
                        image1solucao.setVisibility(View.VISIBLE);
                        // bimatp factory
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // downsizing image as it throws OutOfMemory Exception for larger
                        // images
                        options.inSampleSize = 8;
                        Bitmap bitmap = BitmapFactory.decodeFile(uriIImagemSalva.getPath(), options);
                        image1solucao.setImageBitmap(bitmap);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:

                    try {
                        // hide video previe
                        // videoPreview.setVisibility(View.GONE);
                        image2solucao.setVisibility(View.VISIBLE);
                        // bimatp factory
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // downsizing image as it throws OutOfMemory Exception for larger
                        // images
                        options.inSampleSize = 8;
                        Bitmap bitmap = BitmapFactory.decodeFile(uriIImagemSalva.getPath(), options);
                        image2solucao.setImageBitmap(bitmap);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:

                    try {
                        // hide video previe
                        // videoPreview.setVisibility(View.GONE);
                        image3solucao.setVisibility(View.VISIBLE);
                        // bimatp factory
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // downsizing image as it throws OutOfMemory Exception for larger
                        // images
                        options.inSampleSize = 8;
                        Bitmap bitmap = BitmapFactory.decodeFile(uriIImagemSalva.getPath(), options);
                        image3solucao.setImageBitmap(bitmap);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        // hide video previe
                        // videoPreview.setVisibility(View.GONE);
                        image4solucao.setVisibility(View.VISIBLE);
                        // bimatp factory
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // downsizing image as it throws OutOfMemory Exception for larger
                        // images
                        options.inSampleSize = 8;
                        Bitmap bitmap = BitmapFactory.decodeFile(uriIImagemSalva.getPath(), options);
                        image4solucao.setImageBitmap(bitmap);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    try {
                        // hide video previe
                        // videoPreview.setVisibility(View.GONE);
                        image5solucao.setVisibility(View.VISIBLE);
                        // bimatp factory
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // downsizing image as it throws OutOfMemory Exception for larger
                        // images
                        options.inSampleSize = 8;
                        Bitmap bitmap = BitmapFactory.decodeFile(uriIImagemSalva.getPath(), options);
                        image5solucao.setImageBitmap(bitmap);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        break;
                    }

            }
        }

    }


    private void armazenaUriDeFotosStorage(String uri) {
        if (i < 5) {
            this.uri[i] = uri;
            i++;
        }
    }
}
