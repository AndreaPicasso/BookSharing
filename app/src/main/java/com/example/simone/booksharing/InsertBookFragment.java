package com.example.simone.booksharing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class InsertBookFragment extends android.app.Fragment {
    public Button inserisci;
    public TextView titolo;
    public TextView autore;
    public TextView genere;

    public RatingBar rating;

    public ImageView copertina;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences pref= getActivity().getSharedPreferences("home", Context.MODE_PRIVATE);

        View view=null;

        view=inflater.inflate(R.layout.fragment_insert_book_port,container,false);
        /*
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            view=inflater.inflate(R.layout.fragment_insert_book_port,container,false);

        else
            view=inflater.inflate(R.layout.fragment_insert_book_land,container,false);
    */
        inserisci=(Button) view.findViewById(R.id.insert_book_inserisci_button);
        titolo=(TextView) view.findViewById(R.id.titolo_tw);
        autore=(TextView) view.findViewById(R.id.autore_tw);
        genere=(TextView) view.findViewById(R.id.genere_tw);
        copertina=(ImageView) view.findViewById(R.id.imageView);

        titolo.setText(pref.getString("titoloBookToShow",""));
        autore.setText(pref.getString("autoreBookToShow",""));
        genere.setText(pref.getString("genereBookToShow", ""));
        String url=pref.getString("copertinaBookToShow", "");
        View.OnClickListener ins= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnigeServerConnection connection= new UnigeServerConnection(new UnigeServerConnectionHandler() {
                    @Override
                    public void onResponse(JSONObject risposta) {
                        try {
                            if(risposta.has("ok")){
                                Toast.makeText(autore.getContext(), "Il libro è stato inserito!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(titolo.getContext(), Home.class));

                            }
                            else{
                                Toast.makeText(autore.getContext(), risposta.getString("risultato"),Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch(Exception e){
                            Log.e("insertBook","Exce"+e.getMessage());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("insertBook","errorResponse"+error.getMessage());
                    }

                    @Override
                    public Map<String, String> getParams() {
                       Geolocation geolocation=new Geolocation();
                        geolocation.getLocation(autore.getContext());
                        Map<String,String> params = new HashMap<String, String>();


                        SharedPreferences pref= getActivity().getSharedPreferences("home", Context.MODE_PRIVATE);
                        SharedPreferences login= getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                        params.put("isbn", pref.getString("ISBN", ""));

                        params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);
                        params.put("proprietario",login.getString("email", ""));

                        params.put("lat", geolocation.lat.toString());
                        params.put("lon", geolocation.lng.toString());
                        Log.e("insertBook", ""+geolocation.lat.toString()+"lon"+geolocation.lng.toString());



                        return params;

                    }

                    @Override
                    public String getURL() {
                        return UnigeServerConnection.URL + UnigeServerConnection.INSERISCI_LIBRO;
                    }
                });
                connection.sendRequest(getActivity());

            }
        };

        inserisci.setOnClickListener(ins);
        DownloadSingleImg img = new DownloadSingleImg(url,this.getActivity(),copertina);
        img.execute();




        return view;

    }
}
