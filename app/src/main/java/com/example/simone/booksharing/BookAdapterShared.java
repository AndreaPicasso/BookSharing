package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
            holder.titolo = (TextView) row.findViewById(R.id.titolo_prestato_tw);
            holder.dataPrestito = (TextView) row.findViewById(R.id.data_prestito_tw);

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
        holder.titolo.setText(list.get(position).titolo);
        holder.dataPrestito.setText(list.get(position).dataPrestito);


        return row;
    }

    class BookHolder {
        TextView titolo;
        TextView dataPrestito;
        //TextView txtTitle;
    }
}


