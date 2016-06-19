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
    private HashMap<Integer, ItemBook> sliderMap;
    HomeCreationSlider( Context context,  TwoWayView slider, HashMap<Integer, ItemBook> sliderMap){
        this.slider=slider;
        this.context=context;
        this.sliderMap = sliderMap;

    }

    public  void start(final Map<String, String> unigeSearchParam, final Map<String, String> googleSearchParam){

        UnigeServerConnection unigeCon = new UnigeServerConnection(new UnigeServerConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {
                try {
                    String isbn, proprietario,disp;
                    double lat,lon;
                    boolean disponibile=true;
                    int numBook = risposta.getInt("number");
                    JSONArray books = risposta.getJSONArray("items");
                    //if(numBook>5)   numBook =5;             /*Massimo caricane solo 5 */
                    ArrayList<ItemBook> toCreate = new ArrayList<>(numBook);
                    for(int i = 0; i<numBook; i++){
                        isbn = books.getJSONObject(i).getString("isbn");
                        proprietario = books.getJSONObject(i).getString("proprietario");
                        lat = books.getJSONObject(i).getDouble("lat");
                        lon = books.getJSONObject(i).getDouble("lon");
                        disp= books.getJSONObject(i).getString("disponibile");
                        if(disp.equals("no")) {
                            disponibile = false;
                        }
                        else {
                            disponibile = true;
                        }
                        toCreate.add(new ItemBook(isbn,lat,lon,proprietario,disponibile));
                    }
                    ListaLibri list= new ListaLibri(toCreate,context,slider,sliderMap);
                    list.Riempi(googleSearchParam);





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
                if(unigeSearchParam!=null) {
                    params.putAll(unigeSearchParam);
                }
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
