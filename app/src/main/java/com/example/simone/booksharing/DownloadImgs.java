package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import org.lucasr.twowayview.TwoWayView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Utente on 01/05/2016.
 */

public class DownloadImgs extends AsyncTask<Void, Void, String> {

    private String url[];
    private ArrayList<Bitmap> images;
    private Context context;
    private  ArrayList<ItemBook> listaLibri;
    private HashMap<Integer, ItemBook> sliderMap;


    private TwoWayView slider;
    public DownloadImgs(String url[], Context context, TwoWayView slider, ArrayList<ItemBook> listaLibri, HashMap<Integer, ItemBook> sliderMap){
        this.url=url;
        this.slider=slider;
        this.context=context;
        this.images= new ArrayList<>();
        this.listaLibri = listaLibri;
        this.sliderMap=sliderMap;
    }


    @Override
    protected String doInBackground(Void... params) {
        try {
            Log.e("234", ""+url.length);
            for(int i=0; i<url.length; i++){
                Bitmap myBitmap;
                if(url[i]==null){
                    myBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.not_available);
                }
                else {
                    URL urlConnection = new URL(url[i]);

                    HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();


                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                }
                Log.e("234", ""+listaLibri.get(i).getCopertinaLink());
                images.add(myBitmap);
                listaLibri.get(i).setCopertina(myBitmap);
                sliderMap.put(i,listaLibri.get(i));
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


