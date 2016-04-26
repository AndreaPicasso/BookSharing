package com.example.simone.booksharing;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LoginFragment extends android.app.Fragment {
    private Button accedi;
    public void onCreate(Bundle savedInstanceState) {
        ;
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_login,container,false);
        accedi=(Button)view.findViewById(R.id.accedi_button);
        View.OnClickListener accedi_list=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(),Home.class));
            }
        };
        accedi.setOnClickListener(accedi_list);

       return view;

    }

}
