package com.example.simone.booksharing;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;


public class Index extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        /*
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
        else
        */
   }

}
