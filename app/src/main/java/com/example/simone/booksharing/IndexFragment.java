package com.example.simone.booksharing;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*


public class FragmentProva extends android.app.Fragment {

    Button opzione1;
    @Override


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=null;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            view=inflater.inflate(R.layout.port,container,false);

        else
            view=inflater.inflate(R.layout.land,container,false);

        opzione1=(Button)view.findViewById(R.id.button2);
        return view;
    }


}

 */





public class IndexFragment extends android.app.Fragment {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=null;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            view=inflater.inflate(R.layout.fragment_index_port,container,false);

        else
            view=inflater.inflate(R.layout.fragment_index_land,container,false);

        return view;
    }
}
