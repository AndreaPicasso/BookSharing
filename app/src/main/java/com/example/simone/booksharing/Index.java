package com.example.simone.booksharing;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.OrientationEventListener;


public class Index extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences pref =getSharedPreferences("index",MODE_PRIVATE);
        SharedPreferences.Editor et= pref.edit();
        et.putInt("flag",0).commit();


        setContentView(R.layout.activity_index);
        /*if (findViewById(R.id.index_fragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.


            // Create a new Fragment to be placed in the activity layout
            IndexFragment indFragment=new IndexFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            getFragmentManager().beginTransaction().add(R.id.index_fragment, indFragment).commit();

            // Add the fragment to the 'fragment_container' FrameLayout

        }*/


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout



        /*
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
        else
        */
   }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences indexPref =getSharedPreferences("index", MODE_PRIVATE);
        SharedPreferences.Editor et= indexPref.edit();
        int flag=indexPref.getInt("flag",0);

        //Log.e("ONRESUME", ""+flag);
        switch (flag){
            case 0:
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
                break;
            case 1:
                if (findViewById(R.id.index_fragment) != null) {

                    // However, if we're being restored from a previous state,
                    // then we don't need to do anything and should return or else
                    // we could end up with overlapping fragments.

                    // Create a new Fragment to be placed in the activity layout
                    LoginFragment loginFragment=new LoginFragment();

                    // In case this activity was started with special instructions from an
                    // Intent, pass the Intent's extras to the fragment as arguments
                    getFragmentManager().beginTransaction().add(R.id.index_fragment, loginFragment).commit();
                    // Add the fragment to the 'fragment_container' FrameLayout
                    Log.e("login","sgfgdfgdf");

                }
                break;
            case 2:
                if (findViewById(R.id.index_fragment) != null) {

                    // However, if we're being restored from a previous state,
                    // then we don't need to do anything and should return or else
                    // we could end up with overlapping fragments.

                    // Create a new Fragment to be placed in the activity layout
                    RegistrationFragment registrationFragment = new RegistrationFragment();

                    // In case this activity was started with special instructions from an
                    // Intent, pass the Intent's extras to the fragment as arguments
                    getFragmentManager().beginTransaction().add(R.id.index_fragment, registrationFragment).commit();
                    // Add the fragment to the 'fragment_container' FrameLayout
                    Log.e("registra","sgfgdfgdf");
                }
                break;
            default:
                if (findViewById(R.id.index_fragment) != null) {

                    // However, if we're being restored from a previous state,
                    // then we don't need to do anything and should return or else
                    // we could end up with overlapping fragments.


                    // Create a new Fragment to be placed in the activity layout
                    IndexFragment indFragment=new IndexFragment();

                    // In case this activity was started with special instructions from an
                    // Intent, pass the Intent's extras to the fragment as arguments
                    getFragmentManager().beginTransaction().add(R.id.index_fragment, indFragment).commit();
                    Log.e("default", "sgfgdfgdf");
                    // Add the fragment to the 'fragment_container' FrameLayout

                }
        }
    }
}
