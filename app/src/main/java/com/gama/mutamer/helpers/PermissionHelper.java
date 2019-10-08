package com.gama.mutamer.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/***
 * Helper to check/request android permissions
 */
public class PermissionHelper {

    /***
     * Check if list of permission are granted to app
     * @param context Context to be able to check permissions
     * @param permissions Required permission
     * @return true if all permissions are granted
     */
    public boolean isPermissionsGranted(@NonNull Context context, @NonNull String[] permissions) {
        for (String permission : permissions) {
            Log.v("PERMISSIONS","CHECKING "+permission);
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /***
     * Check if we should show permission request dialog for for list of permissions
     * @param context Context to be able to check permissions
     * @param permissions Required permission
     * @return true if any permission in the list should showing request dialog
     */
    public boolean shouldAskForPermission(@NonNull Activity context, @NonNull String[] permissions) {
        for (String permission : permissions) {
            Log.v("PERMISSIONS",permission +" CHECK");
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
                    permission)) {
                Log.v("PERMISSIONS",permission +" TRUE");
                return true;
            }
        }
        return false;
    }

    /***
     * Display permission request dialog(s) for list of permissions
     * @param activity Context to be able to display dialog
     * @param permissions Required permission
     */
    public void askForPermissions(@NonNull Activity activity, @NonNull String[] permissions) {
        ActivityCompat.requestPermissions(activity,
                permissions, 8);
    }
}

