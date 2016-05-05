package com.example.simone.booksharing;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;


public class AccountFragment extends android.app.Fragment {
    public Button modifica;
    public EditText nome;
    public EditText cognome;
    public EditText sesso;
    public EditText genere;
    public EditText password;
    public Boolean flag;
    SharedPreferences pref;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref=this.getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);
        flag=pref.getBoolean("flag",true);

    }

    @Override
    public void onResume() {
        flag=pref.getBoolean("flag",true);
        super.onResume();
    }

    @Override
    public void onPause() {
        SharedPreferences.Editor et= pref.edit();
        et.putBoolean("flag",flag);

        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =null;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            view=inflater.inflate(R.layout.fragment_account_port,container,false);

        else
            view=inflater.inflate(R.layout.fragment_account_land,container,false);
        modifica=(Button)view.findViewById(R.id.modifica_button);


        nome=(EditText) view.findViewById(R.id.nome_et);
        cognome=(EditText) view.findViewById(R.id.cognome_et);
        sesso=(EditText) view.findViewById(R.id.sesso_et);
        genere=(EditText) view.findViewById(R.id.genere_pref_et);
        password=(EditText) view.findViewById(R.id.login_password_et);


        View.OnClickListener modif= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){

                    nome.setFocusable(true);
                    nome.setFocusableInTouchMode(true);
                    cognome.setFocusable(true);
                    cognome.setFocusableInTouchMode(true);
                    genere.setFocusable(true);
                    genere.setFocusableInTouchMode(true);
                    sesso.setFocusable(true);
                    sesso.setFocusableInTouchMode(true);
                    password.setFocusable(true);
                    password.setFocusableInTouchMode(true);
                    modifica.setText("Fatto");
                    flag=false;
                }
                else  if(!flag){
                    nome.setFocusable(false);
                    nome.setFocusableInTouchMode(false);
                    cognome.setFocusable(false);
                    cognome.setFocusableInTouchMode(false);
                    genere.setFocusable(false);
                    genere.setFocusableInTouchMode(false);
                    sesso.setFocusable(false);
                    sesso.setFocusableInTouchMode(false);
                    password.setFocusable(false);
                    password.setFocusableInTouchMode(false);

                    modifica.setText("Modifica");
                    flag=true;
                }

                Log.e("bottone", ""+modifica.getText());

            }
        };
        modifica.setOnClickListener(modif);

        return view;
    }
}
