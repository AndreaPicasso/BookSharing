package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class Details extends Activity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref= getSharedPreferences("pref", MODE_PRIVATE);
        Boolean flag=true;
        SharedPreferences.Editor et= pref.edit();
        et.putBoolean("flag",flag);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_details);
        int flagdetails=pref.getInt("flagdetails",0);


        //Log.e("ONRESUME", ""+flag);
        switch (flagdetails){
            case 0:
                if (findViewById(R.id.details_fragment) != null) {

                    // However, if we're being restored from a previous state,
                    // then we don't need to do anything and should return or else
                    // we could end up with overlapping fragments.


                    // Create a new Fragment to be placed in the activity layout
                    AccountFragment aFragment=new AccountFragment();

                    // In case this activity was started with special instructions from an
                    // Intent, pass the Intent's extras to the fragment as arguments
                    getFragmentManager().beginTransaction().add(R.id.details_fragment, aFragment).commit();
                }
                break;
            case 1:
                if (findViewById(R.id.details_fragment) != null) {

                    // However, if we're being restored from a previous state,
                    // then we don't need to do anything and should return or else
                    // we could end up with overlapping fragments.
                    // Create a new Fragment to be placed in the activity layout
                    ChatFragment cFragment=new ChatFragment();

                    // In case this activity was started with special instructions from an
                    // Intent, pass the Intent's extras to the fragment as arguments
                    getFragmentManager().beginTransaction().add(R.id.details_fragment, cFragment).commit();
                }break;


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        int flagdetails = pref.getInt("flagdetails", 0);
        switch (flagdetails) {
            case 0:
                if (findViewById(R.id.details_fragment) != null) {

                    // However, if we're being restored from a previous state,
                    // then we don't need to do anything and should return or else
                    // we could end up with overlapping fragments.


                    // Create a new Fragment to be placed in the activity layout
                    AccountFragment aFragment = new AccountFragment();

                    // In case this activity was started with special instructions from an
                    // Intent, pass the Intent's extras to the fragment as arguments
                    getFragmentManager().beginTransaction().add(R.id.details_fragment, aFragment).commit();
                }
                break;
            case 1:
                if (findViewById(R.id.details_fragment) != null) {

                    // However, if we're being restored from a previous state,
                    // then we don't need to do anything and should return or else
                    // we could end up with overlapping fragments.
                    // Create a new Fragment to be placed in the activity layout
                    ChatFragment cFragment = new ChatFragment();

                    // In case this activity was started with special instructions from an
                    // Intent, pass the Intent's extras to the fragment as arguments
                    getFragmentManager().beginTransaction().add(R.id.details_fragment, cFragment).commit();
                }
                break;


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.home_menu){
            Intent i=new Intent(getApplicationContext(), Home.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
