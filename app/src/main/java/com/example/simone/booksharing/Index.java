package com.example.simone.booksharing;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;



public class Index extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_index);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.index_fragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.


            // Create a new Fragment to be placed in the activity layout
            IndexFragment indFragment=new IndexFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            getFragmentManager().beginTransaction().add(R.id.index_fragment, indFragment).commit();

            // Add the fragment to the 'fragment_container' FrameLayout

        }

        /*
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
        else
        */
   }

}
