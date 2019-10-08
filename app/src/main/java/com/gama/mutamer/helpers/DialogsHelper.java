package com.gama.mutamer.helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


public class DialogsHelper {


    /***
     * Get Alert Dialog
     * @param activity Activity to show the dialog
     * @param title Dialog title
     * @param message Dialog Message
     * @param okButton OK Button text
     * @return Alert Dialog
     */
    public static AlertDialog getAlert(Activity activity, String title, String message, String okButton) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(title).setMessage(message);
        dialog.setPositiveButton(okButton, null);
        return dialog.create();
    }


    /***
     * Get Progress dialog with base setup implemented
     * @param activity The activity the progress dialog will be shown in
     * @param titleId Dialog's Title string resource id
     * @return Progress dialog
     */
    public static ProgressDialog getProgressDialog(Activity activity, int titleId) {
        if (activity == null){
            return null;
        }
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(activity.getString(titleId));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /***
     * Display Toast
     * @param context Activity
     * @param messageId Message resource id to Display
     */
    public static void showToast( Context context, int messageId) {
        if (context == null) return;
        Toast.makeText(context, context.getString(messageId), Toast.LENGTH_LONG).show();
    }

    /***
     * Display Toast
     * @param context Activity
     * @param message String message to display
     */
    public static void showToast( Context context, String message) {
        if (context == null) return;
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


}
