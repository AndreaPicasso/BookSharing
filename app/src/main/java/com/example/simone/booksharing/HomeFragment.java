package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends android.app.Fragment {
    public Button cerca;
    public TwoWayView slider;
    public ItemBook l1;
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        cerca=(Button) view.findViewById(R.id.cerca_button);
        slider=(TwoWayView) view.findViewById(R.id.slider_lw);
        String[] linkImmagini= {"http://www.fantascienza.com/catalogo/imgbank/cover/UV039.jpg","http://www.mondadoristore.it/img/Il-vecchio-e-il-mare-Ernest-Hemingway/ea978880461312/BL/BL/01/NZO/?tit=Il+vecchio+e+il+mare&aut=Ernest+Hemingway"};
        l1=new ItemBook("9788858754405", this.getActivity());
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




        View.OnClickListener cerca_l= new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               getFragmentManager().beginTransaction().replace(R.id.home_fragment, new BookFragment()).addToBackStack(null).commit();
            }
        };
        cerca.setOnClickListener(cerca_l);

        return view;

    }
}
