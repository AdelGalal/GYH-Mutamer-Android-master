package com.gama.mutamer.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mustafa on 5/15/18.
 * Release the GEEK
 */
public class SharedPrefHelper {
    public static final String SHARED_PREFERENCE_LANGUAGE_KEY = "language";
    public static final String PRAYER_CALC = "PrayersCalc";
    public static final String PRAYER_NOTIFICATIONS = "PrayersNotifications";

    public static String getSharedString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setSharedString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }


    public static void setSharedFloat(Context context, String key, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public static float getSharedFloat(Context context, String key,float backup) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, backup);
    }

    public static void setSharedBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getSharedBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setSharedInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static int getSharedInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

}
