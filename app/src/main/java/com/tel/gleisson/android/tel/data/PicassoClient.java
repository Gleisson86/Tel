package com.tel.gleisson.android.tel.data;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tel.gleisson.android.tel.R;

/**
 * Created by Gleisson e Rosy on 18/11/2016.
 */

public class PicassoClient {

    public static void downloaImage(Context context, String url, ImageView img) {
        if (url != null && url.length() > 0) {
            Picasso
                    .with(context)
                    .load(url)
                    .placeholder(R.drawable.chipcard)
                    .resize(640, 480)
                    .into(img);

        } else {
            Picasso
                    .with(context)
                    .load(R.drawable.chipcard)
                    .resize(640, 480)
                    .into(img);
        }
    }


}
