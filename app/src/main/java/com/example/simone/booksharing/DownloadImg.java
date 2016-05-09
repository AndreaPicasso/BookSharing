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

public class DownloadImg extends AsyncTask<Void, Void, String> {

    private String url[];
    private ArrayList<Bitmap> images;
    private Context context;
    private  ArrayList<ItemBook> listaLibri;
    private HashMap<Bitmap, ItemBook> sliderMap;


    private TwoWayView slider;
    public DownloadImg(String url[], Context context, TwoWayView slider, ArrayList<ItemBook> listaLibri){
        this.url=url;
        this.slider=slider;
        this.context=context;
        this.images= new ArrayList<>();
        this.listaLibri = listaLibri;
        sliderMap = new HashMap<>();
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
                listaLibri.get(i).setCopertina(myBitmap);
                sliderMap.put(myBitmap, listaLibri.get(i));
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
        /*
        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ItemBook choose = sliderMap.get(view);
                final Activity act = (Activity)context;
                act.getFragmentManager().beginTransaction().replace(R.id.home_fragment, new BookFragment()).addToBackStack(null).commit();
            }
        });
        */
        slider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //ItemBook choose = sliderMap.get(view);
                final Activity act = (Activity)context;
                act.getFragmentManager().beginTransaction().replace(R.id.home_fragment, new BookFragment()).addToBackStack(null).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        super.onPostExecute(s);
    }
}


