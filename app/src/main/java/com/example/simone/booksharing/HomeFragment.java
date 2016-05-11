package com.example.simone.booksharing;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends android.app.Fragment implements View.OnClickListener {
    public Button cerca;
    SharedPreferences pref1;
    public TwoWayView slider;

    private EditText titolo, autore,genere,isbn;
    private CheckBox disponibile;
    private SeekBar raggio;
    private TextView raggioDisp;

    private HashMap<Integer, ItemBook> sliderMap;

    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pref1=this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor et= pref1.edit();
        et.putInt("n",0);
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        cerca=(Button) view.findViewById(R.id.cerca_button);
        slider=(TwoWayView) view.findViewById(R.id.slider_lw);
        genere = (EditText) view.findViewById(R.id.genere_et);
        isbn = (EditText) view.findViewById(R.id.isbn_et);
        titolo = (EditText) view.findViewById(R.id.titolo_et);
        autore = (EditText) view.findViewById(R.id.autore_et);
        disponibile = (CheckBox) view.findViewById(R.id.disponibile_chk);
        raggio = (SeekBar) view.findViewById(R.id.raggio_seek);
        raggioDisp =(TextView) view.findViewById(R.id.raggio_tw);
        sliderMap = new HashMap<>();
        raggio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 2)
                    seekBar.setProgress(2);
                raggioDisp.setText(String.valueOf(seekBar.getProgress()) + " Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        cerca.setOnClickListener(this);

        HomeCreationSlider homeCreationSlider= new HomeCreationSlider(this.getActivity(),slider,sliderMap);
        homeCreationSlider.start(null, null);
        slider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemBook choose = sliderMap.get(position);
                SharedPreferences pref = getActivity().getSharedPreferences("home",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("titoloBookToShow", choose.getTitolo().toString());
                editor.putString("autoreBookToShow", choose.getAutore().toString());
                editor.putString("copertinaBookToShow", choose.getCopertinaLink().toString());
                editor.putString("genereBookToShow", choose.getGenere().toString());
                editor.putString("proprietarioBookToShow", choose.getProprietario().toString());
                editor.putString("isbnBookToShow", choose.getISBN().toString());
                editor.putFloat("latBookToShow", ((float) choose.getLat()));
                editor.putFloat("lonBookToShow", ((float) choose.getLon()));
                editor.putInt("numPagBookToShow", choose.getNumPag());
                int disp=0;
                if(choose.getDisponibile()) disp=1;
                editor.putInt("disponibileBookToShow", disp);

                editor.commit();
                getFragmentManager().beginTransaction().replace(R.id.home_fragment, new BookFragment()).addToBackStack(null).commit();

            }
        });
        return view;

    }

    @Override
    public void onClick(View v) {
        //getFragmentManager().beginTransaction().replace(R.id.home_fragment, new BookFragment()).addToBackStack(null).commit();

        Map<String,String> unigeParams = new HashMap<String, String>();
        boolean googleOk=false, unigeOk=false;
        if(!isbn.getText().toString().equals("")){
            unigeParams.put("isbn",isbn.getText().toString()); unigeOk=true;
        }
        if(disponibile.isChecked()){
            unigeParams.put("disponibili","true"); unigeOk=true;
        }
        if(raggio.getProgress()>2) {
            /* trova posizione */
            unigeOk=true;
            double myLat =5;
            double myLon = 6;

            // /!\ NON CREDO SIA COSI SEMPLICE
            double temp = myLat-raggio.getProgress()/2;
            unigeParams.put("minLat",Double.toString(temp));
            temp = myLat-raggio.getProgress()+2;
            unigeParams.put("maxLat",Double.toString(temp));
            temp = myLon-raggio.getProgress()-2;
            unigeParams.put("minLon",Double.toString(temp));
            temp = myLon-raggio.getProgress()-2;
            unigeParams.put("maxLon",Double.toString(temp));
            }
        Map<String,String> googleParams = new HashMap<String, String>();
        if(!autore.getText().toString().equals("")){
            googleParams.put("autore",autore.getText().toString()); googleOk=true;
        }
        if(!genere.getText().toString().equals("")){
            googleParams.put("genere",genere.getText().toString()); googleOk=true;
        }
        if(!titolo.getText().toString().equals("")){
            googleParams.put("titolo",titolo.getText().toString()); googleOk=true;
        }

        if(!unigeOk) unigeParams=null;


        if(!googleOk) googleParams=null;
        if(googleOk || unigeOk) {
            //slider.removeAllViews();
            HomeCreationSlider homeCreationSlider = new HomeCreationSlider(this.getActivity(), slider, sliderMap);
            homeCreationSlider.start(unigeParams, googleParams);
        }





    }
}
