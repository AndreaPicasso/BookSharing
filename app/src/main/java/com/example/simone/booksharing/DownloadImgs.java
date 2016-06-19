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
import android.widget.ProgressBar;

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
    private  ProgressBar progressBar;


    private TwoWayView slider;
    public DownloadImgs(String url[], Context context, TwoWayView slider, ArrayList<ItemBook> listaLibri, HashMap<Integer, ItemBook> sliderMap,ProgressBar progressBar){
        this.url=url;
        this.progressBar=progressBar;
        this.slider=slider;
        this.context=context;
        this.images= new ArrayList<>();
        this.listaLibri = listaLibri;
        this.sliderMap=sliderMap;
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
                    Log.e("caricamentoesterno","inizio");
                    myBitmap = ImgsStorageManager.loadImageFromStorage(listaLibri.get(i).getISBN(),context);
                    Log.e("caricamentoesterno","fine");
                    if (myBitmap == null) {
                        Log.e("imgasd","NON TROVATA");
                        //Se non la trovo in memoria la scarico
                        URL urlConnection = new URL(url[i]);
                        HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        myBitmap = BitmapFactory.decodeStream(input);
                        //E poi la salvo con il nome del link
                        ImgsStorageManager.saveToInternalStorage(myBitmap,context,listaLibri.get(i).getISBN());
                    }
                    else{
                        Log.e("imgasd","TROVATA");

                    }
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
        progressBar.setVisibility(View.INVISIBLE);
        slider.setVisibility(View.VISIBLE);


        super.onPostExecute(s);
    }
}


