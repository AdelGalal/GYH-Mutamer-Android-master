package com.gama.mutamer.helpers;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class MapHelper {
    public static void gotoLocation(Context activity, double lat, double lng) {
        if (activity == null){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat + "," + lng));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            Log.v("Maps", "Click");
            activity.startActivity(intent);
        }
    }
}
