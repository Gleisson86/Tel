package com.tel.gleisson.android.tel.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.data.ImplementaSolucao;
import com.tel.gleisson.android.tel.util.ClasseUtil;
import com.tel.gleisson.android.tel.util.MarshMallowPermissao;
import com.tel.gleisson.android.tel.util.SingletonFirebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.tel.gleisson.android.tel.R.id.palavraChaveSolucao;
import static com.tel.gleisson.android.tel.R.id.tituloSolucao;


public class CriarNovoCardSolucao_Activity extends AppCompatActivity implements OnItemSelectedListener {

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int INTENT_GALERIA = 2;
    public ImplementaSolucao implementaSolucao;
    String retornoUpImagemOK = null;
    boolean result;
    MarshMallowPermissao marshMallowPermission;
    int i = 1;
    private Uri uriIImagemSalva = null;
    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
    String tipoSolucaoAdapter;
    private ClasseUtil classeUtil;
    private ImageButton botaoVoltarSolucao;
    private ImageButton botaoConfirmarSolucao;
    private ImageButton botaoAddFotoGaleriaOuCamera;
    private EditText titulo;
    private EditText palavraChave;
    private EditText descricao;
    private ImageView image1solucao;
    private Spinner tipoSolucao;
    private Uri fileUri; // file url to store image/video
    private AlertDialog.Builder builder;
    private StorageReference StorageRef;
    private DatabaseReference mDatabase;
    private Firebase firebase;
    private SingletonFirebase singletonFirebase;
    private String  nomeUsuarioSolucao;
    private FirebaseUser usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_novo_card_solucao_activity);


        implementaSolucao = new ImplementaSolucao(this);
        tipoSolucao = (Spinner) findViewById(R.id.tipoSolucao);
        titulo = (EditText) findViewById(tituloSolucao);
        palavraChave = (EditText) findViewById(palavraChaveSolucao);
        descricao = (EditText) findViewById(R.id.descricaoSolucao);
        StorageRef = FirebaseStorage.getInstance().getReference();
        builder = new AlertDialog.Builder(this);
        image1solucao = (ImageView) findViewById(R.id.image1Solucao);
        botaoAddFotoGaleriaOuCamera = (ImageButton) findViewById(R.id.botao_add_foto_CameraOuGaleria);
        botaoVoltarSolucao = (ImageButton) findViewById(R.id.botao_voltar_solucao);
        botaoConfirmarSolucao = (ImageButton) findViewById(R.id.botao_confirmar_solucao);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        classeUtil = new ClasseUtil(this);
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        singletonFirebase = new SingletonFirebase();







        //--------------ABRE ADAPTER QUE CRIAR O SPINNER NO LAYOUT

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tiposolucao_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoSolucao.setAdapter(adapter);
        //--------------FECHA O  ADAPTER QUE CRIAR O SPINNER NO LAYOUT



        //------------------ABRE ADD FOTO --------------------------------------

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

        //--------------------------FECHA O ADD FOTO

        botaoVoltarSolucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        botaoConfirmarSolucao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String tituloText = titulo.getText().toString().trim();
                String descricaoDaSolucao = descricao.getText().toString().trim();
                String palavraChaveText = palavraChave.getText().toString().trim();
                TextView textView = (TextView) tipoSolucao.getSelectedView();
                tipoSolucaoAdapter = textView.getText().toString();
                if (TextUtils.isEmpty(tituloText)) {
                    classeUtil.chamaDialogo(getString(R.string.atencao), getString(R.string.tituloVazio));
                    return;
                }

                if (TextUtils.isEmpty(palavraChaveText)) {
                    classeUtil.chamaDialogo(getString(R.string.atencao), getString(R.string.palavraChaveVazio));
                    return;
                }

                if (TextUtils.isEmpty(tipoSolucaoAdapter)) {
                    classeUtil.chamaDialogo(getString(R.string.atencao), getString(R.string.tiposolucaoVazio));
                    return;
                }

                if (TextUtils.isEmpty(descricaoDaSolucao)) {
                    classeUtil.chamaDialogo(getString(R.string.atencao), getString(R.string.descricaoVazio));
                    return;
                }

         /*       if (password.length()<6) {
                    classeUtil.chamaDialogo(getString(R.string.senhaCurtaTitulo),getString(R.string.senhaCurtaMensagem));
                    //Toast.makeText(getApplicationContext(), getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();
                    return;
                } */
                adpterNovaSolucao(FirebaseAuth.getInstance().getCurrentUser());
            }
        });

    }
    //-----------------FECHA O onCREATE

    public void adpterNovaSolucao(FirebaseUser user) {

        //  Solucao implementaSolucao = new ImplementaSolucao(this);
        String tituloSolucao = titulo.getText().toString().trim();
        String palavraChaveSolucao = palavraChave.getText().toString().trim();
        String descricaoDaSolucao = descricao.getText().toString().trim();

        TextView textView = (TextView) tipoSolucao.getSelectedView();
        tipoSolucaoAdapter = textView.getText().toString();
        // mDatabase.child("users").child(user.getUid());

      //  firebase.setAndroidContext(this);
     //   if(auth.getCurrentUser() != null)
     //       firebase = new Firebase("https://apptel-84297.firebaseio.com").child("users").child(user.getUid());

    //    singletonFirebase = new SingletonFirebase();
     //   firebase.addValueEventListener(singletonFirebase);


        String nome = "Gleisson";

        implementaSolucao.upLoadSolucao(user.getUid(), nome, tituloSolucao, palavraChaveSolucao
                , tipoSolucaoAdapter
                , descricaoDaSolucao
                , fileUri);
        Toast.makeText(CriarNovoCardSolucao_Activity.this, "Solução criada com sucesso!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(CriarNovoCardSolucao_Activity.this, MainActivity.class));
        finish();
    }




    //-------------------------ABRE ENVIA FOTO FIREBASE-------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                //    retornoUpImagemOK =  implementaSolucao.upLoadImagem(fileUri);
                previewCapturedImage(fileUri);
                //   Toast.makeText(CriarNovoCardSolucao_Activity.this, "", Toast.LENGTH_LONG).show();

            }
            if (requestCode == INTENT_GALERIA) {
                fileUri = data.getData();
                // retornoUpImagemOK =  implementaSolucao.upLoadImagem( uriIImagemSalva);
                previewCapturedImage(fileUri);

              /*
                StorageReference imagesRef = StorageRef.child("images/" + timeStamp);
                imagesRef.putFile(uriIImagemSalva).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                    @Override
                    public void onSuccess(TaskSnapshot taskSnapshot) {
                        Toast.makeText(CriarNovoCardSolucao_Activity.this, "upload realizado", Toast.LENGTH_LONG).show();
                        teste = taskSnapshot.getDownloadUrl().toString();
                        previewCapturedImage(uriIImagemSalva);
                    }
                });
                */
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

    //-------------------------FIM ENVIA FOTO FIREBASE-------------------------------

    public byte[] encodeBitmapAndSaveToFirebase(Uri uri) {

        Bitmap bm=null;
        try {
            bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        //  String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        byte[] data = baos.toByteArray();
        return data;
    }


    //----------------ABRE PREVIEW IMAGEM NA TELA DE CADATRO DE SOLUÇÃO------------
    private void previewCapturedImage(Uri uri) {
        Bitmap bm=null;

        try {
            bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        image1solucao.setImageBitmap(bm);
    }


    //---------------FECHA PREVIEW IMAGEM NA TELA DE CADATRO DE SOLUÇÃO------------

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}