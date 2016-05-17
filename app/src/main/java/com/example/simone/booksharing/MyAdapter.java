package com.example.simone.booksharing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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
        Log.e("url", "adapter"+position+list.size());

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new BookHolder();
            holder.imgIcon = (ImageView) row.findViewById(R.id.immagine_slider_iw);
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
        holder.imgIcon.setImageBitmap(list.get(position));

        return row;
    }

    class BookHolder {
        ImageView imgIcon;
        //TextView txtTitle;
    }
}
