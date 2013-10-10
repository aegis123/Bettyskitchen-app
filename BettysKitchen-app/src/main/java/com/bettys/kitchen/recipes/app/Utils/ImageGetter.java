package com.bettys.kitchen.recipes.app.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.bettys.kitchen.recipes.app.RecipeApplication;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by Dylan on 9-10-13.
 */
public class ImageGetter implements android.text.Html.ImageGetter {
    class URLDrawable extends BitmapDrawable {
        // the drawable that you need to set, you could set the initial drawing
        // with the loading image if you need to
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if(drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

    class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable>  {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            Uri uri = Uri.parse(source);
            Bitmap bitmap = null;
            try {
                Picasso pic = new Picasso.Builder(mContext).debugging(true).build();
                bitmap = pic.load(uri).get();
            } catch (IOException e) {
                Log.e(RecipeApplication.TAG, e.getMessage());
            }
            return new BitmapDrawable(mContext.getResources(),bitmap);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // set the correct bound according to the result from HTTP call
            urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0
                    + result.getIntrinsicHeight());

            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            ImageGetter.this.mView.invalidate();
        }
    }

    private final Context mContext;
    private final View mView;

    public ImageGetter(Context context, View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public Drawable getDrawable(String source) {
        Uri uri = Uri.parse(source);
        URLDrawable urlDrawable = new URLDrawable();
        ImageGetterAsyncTask task = new ImageGetterAsyncTask(urlDrawable);
        task.execute(source);
        return urlDrawable;
    }
}
