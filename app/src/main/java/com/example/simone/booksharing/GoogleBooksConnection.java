package com.example.simone.booksharing;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by simone on 04/05/2016.
 */

interface GoogleBooksConnectionHandler{
    void onResponse(JSONObject risposta);
    void onErrorResponse(VolleyError error);
    Map<String,String> getParams();
    String getURL();

}


public class GoogleBooksConnection {
    public static final String URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public static final String APIKey ="AIzaSyBRCubp0RFQ_EgffojAOC8jf7fxjMPGXHg";
    public static final String APIKey2 ="AIzaSyDH-V4-HfH8JSzU26QGD2jozQA9Onb5g44";

    public GoogleBooksConnectionHandler helper = null;



    public GoogleBooksConnection(GoogleBooksConnectionHandler handler){
        helper = handler;
    }



    /*Implementa la classe dove si vuole utilizzare la connessione e passa this */
    public void sendRequest(Context context){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, helper.getURL()+"&key="+APIKey, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    helper.onResponse(new JSONObject(response));
                }
                catch(Exception e){
                }
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                helper.onErrorResponse(error);
            }
        }){ @Override
            protected Map<String,String> getParams(){
            return helper.getParams();
        }
        };
        queue.add(stringRequest);
    }




    public static String makeGoogleQuery(String titolo, String autore, String isbn, String genere){
        StringBuffer ris=new StringBuffer();
        if(!isbn.equals("")) ris.append("isbn:"+isbn+"+");
        if(!genere.equals("")) ris.append("subject:"+genere+"+");
        if(!titolo.equals("")) ris.append("intitle:"+titolo+"+");
        if(!autore.equals("")) ris.append("inauthor:"+autore+"+");

        return ris.toString();
    }

}
