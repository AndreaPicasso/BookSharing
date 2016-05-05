package com.example.simone.booksharing;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by simone on 05/05/2016.
 */
public class HomeCreationSlider {

    public static void start(){
        UnigeServerConnection unigeCon = new UnigeServerConnection(new UnigeServerConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {
                try {
                    String isbn, proprietario;
                    double lat,lon;
                    int numBook = risposta.getInt("number");
                    JSONArray books = risposta.getJSONArray("items");
                    if(numBook>5)   numBook =5;             /*Massimo caricane solo 5 */
                    ItemBook[] toCreate = new ItemBook[numBook];
                    for(int i = 0; i<numBook; i++){
                        isbn = books.getJSONObject(i).getString("isbn");
                        proprietario = books.getJSONObject(i).getString("proprietario");
                        lat = books.getJSONObject(i).getDouble("lat");
                        lon = books.getJSONObject(i).getDouble("lon");
                        toCreate[i] = new ItemBook(isbn,lat,lon,proprietario);
                    }




                }catch(Exception e){}
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public Map<String, String> getParams() {
                return null;
            }

            @Override
            public String getURL() {
                return UnigeServerConnection.URL+UnigeServerConnection.RICERCA;
            }
        });
    }
}
