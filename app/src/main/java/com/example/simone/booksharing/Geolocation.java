package com.example.simone.booksharing;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Utente on 04/06/2016.
 */
public class Geolocation {

    public Double lng;
    public Double lat;


    public void getLocation(Context context) {
        try {

            Location location = null;
            LocationManager mLocationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                // First get location from Network Provider
                if (isNetworkEnabled) {

                    Log.d("Network", "Network");
                    try {
                        if (mLocationManager != null) {
                            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lng = location.getLongitude();
                            }
                        }
                    } catch (SecurityException e) {
                        Toast.makeText(context, "Non ho il permesso di accedere alla posizione!", Toast.LENGTH_SHORT).show();

                    }

                }
                //get the location by gps
                if (isGPSEnabled) {
                    if (location == null) {
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (mLocationManager != null) {

                            try {
                                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    lat = location.getLatitude();
                                    lng = location.getLongitude();
                                }
                            } catch (SecurityException e) {
                                Toast.makeText(context, "Non ho il permesso di accedere alla posizione!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
            }
            Log.e("latlon",""+lat.toString()+" "+lng.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
