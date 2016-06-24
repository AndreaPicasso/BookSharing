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
import android.widget.ListView;
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
class BookAdapterShared extends ArrayAdapter<BookSharedForAdapter> {
    Context context;
    int layoutResourceId;
    ArrayList<BookSharedForAdapter> list;

    public BookAdapterShared(Context context, int layoutResourceId, ArrayList<BookSharedForAdapter> list) {
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
            holder.isbn = (TextView) row.findViewById(R.id.isbn_prestato_tw);
            holder.dataPrestito = (TextView) row.findViewById(R.id.data_prestito_tw);
            holder.richiedente= (TextView) row.findViewById(R.id.richiedente_prestito_tw);
            holder.button=(Button) row.findViewById(R.id.prestito_button);
            holder.button1=(Button) row.findViewById(R.id.prestito_button2);
            holder.rating = (RatingBar) row.findViewById(R.id.ratingBar2);

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
        holder.isbn.setText(list.get(position).ISBN);
        holder.dataPrestito.setText(list.get(position).dataPrestito);
        holder.richiedente.setText(list.get(position).richiedente);
        switch (list.get(position).stato) {
            case nonconfermato:
                holder.button.setText("Conferma prestito");
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        UnigeServerConnection unigeServerConnection = new UnigeServerConnection(new UnigeServerConnectionHandler() {
                            @Override
                            public void onResponse(JSONObject risposta) {
                                try {
                                    if (risposta.has("ok")) {
                                        Toast.makeText(getContext(), risposta.getString("ok"), Toast.LENGTH_SHORT).show();
                                        AccountFunction.RiempiLibriPrestati(context, (ListView) v.findViewById(R.id.prestito_list));
                                    } else {
                                        Toast.makeText(getContext(), risposta.getString("error"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.e("e", e.getMessage());
                                }

                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.getMessage());
                            }

                            @Override
                            public Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                SharedPreferences login = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                                params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);
                                params.put("email", login.getString("email", ""));
                                params.put("isbn", list.get(position).ISBN);
                                params.put("richiedente", list.get(position).richiedente);
                                return params;
                            }

                            @Override
                            public String getURL() {
                                return UnigeServerConnection.URL + UnigeServerConnection.CONFERMA_PRESTITO;
                            }
                        });
                        unigeServerConnection.sendRequest(context);

                    }
                });
                holder.button1.setVisibility(View.VISIBLE);
                holder.button.setClickable(true);
                holder.button1.setClickable(true);
                holder.button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        UnigeServerConnection unigeServerConnection = new UnigeServerConnection(new UnigeServerConnectionHandler() {
                            @Override
                            public void onResponse(JSONObject risposta) {
                                try {
                                    if (risposta.has("ok")) {
                                        Toast.makeText(getContext(), risposta.getString("ok"), Toast.LENGTH_SHORT).show();
                                        AccountFunction.RiempiLibriPrestati(context, (ListView) v.findViewById(R.id.prestito_list));

                                    } else {
                                        Toast.makeText(getContext(), risposta.getString("error"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.e("e", e.getMessage());
                                }

                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.getMessage());
                            }

                            @Override
                            public Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                SharedPreferences login = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                                params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);
                                params.put("email", login.getString("email", ""));
                                params.put("isbn", list.get(position).ISBN);
                                params.put("richiedente", list.get(position).richiedente);
                                return params;
                            }

                            @Override
                            public String getURL() {
                                return UnigeServerConnection.URL + UnigeServerConnection.RIFIUTA_PRESTITO ;
                            }
                        });
                        unigeServerConnection.sendRequest(context);
                    }
                });
                holder.button.setVisibility(View.VISIBLE);
                break;
            case incorso:
                holder.button.setVisibility(View.INVISIBLE);
                holder.button.setClickable(false);
                holder.button1.setClickable(false);
                holder.button1.setVisibility(View.INVISIBLE);
                break;
            case inrestituzione:
                holder.button.setVisibility(View.VISIBLE);
                holder.button.setClickable(true);
                holder.button.setText("Conferma restituzione");
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        UnigeServerConnection unigeServerConnection = new UnigeServerConnection(new UnigeServerConnectionHandler() {
                            @Override
                            public void onResponse(JSONObject risposta) {
                                try {
                                    if (risposta.has("ok")) {
                                        Toast.makeText(getContext(), risposta.getString("ok"), Toast.LENGTH_SHORT).show();
                                        AccountFunction.RiempiLibriPrestati(context,(ListView) v.findViewById(R.id.prestito_list));

                                    } else {
                                        Toast.makeText(getContext(), risposta.getString("error"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.e("e", e.getMessage());
                                }

                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.getMessage());
                            }

                            @Override
                            public Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                SharedPreferences login = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                                params.put("pswAccesso", UnigeServerConnection.PSW_ACCESSO);
                                params.put("email", login.getString("email", ""));
                                params.put("isbn", list.get(position).ISBN);
                                params.put("richiedente", list.get(position).richiedente);
                                return params;
                            }

                            @Override
                            public String getURL() {
                                return UnigeServerConnection.URL + UnigeServerConnection.CONFERMA_RESTITUZIONE;
                            }
                        });
                        unigeServerConnection.sendRequest(context);

                    }
                });

                holder.button1.setVisibility(View.INVISIBLE);
                holder.button1.setClickable(false);
                break;
            default:
                Log.e("riempiprestati", "problemabottonestato");
        }


        holder.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
                Log.e("rating","ok");
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
                        params.put("valutato",list.get(position).richiedente);
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



        return row;
    }

    class BookHolder {
        TextView isbn;
        TextView dataPrestito;
        TextView richiedente;
        TextView dataprestito;
        Button button;
        Button button1;
        RatingBar rating;
    }
}


