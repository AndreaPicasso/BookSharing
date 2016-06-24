package com.example.simone.booksharing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AccountFragment extends android.app.Fragment {
    public Button modifica;
    public  Button inserisci_libro;
    public EditText nome;
    public EditText cognome;
    public EditText sesso;
    public EditText genere;
    public EditText password;
    public EditText ISBN;
    public Boolean flag;
    public ListView libri_inlettura;
    public ListView libri_prestati;
    SharedPreferences pref;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        flag = pref.getBoolean("flag", true);
        Log.e("account fragment", "oncreate");
    }



    @Override
    public void onResume() {
        flag=pref.getBoolean("flag",true);
        Log.e("account fragment","onresume");
        super.onResume();
    }

    @Override
    public void onPause() {
        SharedPreferences.Editor et= pref.edit();
        et.putBoolean("flag",flag);
        Log.e("account fragment", "onpause");
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =null;
        Log.e("account fragment", "oncreateview");
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            view=inflater.inflate(R.layout.fragment_account_port,container,false);

        else
            view=inflater.inflate(R.layout.fragment_account_land,container,false);
        modifica=(Button)view.findViewById(R.id.modifica_button);
        ISBN= (EditText) view.findViewById(R.id.ISBN_et);
        inserisci_libro=(Button) view.findViewById(R.id.aggiungi_button);
        nome=(EditText) view.findViewById(R.id.nome_et);
        cognome=(EditText) view.findViewById(R.id.cognome_et);
        sesso=(EditText) view.findViewById(R.id.sesso_et);
        genere=(EditText) view.findViewById(R.id.genere_pref_et);
        password=(EditText) view.findViewById(R.id.login_password_et);
        libri_inlettura=(ListView) view.findViewById(R.id.lettura_list);
        libri_prestati=(ListView) view.findViewById(R.id.prestito_list);
        AccountFunction.RiempiLibriInLettura(view.getContext(),libri_inlettura);
        AccountFunction.RiempiLibriPrestati(view.getContext(),libri_prestati);

        UnigeServerConnection con = new UnigeServerConnection(new UnigeServerConnectionHandler() {
            @Override
            public void onResponse(JSONObject risposta) {

                try{
                    nome.setText(risposta.getString("nome"));
                    cognome.setText(risposta.getString("cognome"));
                    sesso.setText(risposta.getString("sesso"));
                    genere.setText(risposta.getString("genere"));


                }
                catch (Exception e){
                    Log.e("Erro",""+e.getMessage());
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public Map<String, String> getParams() {
                SharedPreferences login=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                Map<String,String> params = new HashMap<String, String>();

                params.put("email", login.getString("email",""));
                params.put("psw", login.getString("psw",""));
                params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);
                return params;
            }

            @Override
            public String getURL() {
                return UnigeServerConnection.URL+UnigeServerConnection.UTENTE;
            }
        });
        con.sendRequest(this.getActivity());



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
                    UnigeServerConnection connection=new UnigeServerConnection(new UnigeServerConnectionHandler() {
                        @Override
                        public void onResponse(JSONObject risposta) {
                            try {
                                if(risposta.has("ok")){
                                    Toast.makeText(password.getContext(), "Modifica dati avvenuta con successo!", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    Toast.makeText(password.getContext(), risposta.getString("error"),Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch(Exception e){
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                        @Override
                        public Map<String, String> getParams() {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("nome", nome.getText().toString());
                            SharedPreferences login=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

                            params.put("email", login.getString("email", ""));
                            params.put("psw", login.getString("psw",""));
                            params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);

                            params.put("cognome", cognome.getText().toString());
                            params.put("genere_pref", genere.getText().toString());
                            params.put("password", password.getText().toString());

                            params.put("sesso", sesso.getText().toString());
                            return params;

                        }

                        @Override
                        public String getURL() {
                            return UnigeServerConnection.URL+UnigeServerConnection.MODIFICA_DATI;
                        }
                    });
                    connection.sendRequest(getActivity());

                    flag=true;
                }

                Log.e("bottone", ""+modifica.getText());

            }
        };
        modifica.setOnClickListener(modif);
        View.OnClickListener ins_libro= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ISBN.getText().toString().equals(""))
                {
                    Toast.makeText(ISBN.getContext(), "Nessun libro trovato", Toast.LENGTH_LONG).show();
                    return;
                }
                GoogleBooksConnection connection= new GoogleBooksConnection(new GoogleBooksConnectionHandler() {
                    @Override
                    public void onResponse(JSONObject risposta) {
                        try {
                            if(risposta.getInt("totalItems")>0) {
                                JSONArray arr = risposta.getJSONArray("items");
                                risposta = arr.getJSONObject(0).getJSONObject("volumeInfo");
                                SharedPreferences pref = getActivity().getSharedPreferences("home", Context.MODE_PRIVATE);
                                SharedPreferences.Editor et = pref.edit();

                                et.putString("ISBN", ISBN.getText().toString()).commit();
                                et.putString("titoloBookToShow", risposta.getString("title")).commit();


                                if (risposta.has("imageLinks"))
                                    et.putString("copertinaBookToShow", risposta.getJSONObject("imageLinks").getString("thumbnail")).commit();
                                if (risposta.has("categories"))
                                    et.putString("genereBookToShow", risposta.getJSONArray("categories").getString(0)).commit();
                                if (risposta.has("authors"))
                                    et.putString("autoreBookToShow", risposta.getJSONArray("authors").getString(0)).commit();
                                getFragmentManager().beginTransaction().replace(R.id.details_fragment, new InsertBookFragment()).addToBackStack(null).commit();
                            }
                            else
                                Toast.makeText(ISBN.getContext(), "Nessun libro trovato", Toast.LENGTH_LONG).show();

                        }
                        catch(Exception e){
                            Log.e("Eccezione lista libri",""+e.getMessage());
                        }

                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                    @Override
                    public Map<String, String> getParams() {
                        return null;
                    }

                    @Override
                    public String getURL() {
                        Log.e("isbn",""+ISBN.getText().toString());
                        return GoogleBooksConnection.URL + GoogleBooksConnection.makeGoogleQuery("", "",ISBN.getText().toString() , "");
                    }
                });
                connection.sendRequest(getActivity());
            }
        };
        inserisci_libro.setOnClickListener(ins_libro);

        return view;
    }
}
