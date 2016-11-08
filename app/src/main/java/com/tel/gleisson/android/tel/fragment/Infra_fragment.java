package com.tel.gleisson.android.tel.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.activity.DetalheCardActivity;

/**
 * Created by Gleisson e Rosy on 27/10/2016.
 */

public class Infra_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }


    //--------------------------INICIO ViewFolder---------------------------------//
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView picture;
        public TextView name;
        public TextView description;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.equipamento_card, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.imagem_card);
            name = (TextView) itemView.findViewById(R.id.titulo_card);
            description = (TextView) itemView.findViewById(R.id.texto_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetalheCardActivity.class);
                    intent.putExtra(DetalheCardActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }
        //--------------------------FIM ViewFolder---------------------------------//


        //--------------------------INICIO ContentAdapter---------------------------------//

        /**
         * Adapter to display recycler view.
         */
        public static class ContentAdapter extends RecyclerView.Adapter<Equipamento_fragment.ViewHolder> {
            // Set numbers of List in RecyclerView.
            private static final int LENGTH = 18;
            private final String[] mPlaces;
            private final String[] mPlaceDesc;
            private final Drawable[] mPlacePictures;

            public ContentAdapter(Context context) {
                Resources resources = context.getResources();
                mPlaces = resources.getStringArray(R.array.places);
                mPlaceDesc = resources.getStringArray(R.array.place_desc);
                TypedArray a = resources.obtainTypedArray(R.array.places_picture);
                mPlacePictures = new Drawable[a.length()];
                for (int i = 0; i < mPlacePictures.length; i++) {
                    mPlacePictures[i] = a.getDrawable(i);
                }
                a.recycle();
            }

            @Override
            public Equipamento_fragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new Equipamento_fragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
            }

            @Override
            public void onBindViewHolder(Equipamento_fragment.ViewHolder holder, int position) {

                holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
                holder.name.setText(mPlaces[position % mPlaces.length]);
                holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
            }

            @Override
            public int getItemCount() {
                return LENGTH;
            }
        }
        //--------------------------FIM ContentAdapter---------------------------------//

}

