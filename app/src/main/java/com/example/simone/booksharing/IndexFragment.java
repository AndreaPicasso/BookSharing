package com.example.simone.booksharing;


import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/*
/!\ SE NON METTO ANDROID.APP.FRAGMENT NON FUNZIONA NIENTE
 */
public class IndexFragment extends android.app.Fragment
        implements View.OnClickListener {

    Button iscriviti_button,login_button;

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.login_button:{
                this.getActivity().findViewById(R.id.index_fragment).setBackgroundColor(Color.WHITE);
                getFragmentManager().beginTransaction().replace(R.id.index_fragment, new LoginFragment()).commit();

                break;
            }
            case R.id.iscriviti_button:{
                getFragmentManager().beginTransaction().replace(R.id.index_fragment, new RegistrationFragment()).commit();

                break;
            }
        }

    }

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

        login_button=(Button)view.findViewById(R.id.login_button);
        iscriviti_button=(Button)view.findViewById(R.id.iscriviti_button);
        login_button.setOnClickListener(this);
        iscriviti_button.setOnClickListener(this);

        return view;
    }
}
