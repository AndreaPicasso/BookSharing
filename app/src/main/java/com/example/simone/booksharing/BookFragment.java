package com.example.simone.booksharing;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;


public class BookFragment extends android.app.Fragment  {
    public Button prenota;
    public TextView titolo;
    public TextView autore;
    public TextView genere,stato,luogo;
    public RatingBar rating;
    public ImageView copertina;
    public TextView description;
    public ScrollView parent;
    public ScrollView child;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences pref= getActivity().getSharedPreferences("home", Context.MODE_PRIVATE);
        View view=null;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            view=inflater.inflate(R.layout.fragment_book_port,container,false);

        else
            view=inflater.inflate(R.layout.fragment_book_land,container,false);

        prenota=(Button) view.findViewById(R.id.prenota_button);
        titolo=(TextView) view.findViewById(R.id.titolo_tw);
        autore=(TextView) view.findViewById(R.id.autore_tw);
        genere=(TextView) view.findViewById(R.id.genere_tw);
        description=(TextView) view.findViewById(R.id.description_tw);
        copertina=(ImageView) view.findViewById(R.id.imageView);
        stato = (TextView) view.findViewById(R.id.stato_tw);
        luogo = (TextView) view.findViewById(R.id.luogo_tw);
        child=(ScrollView) view.findViewById(R.id.child_scroll_view);
        parent=(ScrollView) view.findViewById(R.id.parent_scroll_view);
        parent.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                v.findViewById(R.id.child_scroll_view).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        child.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        description.setText(pref.getString("description", ""));
        SharedPreferences.Editor et=pref.edit();
        et.putString("description","").commit();
        titolo.setText(pref.getString("titoloBookToShow", ""));
        autore.setText("Autore: "+pref.getString("autoreBookToShow",""));
        genere.setText("Genere: "+pref.getString("genereBookToShow", ""));
        String url=pref.getString("copertinaBookToShow", "");
        int state = pref.getInt("disponibileBookToShow",-1);
        if(state == 0){
            stato.setText("Stato: Non disponibile");
            prenota.setText("Avvisami appena disponibile");
        }
        else if(state == 1){
            stato.setText("Stato: Disponibile");
            prenota.setText("Prenota");
        }
        else if(state == -1) stato.setText("Stato: Err");


        DownloadSingleImg img = new DownloadSingleImg(url,this.getActivity(),copertina);
        img.execute();
        float lat1 = pref.getFloat("latBookToShow", 0);

        final float lat = lat1;
        final float lon = pref.getFloat("lonBookToShow", 0);



        GoogleBooksConnection con= new GoogleBooksConnection(new GoogleBooksConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {

                try {
                    luogo.setText("Luogo: "+risposta.getJSONArray("results").getJSONObject(0).getString("formatted_address"));
                }
                catch(Exception e){
                    Log.e("Eccezione posizione",""+e.getMessage());
                }

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
                    return "https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon;

            }
        });
        con.sendRequest(this.getActivity());




        return view;

    }


}
