package com.example.simone.booksharing;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
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
        SharedPreferences pref= getActivity().getSharedPreferences("index",Context.MODE_PRIVATE);
        SharedPreferences.Editor et= pref.edit();


        switch (v.getId()){
            case R.id.login_button:{
                et.putInt("flag",1).commit();
                getFragmentManager().beginTransaction().replace(R.id.index_fragment, new LoginFragment()).addToBackStack(null).commit();

                break;
            }
            case R.id.iscriviti_button:{
                et.putInt("flag",2).commit();
                getFragmentManager().beginTransaction().replace(R.id.index_fragment, new RegistrationFragment()).addToBackStack(null).commit();

                break;
            }
        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view=null;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            view=inflater.inflate(R.layout.fragment_index_port,container,false);

        else
            view=inflater.inflate(R.layout.fragment_index_land,container,false);

        login_button=(Button)view.findViewById(R.id.login_button);
        iscriviti_button=(Button)view.findViewById(R.id.iscriviti_button);
        login_button.setOnClickListener(this);
        iscriviti_button.setOnClickListener(this);
       /* PROBLEMI CON SENSORE DA SISTEMARE
       OrientationEventListener orientationEventListener= new OrientationEventListener(getActivity(), SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {

                switch (orientation){
                    case Configuration.ORIENTATION_PORTRAIT:
                        getView().inflate(getActivity(),R.layout.fragment_index_port,container);
                        Log.e("index", "orientationportraittttttttttttt");
                        break;
                    case Configuration.ORIENTATION_LANDSCAPE:
                        getView().inflate(getActivity(),R.layout.fragment_index_land,container);
                        Log.e("index", "orientatiolandscapeeeeeeeeeeee");
                        break;
                }
            }
        };

        orientationEventListener.enable();*/


        return view;
    }
}
