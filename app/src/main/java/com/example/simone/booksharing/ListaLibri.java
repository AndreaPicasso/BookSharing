package com.example.simone.booksharing;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Utente on 05/05/2016.
 */
public class ListaLibri  {

    private ArrayList<ItemBook> listaLibri;
    private Integer max;
    private Integer cont;
    private Context context;
    private TwoWayView slider;

    ListaLibri(ArrayList<ItemBook> l, Context context, TwoWayView s){
        this.listaLibri=l;
        this.cont=0;
        this.slider=s;
        this.context=context;
        this.max=this.listaLibri.size();
    }

    public void riempiSlider(){
        String[] listaLink= new String[listaLibri.size()];
        for(int i=0; i<listaLink.length; i++){
            listaLink[i]=listaLibri.get(i).getCopertinaLink();
        }
        DownloadImg downloadImg= new DownloadImg(listaLink,context,slider);
        downloadImg.execute();
    }
    public void Riempi(){
        GoogleBooksConnection con= new GoogleBooksConnection(new GoogleBooksConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {

                try {
                    JSONArray arr = risposta.getJSONArray("items");
                    risposta = arr.getJSONObject(0);
                    // /!\ NON E DETTO CHE CI SIANO TUTTE LE INFO SU GOOGLE

                    listaLibri.get(cont).setTitolo(risposta.getJSONObject("volumeInfo").getString("title"));
                    listaLibri.get(cont).setCopertinaLink(risposta.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail"));
                    listaLibri.get(cont).setNumPag(risposta.getJSONObject("volumeInfo").getInt("pageCount"));
                    listaLibri.get(cont).setGenere(risposta.getJSONObject("volumeInfo").getJSONArray("categories").getString(0));
                    listaLibri.get(cont).setAutore(risposta.getJSONObject("volumeInfo").getJSONArray("authors").getString(0));

                }
                catch(Exception e){
                    Toast.makeText(context, "Exception", Toast.LENGTH_LONG).show();
                }

                if(cont<max){
                    cont++;
                    Riempi();
                }
                else if(cont==max){
                    riempiSlider();
                }



            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "VolleyErrorSInListaLibri", Toast.LENGTH_LONG).show();
            }

            @Override
            public Map<String, String> getParams() {
                return null;
            }

            @Override
            public String getURL() {
                return GoogleBooksConnection.URL+GoogleBooksConnection.makeGoogleQuery("","",listaLibri.get(cont).getISBN(),"");
            }
        });
        con.sendRequest(context);
    }

}
