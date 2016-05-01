package com.example.simone.booksharing;

import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by simone on 27/04/2016.
 */

public class ItemBook {


    //Campi libro prestato, li mettiamo?
    private String proprietario;
    private double lat,lon;
    private Date datacondivisione;  //Campo data?


    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getCopertinaLink() {
        return copertinaLink;
    }

    public void setCopertinaLink(String copertinaLink) {
        this.copertinaLink = copertinaLink;
    }

    public int getNumPag() {
        return numPag;
    }

    public void setNumPag(int numPag) {
        this.numPag = numPag;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Date getDatacondivisione() {
        return datacondivisione;
    }

    public void setDatacondivisione(Date datacondivisione) {
        this.datacondivisione = datacondivisione;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    private String ISBN;
    private String titolo;
    private String autore;
    private String genere;
    
    private String copertinaLink;
    private int numPag;


    //STATO? ENUM DI PRESTITO

    public ItemBook(String ISBN, Context context) {
        this.ISBN =ISBN;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.googleapis.com/books/v1/volumes?q=isbn:"
                + ISBN, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ris = new JSONObject(response);
                    JSONArray arr = ris.getJSONArray("items");
                    ris = arr.getJSONObject(0);
                    titolo= ris.getJSONObject("volumeInfo").getString("title");
                    numPag = ris.getJSONObject("volumeInfo").getInt("pageCount");
                    copertinaLink = ris.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");
                    autore = ris.getJSONObject("volumeInfo").getJSONArray("authors").getString(0);
                    genere =    ris.getJSONObject("volumeInfo").getJSONArray("categories").getString(0);

                }
                catch(Exception e){

                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }




    /*
  RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.googleapis.com/books/v1/volumes?q=isbn:"
                + isbn.getText(), new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject ris = new JSONObject(response);
                            JSONArray arr = ris.getJSONArray("items");
                            ris = arr.getJSONObject(0);
                            ris = ris.getJSONObject("volumeInfo");
                                    title.setText(ris.getString("title"));
                        }
                        catch(Exception e){

                            }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                title.setText("Nessuna connesione");
            }
        });


     */
}
