package com.tel.gleisson.android.tel.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.data.PicassoClient;

public class DetalheCardActivity extends AppCompatActivity {

    private int icon_backhome;


    public static final String EXTRA_POSITION = "position";


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
             finish();
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detalhe_card_solucao_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarDetalheCard));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        // Set Collapsing Toolbar layout to the screen
         CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        //  int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);

        Bundle bundle = getIntent().getBundleExtra("EXTRAS_SOLUCAO");
        int position = getIntent().getIntExtra("posicao",0);

        TextView data = (TextView) findViewById(R.id.dataAutorViewDetalhe);
        TextView titulo = (TextView) findViewById(R.id.tituloSolucaoDetalhe);
        TextView descricao = (TextView) findViewById(R.id.descricaoSolucaoDetalhe);
      //TextView palavraChave = (TextView) findViewById(R.id.tituloSolucaoDetalhe);
        TextView nome = (TextView) findViewById(R.id.nomeAutorViewDetalhe);
        ImageView foto = (ImageView) findViewById(R.id.imagemDetalheSolucao);


        nome.setText(bundle.getString("nome"));
        data.setText(bundle.getString("data"));
        titulo.setText(bundle.getString("titulo"));
        descricao.setText(bundle.getString("descricao"));
        PicassoClient.downloaImage(this,bundle.getString("url"),foto);


    }
}
