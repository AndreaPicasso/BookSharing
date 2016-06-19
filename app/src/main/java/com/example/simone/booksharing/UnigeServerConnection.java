package com.example.simone.booksharing;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface UnigeServerConnectionHandler{
    void onResponse(JSONObject risposta);
    void onErrorResponse(VolleyError error);
    Map<String,String> getParams();
    String getURL();

}


public class UnigeServerConnection {
    public static final String URL = "http://webdev.dibris.unige.it/~S3940125/ANDROID_ENGINE/";

    public static final String LOGIN = "query_login.php";
    public static final String REGISTRATION = "query_registration.php";
    public static final String PSW_DIMENTICATA = "query_psw_dimenticata.php";
    public static final String RICERCA = "query_ricerca.php";
    public static final String UTENTE = "query_utente.php";
    public static final String INSERISCI_LIBRO = "query_aggiungi_libro.php";
    public static final String MODIFICA_DATI = "query_modifica_dati.php";
    public static final String GET_VALUTAZIONE_UTENTE = "query_get_valutazione_utente.php";
    public static final String RICHIESTA_PRESTITO = "query_richiesta_prestito.php";




    public static final String PSW_ACCESSO = "Azet325K54fA32w";



    public UnigeServerConnectionHandler helper = null;

    public UnigeServerConnection(UnigeServerConnectionHandler handler){
        helper = handler;
    }



     /*Implementa la classe dove si vuole utilizzare la connessione e passa this */
    public void sendRequest(Context context){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, helper.getURL(), new Response.Listener<String>(){

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





    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "[A-Z0-9._]+@[A-Z0-9.]+.[A-Z0-9]+";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


}
