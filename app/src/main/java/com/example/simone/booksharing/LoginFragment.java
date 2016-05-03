package com.example.simone.booksharing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends android.app.Fragment implements View.OnClickListener {
    private Button accedi;
    private EditText email, password;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_login,container,false);
        accedi=(Button)view.findViewById(R.id.accedi_button);
        email = (EditText) view.findViewById(R.id.email_et);
        password = (EditText) view.findViewById(R.id.password_et);


        accedi.setOnClickListener(this);

       return view;

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(v.getContext(),Home.class));
/*
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UnigeServerConnection.URL+UnigeServerConnection.REGISTRATION, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ris = new JSONObject(response);
                    if(ris.getString("risultato").equals("ok")){
                        startActivity(new Intent(email.getContext(), Home.class));
                    }
                    else{
                        Toast.makeText(email.getContext(), "Iscrizione avvenuta! E' stata inviata un email,",Toast.LENGTH_SHORT).show();
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
            params.put("psw",password.getText().toString());
        }
        };
        queue.add(stringRequest);
*/
    }
}
