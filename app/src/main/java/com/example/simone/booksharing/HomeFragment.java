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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    public Button indietro;

    private EditText titolo, autore,genere,isbn;
    private CheckBox disponibile;
    private SeekBar raggio;
    private TextView raggioDisp;
    public ProgressBar progressBar;

    private HashMap<Integer, ItemBook> sliderMap;

    public void onCreate(Bundle savedInstanceState) {
        CheckConnection.isOnline(getActivity());


        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pref1=this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor et= pref1.edit();
        et.putInt("n",0);
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        indietro=(Button) view.findViewById(R.id.return_button);
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
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slider.setVisibility(View.INVISIBLE);
                indietro.setClickable(false);
                progressBar.setVisibility(View.VISIBLE);
                HomeCreationSlider homeCreationSlider= new HomeCreationSlider(v.getContext(),slider,sliderMap,progressBar);
                homeCreationSlider.start(null, null,indietro);
                indietro.setVisibility(View.INVISIBLE);

                genere.setText("");
                isbn.setText("");
                titolo.setText("");
                autore.setText("");
                disponibile.setChecked(true);
                raggio.setProgress(0);
                raggioDisp.setText("");

            }
        });
        HomeCreationSlider homeCreationSlider= new HomeCreationSlider(this.getActivity(),slider,sliderMap,progressBar);
        homeCreationSlider.start(null, null,indietro);
        slider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemBook choose = sliderMap.get(position);
                SharedPreferences pref = getActivity().getSharedPreferences("home",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if(choose.getDescription()!=null){
                    editor.putString("description",choose.getDescription().toString()).commit();
                }

                editor.putString("titoloBookToShow", choose.getTitolo().toString());
                editor.putString("autoreBookToShow", choose.getAutore().toString());
                if(choose.getCopertinaLink()!=null) {
                    editor.putString("copertinaBookToShow", choose.getCopertinaLink().toString());
                }
                else{
                    editor.putString("copertinaBookToShow",null);
                }
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
        InputMethodManager mgr = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
            Geolocation loc = new Geolocation();
            loc.getLocation(getActivity());
            loc.boundingCoordinates(raggio.getProgress());

            unigeParams.put("minLat", Double.toString(loc.minLat));
            Log.e("geol", "" + Double.toString(loc.minLat));
            unigeParams.put("maxLat", Double.toString(loc.maxLat));
            Log.e("geol", "" + Double.toString(loc.minLon));
            unigeParams.put("minLon", Double.toString(loc.minLon));
            Log.e("geol", "" + Double.toString(loc.maxLat));
            unigeParams.put("maxLon", Double.toString(loc.maxLon));
            Log.e("geol", "" + Double.toString(loc.maxLon));

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
            slider.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            HomeCreationSlider homeCreationSlider = new HomeCreationSlider(this.getActivity(), slider, sliderMap,progressBar);
            homeCreationSlider.start(unigeParams, googleParams,indietro);
            indietro.setVisibility(View.VISIBLE);
            indietro.setClickable(true);


        }
    }
}
