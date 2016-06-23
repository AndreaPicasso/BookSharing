package com.example.simone.booksharing;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;


public class Geolocation {

    public Double lng;
    public Double lat;
    public Double minLat;
    public Double maxLat;
    public Double minLon;
    public Double maxLon;
    public static final double radius = 6371.01;
    private static final double MIN_LAT = Math.toRadians(-90d);  // -PI/2
    private static final double MAX_LAT = Math.toRadians(90d);   //  PI/2
    private static final double MIN_LON = Math.toRadians(-180d); // -PI
    private static final double MAX_LON = Math.toRadians(180d);  //  PI


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


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static double fromDegrees(double degree) {
        return Math.toRadians(degree);
    }


    public static double fromRadians(double radiants) {
        return  Math.toDegrees(radiants);
    }



    /**
     * <p>Computes the bounding coordinates of all points on the surface
     * of a sphere that have a great circle distance to the point represented
     * by this GeoLocation instance that is less or equal to the distance
     * argument.</p>
     * <p>For more information about the formulae used in this method visit
     * <a href="http://JanMatuschek.de/LatitudeLongitudeBoundingCoordinates">
     * http://JanMatuschek.de/LatitudeLongitudeBoundingCoordinates</a>.</p>
     * @param distance the distance from the point represented by this
     * GeoLocation instance. Must me measured in the same unit as the radius
     * argument.
     *  radius the radius of the sphere, e.g. the average radius for a
     * spherical approximation of the figure of the Earth is approximately
     * 6371.01 kilometers.
     * @return an array of two GeoLocation objects such that:<ul>
     * <li>The latitude of any point within the specified distance is greater
     * or equal to the latitude of the first array element and smaller or
     * equal to the latitude of the second array element.</li>
     * <li>If the longitude of the first array element is smaller or equal to
     * the longitude of the second element, then
     * the longitude of any point within the specified distance is greater
     * or equal to the longitude of the first array element and smaller or
     * equal to the longitude of the second array element.</li>
     * <li>If the longitude of the first array element is greater than the
     * longitude of the second element (this is the case if the 180th
     * meridian is within the distance), then
     * the longitude of any point within the specified distance is greater
     * or equal to the longitude of the first array element
     * <strong>or</strong> smaller or equal to the longitude of the second
     * array element.</li>
     * </ul>
     */
    public void boundingCoordinates(double distance) {

        if (radius < 0d || distance < 0d)
            throw new IllegalArgumentException();

        // angular distance in radians on a great circle
        double radDist = distance / radius;
        double latRad = fromDegrees(lat);
        double lonRad = fromDegrees(lng);

        minLat = latRad - radDist;
        maxLat = latRad + radDist;

        if (minLat > MIN_LAT && maxLat < MAX_LAT) {
            double deltaLon = Math.asin(Math.sin(radDist) /
                    Math.cos(latRad));
            minLon = lonRad - deltaLon;
            if (minLon < MIN_LON) minLon += 2d * Math.PI;
            maxLon = lonRad + deltaLon;
            if (maxLon > MAX_LON) maxLon -= 2d * Math.PI;
        } else {
            // a pole is within the distance
            minLat = Math.max(minLat, MIN_LAT);
            maxLat = Math.min(maxLat, MAX_LAT);
            minLon = MIN_LON;
            maxLon = MAX_LON;
        }
            minLat=fromRadians(minLat);
            minLon=fromRadians(minLon);
            maxLon=fromRadians(maxLon);
            maxLat=fromRadians(maxLat);

    }


}
