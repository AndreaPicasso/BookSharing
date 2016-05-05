package com.example.simone.booksharing;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public ItemBook l1;

    private EditText titolo, autore,genere,isbn;
    private CheckBox disponibile;
    private SeekBar raggio;
    private TextView raggioDisp;

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


        // /!\  MEGLIO METTERE QUESTO PRIMA, CHE TANTO PARTE E SI FA I CAZZI SUOI DIREI

       l1=new ItemBook("9788858754405", this.getActivity());
        String[] linkImmagini= {"http://books.google.it/books/content?id=RDVS2LeLzhQC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api","https://upload.wikimedia.org/wikipedia/commons/a/ab/JoyceUlysses2.jpg","https://ilcentrodellessere.files.wordpress.com/2011/05/il-gabbiano-jonathan-livingstone_fronte.jpg","http://alessandria.bookrepublic.it/api/books/9788858600795/cover","http://www.fantascienza.com/catalogo/imgbank/cover/UV039.jpg","http://www.mondadoristore.it/img/Il-vecchio-e-il-mare-Ernest-Hemingway/ea978880461312/BL/BL/01/NZO/?tit=Il+vecchio+e+il+mare&aut=Ernest+Hemingway"};

        ArrayList<HashMap<String,String>> items= new ArrayList<>();
        DownloadImg img= new DownloadImg(linkImmagini,this.getActivity(),slider);
        img.execute();




        //DownloadImg img1= new DownloadImg("http://www.mondadoristore.it/img/Il-vecchio-e-il-mare-Ernest-Hemingway/ea978880461312/BL/BL/01/NZO/?tit=Il+vecchio+e+il+mare&aut=Ernest+Hemingway");
       // img1.execute();
        //ownloadImg img= new DownloadImg("http://www.fantascienza.com/catalogo/imgbank/cover/UV039.jpg");
        //img.execute();
       // ArrayList<Bitmap> l= new ArrayList<>();
        //while(img.getImage()==null || img1.getImage()==null){

        //}
        //l.add(img1.getImage());
        //l.add(img.getImage());
      //  Log.e("url", ""+l.size());
        //ArrayAdapter<Bitmap> m= new ArrayAdapter<Bitmap>(this.getActivity(),R.layout.list_item_img_book,l);
      //  MyAdapter m=new MyAdapter(this.getActivity(),R.layout.list_item_img_book,l);
       // slider.setAdapter(m);
        /*HashMap<String,String> map= new HashMap<>();
        l1.setCopertinaLink("https://i.ytimg.com/vi/oJxtQ9u8ISs/hqdefault.jpg");
        l1.setTitolo("godfjgildfgl");
        l1.setGenere("titolo");
        map.put("titolo", l1.getTitolo());
        map.put("copertina", l1.getCopertinaLink());

        items.add(map);
        int resource=R.layout.list_item_book ;
        String[] from={"titolo","copertina"};
        int[] to={R.id.titolo_tw, R.id.copertina_iw,};
        SimpleAdapter adapter= new SimpleAdapter(this.getActivity(),items,resource,from,to);
        slider.setAdapter(adapter);*/

        return view;

    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.home_fragment, new BookFragment()).addToBackStack(null).commit();
        UnigeServerConnection unigeCon = new UnigeServerConnection(new UnigeServerConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                if(!isbn.getText().equals(""))params.put("isbn",isbn.getText().toString());
                if(disponibile.isChecked()) params.put("disponibili","true");

                if(true) {
                    /* trova posizione */
                    double myLat =5;
                    double myLon = 6;

                }
                return params;
            }

            @Override
            public String getURL() {
                return UnigeServerConnection.URL+UnigeServerConnection.RICERCA;
            }
        });


    }
}
