package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Utente on 17/05/2016.
 */
public class BookAdapterReading extends ArrayAdapter<BookReadingForAdapter> {
    Context context;
    int layoutResourceId;
    ArrayList<BookReadingForAdapter> list;

    public BookAdapterReading(Context context, int layoutResourceId, ArrayList<BookReadingForAdapter> list) {
        super(context, layoutResourceId, list);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();

    }
    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
         BookHolder holder = null;



        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new BookHolder();
            holder.isbn = (TextView) row.findViewById(R.id.isbn_libro_inlettura);
            holder.rating = (RatingBar) row.findViewById(R.id.ratingBar);
            holder.button=(Button) row.findViewById(R.id.restituisci_button);
            holder.dataprestito=(TextView) row.findViewById(R.id.data_libro_inlettura);
            holder.proprietario=(TextView) row.findViewById(R.id.proprietario_libro_inlettura);

            /*
            holder.imgIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.home_fragment, new BookFragment()).addToBackStack(null).commit();
                }

            });*/


            row.setTag(holder);
        } else {
            holder = (BookHolder) row.getTag();
        }


        //holder.txtTitle.setText(weather.title);
        holder.isbn.setText(list.get(position).isbn);


        holder.dataprestito.setText(list.get(position).dataprestito);
        holder.proprietario.setText(list.get(position).proprietario);

        holder.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {

                UnigeServerConnection unigeServerConnection=new UnigeServerConnection(new UnigeServerConnectionHandler() {
                    @Override
                    public void onResponse(JSONObject risposta) {
                        try{
                            if (risposta.has("ok")) {
                                Toast.makeText(getContext(), risposta.getString("ok"), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(),risposta.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch(Exception e){
                            Log.e("e",e.getMessage());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.getMessage());
                    }

                    @Override
                    public Map<String, String> getParams() {
                        Float rat=rating;
                        Map<String, String> params = new HashMap<String, String>();
                        SharedPreferences login=context.getSharedPreferences("login", Context.MODE_PRIVATE);
                        params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);
                        params.put("valutatore",login.getString("email","") );
                        params.put("rating",rat.toString() );
                        params.put("valutato",list.get(position).proprietario);
                        return params;
                    }

                    @Override
                    public String getURL() {
                        return UnigeServerConnection.URL+UnigeServerConnection.VALUTAZIONE;
                    }
                });
                unigeServerConnection.sendRequest(context);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnigeServerConnection unigeServerConnection=new UnigeServerConnection(new UnigeServerConnectionHandler() {
                    @Override
                    public void onResponse(JSONObject risposta) {
                        try{
                            if (risposta.has("ok")){
                                Toast.makeText(getContext(), risposta.getString("ok"), Toast.LENGTH_SHORT).show();
                                SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor et = pref.edit();
                                et.putInt("flagdetails", 1).commit();
                                Intent i2 = new Intent(context.getApplicationContext(), Details.class);
                                i2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                context.startActivity(i2);
                            }
                            else{
                                Toast.makeText(getContext(), risposta.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e){
                            Log.e("e",e.getMessage());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.getMessage());
                    }

                    @Override
                    public Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        SharedPreferences login=context.getSharedPreferences("login", Context.MODE_PRIVATE);
                        params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);
                        params.put("email",login.getString("email","") );
                        params.put("isbn",list.get(position).isbn);
                        params.put("proprietario",list.get(position).proprietario);
                        return params;
                    }

                    @Override
                    public String getURL() {
                        return UnigeServerConnection.URL+UnigeServerConnection.RICHIESTA_RESTITUZIONE;
                    }
                });
                unigeServerConnection.sendRequest(context);
            }
        });


        return row;
    }

    class BookHolder {
        TextView isbn;
        RatingBar rating;
        Button button=null;
        TextView proprietario;
        TextView dataprestito;
        //TextView txtTitle;
    }
}

