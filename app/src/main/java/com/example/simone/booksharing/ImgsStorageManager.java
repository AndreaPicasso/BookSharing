package com.example.simone.booksharing;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by simone on 18/06/2016.
 */

public class ImgsStorageManager {
    private final static String PATH ="/data/data/BookSharing/app_data/imageDir";



    public static Bitmap loadImageFromStorage(String nameImg, Context context)
    {
        Log.e("caricamento","inizio");
        Bitmap b = null;
        try {
            ContextWrapper cw = new ContextWrapper(context);
            Log.e("caricamento","1");
            File directory = cw.getDir("imageDir", Context.MODE_APPEND);
            Log.e("caricamento","2");
            if(!directory.exists()){
                directory.mkdir();
            }
            File f=new File(directory, nameImg);
            Log.e("caricamento","3");
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            Log.e("caricamento","4");
            Log.e("imgasd", "cerco nella dir: "+directory.getAbsolutePath());

        }
        catch (FileNotFoundException e)
        {
            Log.e("imgasd", e.getMessage());

            e.printStackTrace();
        }
        return b;

    }


    public static String saveToInternalStorage(Bitmap bitmapImage, Context context, String nameImg){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,nameImg);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Log.e("imgasd", "Salvata " + directory.getAbsolutePath());

            fos.close();
        } catch (Exception e) {
            Log.e("imgasd", e.getMessage());

            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

}
