package com.example.simone.booksharing;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class InsertBookFragment extends android.app.Fragment {
    public void onCreate(Bundle savedInstanceState) {
        CheckConnection.isOnline(getActivity());

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =null;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            view=inflater.inflate(R.layout.fragment_insert_book_port,container,false);

        else
            view=inflater.inflate(R.layout.fragment_insert_book_land,container,false);

        return view;

    }
}
