package com.example.simone.booksharing;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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


public class InsertBookFragment extends android.app.Fragment {
    public Button inserisci;
    public TextView titolo;
    public TextView autore;
    public TextView genere,stato;

    public RatingBar rating;

    public ImageView copertina;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences pref= getActivity().getSharedPreferences("home", Context.MODE_PRIVATE);
        View view=null;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            view=inflater.inflate(R.layout.fragment_insert_book_port,container,false);

        else
            view=inflater.inflate(R.layout.fragment_book_land,container,false);
        inserisci=(Button) view.findViewById(R.id.Inserisci_button);
        titolo=(TextView) view.findViewById(R.id.titolo_tw);
        autore=(TextView) view.findViewById(R.id.autore_tw);
        genere=(TextView) view.findViewById(R.id.genere_tw);
        copertina=(ImageView) view.findViewById(R.id.imageView);
        stato = (TextView) view.findViewById(R.id.stato_tw);
        titolo.setText(pref.getString("titoloBookToShow",""));
        autore.setText(pref.getString("autoreBookToShow",""));
        genere.setText(pref.getString("genereBookToShow", ""));
        String url=pref.getString("copertinaBookToShow", "");
        int state = pref.getInt("disponibileBookToShow",-1);
        if(state == 0) stato.setText("Stato: Non disponibile");
        else if(state == 1) stato.setText("Stato: Disponibile");
        else if(state == -1) stato.setText("Stato: Err");


        DownloadSingleImg img = new DownloadSingleImg(url,this.getActivity(),copertina);
        img.execute();




        return view;

    }
}
