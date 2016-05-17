package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref= getSharedPreferences("pref", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_home);
        if (findViewById(R.id.home_fragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.


            // Create a new Fragment to be placed in the activity layout
            HomeFragment hFragment=new HomeFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            getFragmentManager().beginTransaction().add(R.id.home_fragment, hFragment).commit();

            // Add the fragment to the 'fragment_container' FrameLayout

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.account_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences login=getSharedPreferences("login", Context.MODE_PRIVATE);

        if(login.getString("email","")==""){
            SharedPreferences index=getSharedPreferences("index",MODE_PRIVATE);
            SharedPreferences.Editor et=index.edit();
            Toast.makeText(this, "Effettuare il Login!", Toast.LENGTH_LONG).show();
            et.putInt("flag",1).commit();
            Intent i = new Intent(getApplicationContext(), Index.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences pref=getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor et=pref.edit();
        switch (item.getItemId()){
            case R.id.account_menu:
                et.putInt("flagdetails",0).commit();
                Intent i = new Intent(getApplicationContext(), Details.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                break;
            case R.id.logout_menu:
                SharedPreferences login=getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor etlog=login.edit();
                etlog.putString("email","").commit();
                SharedPreferences index=getSharedPreferences("index",MODE_PRIVATE);
                SharedPreferences.Editor etindex=index.edit();
                etindex.putInt("flag",0).commit();
                Log.e("stringa","nullalogin");
                Intent i1 = new Intent(getApplicationContext(), Index.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
                break;
            case R.id.chat_menu:

                et.putInt("flagdetails",1).commit();
                Intent i2 = new Intent(getApplicationContext(), Details.class);
                i2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i2);
                break;

            default:
                break;


        }


        return super.onOptionsItemSelected(item);
    }
}
