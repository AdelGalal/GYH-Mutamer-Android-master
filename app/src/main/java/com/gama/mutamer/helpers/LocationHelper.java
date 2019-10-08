package com.gama.mutamer.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;

public class LocationHelper {

    public static void checkLocationServiceStatus(Context context) {
        if (context == null) return;
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (lm == null) return;
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            FirebaseErrorEventLog.SaveEventLog(ex);
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            FirebaseErrorEventLog.SaveEventLog(ex);
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage(context.getResources().getString(R.string.app_need_location));
            dialog.setTitle(R.string.gps_network_not_enabled);
            dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), (paramDialogInterface, paramInt) -> {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(myIntent);
                //get gps
            });
            dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {


                }
            });
            dialog.show();
        }
    }
}
