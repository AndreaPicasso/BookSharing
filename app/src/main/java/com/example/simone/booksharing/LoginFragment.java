package com.example.simone.booksharing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends android.app.Fragment implements View.OnClickListener {
    private Button accedi;
    private EditText email, password;
    private TextView pswDim;

    public void onResume(){
        super.onResume();
        /*
        SharedPreferences login= getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
        Log.e("login", "onResume:"+login.getString("email",""));
        if(!login.getString("email","").equals("")) {
            Log.e("login", "emailpresente");
            startActivity(new Intent(getActivity(), Home.class));
        }
        else{
            Log.e("login", "emailNONpresente");
        }
        */
    }

    public void onCreate(Bundle savedInstanceState) {
        CheckConnection.isOnline(this.getActivity().getApplicationContext());
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_login,container,false);
        accedi=(Button)view.findViewById(R.id.login_accedi_button);
        email = (EditText) view.findViewById(R.id.login_email_et);
        password = (EditText) view.findViewById(R.id.login_password_et);
        pswDim = (TextView) view.findViewById(R.id.pswdimenticata_tw);


        accedi.setOnClickListener(this);
        pswDim.setOnClickListener(this);
       return view;

    }

    @Override
    public void onClick(View v) {



        if(v.getId() == accedi.getId()) {
            boolean ok = true;
            if (email.getText().toString().equals("")) {
                ok = false;
                Toast.makeText(this.getActivity(), "Campo email vuoto", Toast.LENGTH_SHORT).show();
            }
            if (!UnigeServerConnection.isEmailValid(email.getText().toString())) {
                ok = false;
                Toast.makeText(this.getActivity(), "Email non valida", Toast.LENGTH_SHORT).show();
            }
            if (password.getText().toString().equals("")) {
                ok = false;
                Toast.makeText(this.getActivity(), "Campo password vuoto", Toast.LENGTH_SHORT).show();
            }
            if (!ok) return;
            UnigeServerConnection connection = new UnigeServerConnection(new UnigeServerConnectionHandler() {
                @Override
                public void onResponse(JSONObject risposta) {
                    try{
                        if (risposta.getString("risultato").equals("ok")) {
                            SharedPreferences login=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor et=login.edit();
                            et.putString("email",""+email.getText()).commit();
                            //et.putString("psw",""+password.getText()).commit();


                            startActivity(new Intent(email.getContext(), Home.class));


                        } else
                            Toast.makeText(email.getContext(), "Utente non riconosciuto.", Toast.LENGTH_SHORT).show();
                    }catch(Exception e){

                    }
                }
                @Override
                public void onErrorResponse(VolleyError error) {
                }

                @Override
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email.getText().toString());
                    params.put("psw", password.getText().toString());
                    return params;
                }

                @Override
                public String getURL() {
                    return UnigeServerConnection.URL + UnigeServerConnection.LOGIN;
                }
            });
            connection.sendRequest(getActivity());
        }

        else
        if(v.getId() == pswDim.getId()){
            if (email.getText().toString().equals("") && !UnigeServerConnection.isEmailValid(email.getText().toString())) {
                Toast.makeText(this.getActivity(), "Inserisci un email valida", Toast.LENGTH_SHORT).show();
                return ;
            }

            UnigeServerConnection conn = new UnigeServerConnection(new UnigeServerConnectionHandler() {
                @Override
                public void onResponse(JSONObject risposta) {
                    try {
                        if (risposta.getString("risultato").equals("ok")) {
                            Toast.makeText(email.getContext(), "E' stata inviata un'email contenente la nuova password.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(email.getContext(), "Utente non riconosciuto.", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email.getText().toString());
                    return params;                }

                @Override
                public String getURL() {
                   return UnigeServerConnection.URL + UnigeServerConnection.PSW_DIMENTICATA;
                }
            });
            conn.sendRequest(this.getActivity());
        }



    }

}
