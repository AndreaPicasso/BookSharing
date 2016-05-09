package com.example.simone.booksharing;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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
        DownloadImg downloadImg= new DownloadImg(listaLink,context,slider,listaLibri);

        downloadImg.execute();


    }
    public void Riempi(final Map<String, String> googleSearchParam){
        GoogleBooksConnection con= new GoogleBooksConnection(new GoogleBooksConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {

                try {
                    JSONArray arr = risposta.getJSONArray("items");
                    risposta = arr.getJSONObject(0).getJSONObject("volumeInfo");
                    // /!\ NON E DETTO CHE CI SIANO TUTTE LE INFO SU GOOGLE

                    listaLibri.get(cont).setTitolo(risposta.getString("title"));
                    if(risposta.has("imageLinks"))
                        listaLibri.get(cont).setCopertinaLink(risposta.getJSONObject("imageLinks").getString("thumbnail"));
                    if(risposta.has("pageCount"))
                        listaLibri.get(cont).setNumPag(risposta.getInt("pageCount"));
                    if(risposta.has("categories"))
                        listaLibri.get(cont).setGenere(risposta.getJSONArray("categories").getString(0));
                    if(risposta.has("authors"))
                        listaLibri.get(cont).setAutore(risposta.getJSONArray("authors").getString(0));
                }
                catch(Exception e){
                    Log.e("Eccezione lista libri",""+e.getMessage());
                }

                if(cont<max-1){
                    cont++;
                    Riempi(googleSearchParam);
                }
                else if(cont==max-1){
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
                if(googleSearchParam!=null) {
                    String tit, aut, gen;
                    if(googleSearchParam.get("titolo")!=null) tit =googleSearchParam.get("titolo");
                    else tit="";
                    if(googleSearchParam.get("autore")!=null) aut =googleSearchParam.get("autore");
                    else aut="";
                    if(googleSearchParam.get("genere")!=null) gen =googleSearchParam.get("genere");
                    else gen="";
                    return GoogleBooksConnection.URL + GoogleBooksConnection.makeGoogleQuery(tit, aut, listaLibri.get(cont).getISBN(), gen);
                }
                else
                    return GoogleBooksConnection.URL+GoogleBooksConnection.makeGoogleQuery("","",listaLibri.get(cont).getISBN(),"");

            }
        });
        con.sendRequest(context);
    }

}
