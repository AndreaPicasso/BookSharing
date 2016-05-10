package com.example.simone.booksharing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Utente on 10/05/2016.
 */
public class DownloadSingleImg extends AsyncTask<Void, Void, String> {

    private String url;
    private ImageView imgPlace;
    private Bitmap img;
    private Context context;

    public DownloadSingleImg(String url, Context context, ImageView img){
        this.imgPlace=img;
        this.context=context;
        this.url=url;
    }
    @Override
    protected String doInBackground(Void... params) {
        try {


                if(url==null){
                    img = BitmapFactory.decodeResource(context.getResources(), R.drawable.not_available);
                }
                else {
                    URL urlConnection = new URL(url);

                    HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();


                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    img = BitmapFactory.decodeStream(input);
                }






            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

      imgPlace.setImageBitmap(img);

        super.onPostExecute(s);
    }
}
