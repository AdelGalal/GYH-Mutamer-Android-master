package com.gama.mutamer.managers;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.gama.mutamer.services.GeofenceTransitionsIntentService;

import java.util.ArrayList;

public class GeofensingManager {

    private GeofencingClient mGeofencingClient;
    private ArrayList<Geofence> mGeofenceList = new ArrayList<>();


    public void addPlace(Context context, LatLng location, String id, String name) {
        mGeofencingClient = LocationServices.getGeofencingClient(context);
        mGeofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(id)

                .setCircularRegion(
                        location.latitude,
                        location.longitude,
                        1000
                )
                .setExpirationDuration(Long.MAX_VALUE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build());
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
        PendingIntent mGeofencePendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGeofencingClient.addGeofences(builder.build(), mGeofencePendingIntent);
    }

    public void removePlace(Context context, String id) {
        mGeofencingClient = LocationServices.getGeofencingClient(context);
        ArrayList<String> list = new ArrayList<>();
        list.add(id);
        mGeofencingClient.removeGeofences(list);
    }
}
