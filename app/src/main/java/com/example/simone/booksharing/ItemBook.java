package com.example.simone.booksharing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * Created by simone on 27/04/2016.
 */

public class ItemBook {


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
    public void setDescription(String description){
        this.description=description;
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
    public String getDescription(){return description;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }


    public Bitmap getCopertina() {
        return copertina;
    }

    public void setCopertina(Bitmap copertina) {
        this.copertina = copertina;
    }

    public boolean getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disp) {
        this.disponibile = disp;
    }

    private String ISBN;
    private String titolo;
    private String autore;
    private String genere;
    private Bitmap copertina;
    public String description;
    private String copertinaLink;
    private int numPag;
    private boolean disponibile;

    private String proprietario;
    private double lat,lon;
    private Date datacondivisione;  //Campo data?

    public ItemBook(String isbn, double lat, double lon, String proprietario, boolean disp){
        this.ISBN = isbn;
        this.lat = lat;
        this.lon= lon;
        this.proprietario = proprietario;
        this.disponibile=disp;
    }


    //STATO? ENUM DI PRESTITO

    public ItemBook(String isbn, final Context context) {
        this.ISBN = isbn;
        Log.e("sad", ""+this.getISBN());
        GoogleBooksConnection con = new GoogleBooksConnection(new GoogleBooksConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {
                try {
                    JSONArray arr = risposta.getJSONArray("items");
                    risposta = arr.getJSONObject(0);
                    // /!\ NON E DETTO CHE CI SIANO TUTTE LE INFO SU GOOGLE

                    titolo = risposta.getJSONObject("volumeInfo").getString("title");
                    numPag = risposta.getJSONObject("volumeInfo").getInt("pageCount");
                    copertinaLink = risposta.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");
                    autore = risposta.getJSONObject("volumeInfo").getJSONArray("authors").getString(0);
                    genere = risposta.getJSONObject("volumeInfo").getJSONArray("categories").getString(0);
                    Log.e("dentro onrespo","");
                    if(risposta.has("description")){
                        Log.e("eccolo",""+risposta.getJSONObject("volumeInfo").getString("description"));
                        description=risposta.getJSONObject("volumeInfo").getString("description");

                    }


                }
                catch(Exception e){
                    Toast.makeText(context, "Exception", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "VolleyErrorS", Toast.LENGTH_LONG).show();

            }
            @Override
            public Map<String, String> getParams() {
                return null;
            }

            @Override
            public String getURL() {
                return GoogleBooksConnection.URL+GoogleBooksConnection.makeGoogleQuery("","",getISBN(),"");
            }
        });
        con.sendRequest(context);
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
