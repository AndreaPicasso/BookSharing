package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

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
    public View getView(int position, View convertView, ViewGroup parent) {
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
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //da implementare la modifica sul server
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //da implementare
            }
        });


        return row;
    }

    class BookHolder {
        TextView isbn;
        RatingBar rating;
        Button button;
        TextView proprietario;
        TextView dataprestito;
        //TextView txtTitle;
    }
}

