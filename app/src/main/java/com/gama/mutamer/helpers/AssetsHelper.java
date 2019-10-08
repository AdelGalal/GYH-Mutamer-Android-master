package com.gama.mutamer.helpers;

import android.content.Context;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;

import java.io.InputStream;

public class AssetsHelper {

    public static String getNationalities(Context context) {
        String json = null;
        try {
            InputStream is =  context.getResources().openRawResource(R.raw.countries);;
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            FirebaseErrorEventLog.SaveEventLog(ex);
            return null;
        }
        return json;
    }




}
