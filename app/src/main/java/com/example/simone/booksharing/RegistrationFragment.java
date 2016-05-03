package com.example.simone.booksharing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationFragment extends android.app.Fragment implements View.OnClickListener {
    EditText nome,cognome,email,psw,repsw;
    Button iscriviti;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ris = inflater.inflate(R.layout.fragment_registration,container,false);

        nome = (EditText)  ris.findViewById( R.id.nome_et);
        cognome = (EditText)  ris.findViewById( R.id.cognome_et);
        email = (EditText)  ris.findViewById( R.id.email_et);
        psw = (EditText)  ris.findViewById( R.id.password_et);
        repsw = (EditText)  ris.findViewById( R.id.conf_password_et);
        iscriviti = (Button) ris.findViewById(R.id.iscriviti_button);
        iscriviti.setOnClickListener(this);
        return ris;
    }



    @Override
    public void onClick(View v) {
        boolean ok = true;
        if(email.getText().toString().equals("")){
            ok=false;
            Toast.makeText(this.getActivity(), "Campo email vuoto", Toast.LENGTH_SHORT).show();
        }
        if(isEmailValid(email.getText().toString())){
            ok=false;
            Toast.makeText(this.getActivity(), "Email non valida", Toast.LENGTH_SHORT).show();
        }
            if(nome.getText().toString().equals("")) {
                ok=false;
                Toast.makeText(this.getActivity(), "Campo nome vuoto", Toast.LENGTH_SHORT).show();
            }
        if(cognome.getText().toString().equals("")){
            ok=false;
            Toast.makeText(this.getActivity(), "Campo cognome vuoto", Toast.LENGTH_SHORT).show();
        }
        if(psw.getText().toString().equals("")){
            ok=false;
            Toast.makeText(this.getActivity(), "Campo password vuoto", Toast.LENGTH_SHORT).show();
        }
        if(repsw.getText().toString().equals("")){
            ok=false;
            Toast.makeText(this.getActivity(), "Campo Re-password vuoto", Toast.LENGTH_SHORT).show();
        }
        if(!repsw.getText().toString().equals(psw.getText().toString())){
            ok=false;
            Toast.makeText(this.getActivity(), "Le password non coincidono", Toast.LENGTH_SHORT).show();
        }

        if(!ok) return;

        RequestQueue queue = Volley.newRequestQueue(this.getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UnigeServerConnection.URL+UnigeServerConnection.REGISTRATION, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ris = new JSONObject(response);
                    if(ris.getString("risultato").equals("ok")){
                        Toast.makeText(email.getContext(), "Iscrizione avvenuta! E' stata inviata un email",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(email.getContext(), Home.class));
                    }
                    else{
                        Toast.makeText(email.getContext(), ris.getString("risultato"),Toast.LENGTH_SHORT).show();
                    }

                }
                catch(Exception e){
                }
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){ @Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String, String>();
            params.put("email",email.getText().toString());
            params.put("psw",psw.getText().toString());
            params.put("repsw",repsw.getText().toString());
            params.put("nome",nome.getText().toString());
            params.put("cognome",cognome.getText().toString());
            return params;
            }
        };
        queue.add(stringRequest);
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
