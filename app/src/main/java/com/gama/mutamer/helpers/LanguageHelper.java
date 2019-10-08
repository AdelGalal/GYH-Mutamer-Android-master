package com.gama.mutamer.helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.View;

import java.util.Locale;

/**
 * Created by mustafa on 5/15/18.
 * Release the GEEK
 */
public class LanguageHelper {

    /***
     * Flags
     */
    public static final String LANGUAGE_ENGLISH = "en",
            LANGUAGE_ARABIC = "ar";

    private static final String LANGUAGE_ENGLISH_NAME = "English",
            LANGUAGE_ARABIC_NAME = "العربية";

    public static final int LANGUAGE_ENGLISH_INDEX = 0,
            LANGUAGE_ARABIC_INDEX = 1;
    /***
     * Vars
     */
    private static final String[] LANGUAGES = new String[]{
            LANGUAGE_ENGLISH,
            LANGUAGE_ARABIC
    };
    private static final String TAG = "LOCALE";
    private static final String LANGUAGE_SHARED_KEY = "language";

    /***
     * Get Current App Language as string
     * @param context Context to access Shared Preferences
     * @return Current App's Language
     */
    public String getCurrentLanguage(Context context) {
        if (context == null) return "en";
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).getString(LANGUAGE_SHARED_KEY, "en");
    }

    //TODO: DOCUMENTATION
    public int getCurrentLanguageIndex(Context context) {
        String lang = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).getString(LANGUAGE_SHARED_KEY, LANGUAGE_ENGLISH);
        int position = LANGUAGE_ENGLISH_INDEX;
        if (!lang.equalsIgnoreCase(LANGUAGE_ENGLISH))
            position = LANGUAGE_ARABIC_INDEX;
        return position;
    }

    //TODO: DOCUMENTATION
    public void initLanguage(Activity context) {

        String currentLanguage = getCurrentLanguage(context);
        String language = SharedPrefHelper.getSharedString(context, SharedPrefHelper.SHARED_PREFERENCE_LANGUAGE_KEY);
        if (!currentLanguage.equalsIgnoreCase(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                config.setLocale(locale);
                config.setLayoutDirection(locale);
            }
            config.locale = locale;
            context.getBaseContext().getResources().updateConfiguration(config,
                    context.getBaseContext().getResources().getDisplayMetrics());
        }
        this.forceRTLIfSupported(context, (language.equalsIgnoreCase(LANGUAGE_ARABIC)));
    }

    private String getCurrentLanguage(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return context.getBaseContext().getResources().getConfiguration().getLocales().get(0).getLanguage();
        return context.getBaseContext().getResources().getConfiguration().locale.getLanguage();
    }


    //TODO: DOCUMENTATION
    public Locale getCurrentLocale(Context context) {
        if (context == null) return new Locale("en");
        Locale locale = context.getResources().getConfiguration().locale;
        if (!locale.getLanguage().equals(SharedPrefHelper.getSharedString(context, SharedPrefHelper.SHARED_PREFERENCE_LANGUAGE_KEY)))
            Log.v(TAG, "DIFFERENT LOCALE " + locale.getLanguage());
        return locale;
    }

    //TODO: DOCUMENTATION
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported(Activity context, boolean replace) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context.getWindow().getDecorView().setLayoutDirection(replace ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
        }

    }

    //TODO: DOCUMENTATION
    public void changeLanguage(Activity context, int newLanguagePosition) {
        String newLanguage = LANGUAGES[newLanguagePosition];
        SharedPrefHelper.setSharedString(context, SharedPrefHelper.SHARED_PREFERENCE_LANGUAGE_KEY, newLanguage);
    }

}


