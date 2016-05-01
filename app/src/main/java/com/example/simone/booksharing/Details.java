package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        if (findViewById(R.id.details_fragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.


            // Create a new Fragment to be placed in the activity layout
            AccountFragment aFragment=new AccountFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            getFragmentManager().beginTransaction().add(R.id.details_fragment, aFragment).commit();

            // Add the fragment to the 'fragment_container' FrameLayout

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
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
