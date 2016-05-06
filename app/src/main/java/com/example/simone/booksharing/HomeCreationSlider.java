package com.example.simone.booksharing;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simone on 05/05/2016.
 */
public class HomeCreationSlider {
    private Context context;
    private  TwoWayView slider;
    HomeCreationSlider( Context context,  TwoWayView slider){
        this.slider=slider;
        this.context=context;
        Log.e("Creoslider","costruttore");
    }

    public  void start(){

        UnigeServerConnection unigeCon = new UnigeServerConnection(new UnigeServerConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {
                try {
                    String isbn, proprietario;
                    double lat,lon;
                    int numBook = risposta.getInt("number");
                    JSONArray books = risposta.getJSONArray("items");
                    if(numBook>5)   numBook =5;             /*Massimo caricane solo 5 */
                    ArrayList<ItemBook> toCreate = new ArrayList<>(numBook);
                    for(int i = 0; i<numBook; i++){
                        isbn = books.getJSONObject(i).getString("isbn");
                        proprietario = books.getJSONObject(i).getString("proprietario");
                        lat = books.getJSONObject(i).getDouble("lat");
                        lon = books.getJSONObject(i).getDouble("lon");
                        toCreate.add(new ItemBook(isbn,lat,lon,proprietario));

                        /*
                        if(i==0){
                            isbn="9780099908401";
                            Log.e("webdev", "isbn" + isbn);
                        }
                        else{
                            isbn="9788858754405";
                            Log.e("webdev", "isbn" + isbn);
                        }
                        toCreate.add(new ItemBook(isbn,0,0,null));

                        */
                    }
                    ListaLibri list= new ListaLibri(toCreate,context,slider);
                    list.Riempi();




                }catch(Exception e){
                    Log.e("Eccezione webdev",e.getMessage());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);
                return params;
            }

            @Override
            public String getURL() {

                return UnigeServerConnection.URL+UnigeServerConnection.RICERCA;
            }
        });
        unigeCon.sendRequest(context);

    }
}
