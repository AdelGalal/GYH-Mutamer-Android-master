package com.gama.mutamer.helpers;

import android.location.Location;

import com.google.android.gms.maps.model.LatLngBounds;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;

public class OfflineMapsHelper {
    private static final String MAKKAH_MAPS = "makkah.mbtiles";
    private static final String MADINA_MAPS = "madina.mbtiles";
    private static final String JEDDAH_MAPS = "jeddah.mbtiles";

    public static String getCurrentMap(Location location) {

        try {
            if (location == null) return MAKKAH_MAPS;
            //CHECK MAKKAH
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            double[] boundariesX = {};
            double[] boundariesY = {};
            boundariesX = new BoundariesHelper().vertices_x_Haram;
            boundariesY = new BoundariesHelper().vertices_y_Haram;

            float nearestDistance = Float.MAX_VALUE;
            for (int i = 0; i < boundariesX.length; i++) {

                builder.include(new com.google.android.gms.maps.model.LatLng(boundariesX[i], boundariesY[i]));
                float[] res = new float[99];
                android.location.Location.distanceBetween(location.getLatitude(), location.getLongitude(), boundariesX[i], boundariesY[i], res);
                if (res[0] < nearestDistance) nearestDistance = res[0];
            }
            LatLngBounds bounds = builder.build();
            boolean isLocationWithinBounds = bounds.contains(new com.google.android.gms.maps.model.LatLng(location.getLatitude(), location.getLongitude()));
            if (isLocationWithinBounds) return MAKKAH_MAPS;

            //CHECK MADINA
            builder = new LatLngBounds.Builder();
            boundariesX = new BoundariesHelper().vertices_x_madinah;
            boundariesY = new BoundariesHelper().vertices_y_madinah;

            nearestDistance = Float.MAX_VALUE;
            for (int i = 0; i < boundariesX.length; i++) {

                builder.include(new com.google.android.gms.maps.model.LatLng(boundariesX[i], boundariesY[i]));
                float[] res = new float[99];
                android.location.Location.distanceBetween(location.getLatitude(), location.getLongitude(), boundariesX[i], boundariesY[i], res);
                if (res[0] < nearestDistance) nearestDistance = res[0];
            }
            bounds = builder.build();
            isLocationWithinBounds = bounds.contains(new com.google.android.gms.maps.model.LatLng(location.getLatitude(), location.getLongitude()));
            if (isLocationWithinBounds) return MADINA_MAPS;
            return JEDDAH_MAPS;

        } catch (Exception e)
        {
            FirebaseErrorEventLog.SaveEventLog(e);
            return MAKKAH_MAPS;
        }
    }
}
