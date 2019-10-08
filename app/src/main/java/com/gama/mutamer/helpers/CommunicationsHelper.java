package com.gama.mutamer.helpers;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

public class CommunicationsHelper {

    public static void Dial(@NonNull Activity context, String number) {
        String uri = "tel:" + number;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void openUrl(@NonNull Context context, String url) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (myIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(myIntent);
        }
    }
}
