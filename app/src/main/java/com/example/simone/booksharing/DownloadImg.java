package com.example.simone.booksharing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Utente on 01/05/2016.
 */

public class DownloadImg extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView image;

    public DownloadImg(String url){
        this.url=url;

    }
    public ImageView getImage(){
        return this.image;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            Log.e("url", url);

            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        this.image.setImageBitmap(result);
    }

}


