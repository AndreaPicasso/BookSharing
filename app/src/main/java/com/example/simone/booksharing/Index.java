package com.example.simone.booksharing;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

public class Index extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            return inflater.inflate(R.layout.fragment_index_port, container, false);
        else
            return inflater.inflate(R.layout.fragment_index_land, container, false);
   }

}
