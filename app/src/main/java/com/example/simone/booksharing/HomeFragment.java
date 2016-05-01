package com.example.simone.booksharing;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        l1=new ItemBook("9788804488255", this.getActivity());
        ArrayList<HashMap<String,String>> items= new ArrayList<>();
        HashMap<String,String> map= new HashMap<>();
        l1.setCopertinaLink("link");
        l1.setTitolo("godfjgildfgl");
        map.put("titolo",l1.getTitolo());
        map.put("copertina", l1.getCopertinaLink());
        items.add(map);
        int resource= R.layout.list_item_book;
        String[] from={ "titolo", "copertina"};
        int[] to={R.id.titolo_tw, R.id.copertina_iw};
        SimpleAdapter adapter= new SimpleAdapter(this.getActivity(),items,resource,from,to);
        slider.setAdapter(adapter);




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
