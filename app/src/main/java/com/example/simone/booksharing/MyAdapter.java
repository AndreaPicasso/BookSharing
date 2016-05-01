package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Utente on 01/05/2016.
 */
class MyAdapter extends ArrayAdapter<Bitmap> {


    Context context;
    int layoutResourceId;
    ArrayList<Bitmap> list;

    public MyAdapter(Context context, int layoutResourceId, ArrayList<Bitmap> list) {
        super(context, layoutResourceId, list);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BookHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new BookHolder();
            holder.imgIcon = (ImageView) row.findViewById(R.id.immagine_slider_iw);


            row.setTag(holder);
        } else {
            holder = (BookHolder) row.getTag();
        }


        //holder.txtTitle.setText(weather.title);
        holder.imgIcon.setImageBitmap(list.get(position));

        return row;
    }

    class BookHolder {
        ImageView imgIcon;
        //TextView txtTitle;
    }
}
