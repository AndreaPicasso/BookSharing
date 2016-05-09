package com.example.simone.booksharing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.lucasr.twowayview.TwoWayView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Utente on 01/05/2016.
 */

public class DownloadImg extends AsyncTask<Void, Void, String> {

    private String url[];
    private ArrayList<Bitmap> images;
    private Context context;

    private TwoWayView slider;
    public DownloadImg(String url[], Context context, TwoWayView slider){
        this.url=url;
        this.slider=slider;
        this.context=context;
        this.images= new ArrayList<>();


    }


    @Override
    protected String doInBackground(Void... params) {
        try {
            for(int i=0; i<url.length; i++){
                Bitmap myBitmap;
                if(url[i]==null){
                    myBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.not_available);
                }
                else {
                    URL urlConnection = new URL(url[i]);
                    Log.e("url", url[i]);

                    HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();


                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                    Log.e("Bitmap", "returned");
                    Log.e("url", "" + images.size());
                }
                images.add(myBitmap);
            }




            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        MyAdapter m=new MyAdapter(this.context,R.layout.list_item_img_book,this.images);
        slider.setAdapter(m);
        super.onPostExecute(s);
    }
}


