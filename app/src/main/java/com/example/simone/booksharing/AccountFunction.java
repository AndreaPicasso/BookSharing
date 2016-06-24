package com.example.simone.booksharing;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Utente on 19/06/2016.
 */
public class AccountFunction {
    public static void RiempiLibriPrestati(final Context context, final ListView daRiempire){

        UnigeServerConnection unigeServerConnection=new UnigeServerConnection(new UnigeServerConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {
                try{
                    String isbn, richiedente,dataprestito,stato;

                    int numBook = risposta.getInt("number");
                    JSONArray books = risposta.getJSONArray("items");
                    ArrayList<BookSharedForAdapter> list=new ArrayList<BookSharedForAdapter>();

                    for(int i = 0; i<numBook; i++) {
                        isbn = books.getJSONObject(i).getString("isbn");
                        richiedente = books.getJSONObject(i).getString("richiedente");
                        stato = books.getJSONObject(i).getString("stato");
                        dataprestito= books.getJSONObject(i).getString("dataprestito");

                        list.add(new BookSharedForAdapter(stato, isbn, dataprestito, richiedente));





                    }
                    BookAdapterShared adapter=new BookAdapterShared(context,R.layout.list_item_book_shared,list);
                    daRiempire.setAdapter(adapter);
                    Log.e("conferma","conferma");

                }
                catch (Exception e){
                    Log.e("librirestati",""+e.getMessage().toString());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.e("libriprestati",""+error.getMessage().toString());
            }

            @Override
            public Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences login=context.getSharedPreferences("login", Context.MODE_PRIVATE);
                params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);
                params.put("email",login.getString("email","") );

                return params;
            }

            @Override
            public String getURL(){
                return UnigeServerConnection.URL+UnigeServerConnection.LIBRI_PRESTATI;
            }
        });
        unigeServerConnection.sendRequest(context);


    }
    public static void RiempiLibriInLettura(final Context context,final ListView daRiempire){
        UnigeServerConnection unigeServerConnection=new UnigeServerConnection(new UnigeServerConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {
                try{
                    String isbn, proprietario,dataprestito;

                    int numBook = risposta.getInt("number");
                    JSONArray books = risposta.getJSONArray("items");
                    ArrayList<BookReadingForAdapter> list=new ArrayList<BookReadingForAdapter>();

                    for(int i = 0; i<numBook; i++) {
                        isbn = books.getJSONObject(i).getString("isbn");
                        proprietario = books.getJSONObject(i).getString("proprietario");
                        dataprestito= books.getJSONObject(i).getString("dataprestito");

                        list.add(new BookReadingForAdapter(isbn,proprietario,dataprestito));
                        Log.e("provasi", "eccolo");



                    }
                    BookAdapterReading adapter=new BookAdapterReading(context,R.layout.list_item_book_reading,list);
                    daRiempire.setAdapter(adapter);
                    Log.e("conferma", "conferma");

                }
                catch (Exception e){
                    Log.e("librirestati",""+e.getMessage().toString());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("libriprestati",""+error.getMessage().toString());
            }

            @Override
            public Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences login=context.getSharedPreferences("login", Context.MODE_PRIVATE);
                params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);
                params.put("email",login.getString("email","") );
                return params;
            }

            @Override
            public String getURL(){
                return UnigeServerConnection.URL+UnigeServerConnection.LIBRI_IN_LETTURA;
            }
        });
        unigeServerConnection.sendRequest(context);
    }
}
