package com.gama.mutamer.helpers;

import android.app.Activity;
import android.app.AlertDialog;

public class AlertDialogsHelper {

    public AlertDialog getAlert(Activity activity, String title, String message, String okButton) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(title).setMessage(message);
        dialog.setPositiveButton(okButton, null);
        return dialog.create();
    }


}
