package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

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
    public View getView(int position, View convertView, ViewGroup parent) {
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
        switch (list.get(position).stato){
            case nonconfermato:
                holder.button.setText("Conferma prestito");
                holder.button1.setVisibility(View.VISIBLE);
                holder.button.setVisibility(View.VISIBLE);
                break;
            case incorso:
                holder.button.setVisibility(View.INVISIBLE);
                holder.button1.setVisibility(View.INVISIBLE);
                break;
            case inrestituzione:
                holder.button.setVisibility(View.VISIBLE);
                holder.button.setText("Conferma restituzione");
                holder.button1.setVisibility(View.INVISIBLE);
                break;
            default:
                Log.e("riempiprestati","problemabottonestato");
        }


        return row;
    }

    class BookHolder {
        TextView isbn;
        TextView dataPrestito;
        TextView richiedente;
        TextView dataprestito;
        Button button;
        Button button1;
    }
}


