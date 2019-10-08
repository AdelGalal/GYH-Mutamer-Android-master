package com.gama.mutamer.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by mustafa on 5/15/18.
 * Release the GEEK
 */
public class Utils {
    public static final double INVALID_TRIGGER = -.999;

    public static final double PI = 3.1415926535898;

    public static final double DEG_TO_10_BASE = 1 / 15.0;

    public static final double CENTER_OF_SUN_ANGLE = -0.833370;

    public static final double ALTITUDE_REFRACTION = 0.0347;

    public static final double REF_LIMIT = 9999999;

    public static final double KAABA_LAT = 21.423333;

    public static final double KAABA_LONG = 39.823333;

    public static final double DEF_NEAREST_LATITUDE = 48.5;

    public static final double DEF_IMSAAK_ANGLE = 1.5;

    public static final double DEF_IMSAAK_INTERVAL = 10;

    public static final double DEFAULT_ROUND_SEC = 30;

    public static final double AGGRESSIVE_ROUND_SEC = 1;
    public static final int UNITS_US = 1;
    public static final int UNITS_METRIC = 0;

    public static final int PRAY_MUSLIM_METHOD = 1;
    public static final int PRAY_MECCA_METHOD = 0;
    public static final int PRAY_ISLAMIC_METHOD = 2;
    public static final int PRAY_EGYPT_METHOD = 3;
    public static final int PRAY_NOTIFICATION = 0;
    public static final int PRAY_AZAN = 1;
    public static final int PRAY_VIBRATE = 2;
    public static final int PRAY_MUTE = 3;
    private static final String[] DESK_CLOCK_PACKAGES = new String[]{
            "com.google.android.deskclock", // Google's
            "com.android.deskclock", // AOSP's
    };
    private static final String[] OTHER_CLOCK_PACKAGES = new String[]{
            "com.sec.android.app.clockpackage" // Samsung's
    };
    private static final String SELECT_TAB_INTENT_EXTRA = "deskclock.select.tab";
    private static final String DEFAULT_ALARM_ACTIVITY = "com.android.deskclock.AlarmClock";
    public static boolean isRestartNeeded = false;
    private static boolean mIsForeground = false;

    /* UTILITIES */
    public static double DEG_TO_RAD(double A) {
        return A * (PI / 180.0);
    }

    public static double RAD_TO_DEG(double A) {
        return A / (PI / 180.0);
    }

    public static Intent getDefaultAlarmsIntent(Context context) {
        PackageManager pm = context.getPackageManager();
        for (String packageName : DESK_CLOCK_PACKAGES) {
            try {
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                if (302 >= pi.versionCode) {
                    Intent intent = pm.getLaunchIntentForPackage(packageName);
                    intent.putExtra(SELECT_TAB_INTENT_EXTRA, 0);
                    return intent;
                }
                ComponentName cn = new ComponentName(packageName, DEFAULT_ALARM_ACTIVITY);
                pm.getActivityInfo(cn, 0);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setComponent(cn);
                return intent;
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }

        // None worked, try others
        for (String packageName : OTHER_CLOCK_PACKAGES) {
            try {
                pm.getPackageInfo(packageName, 0);
                return pm.getLaunchIntentForPackage(packageName);
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }


        return null;
    }

    public static boolean getIsForeground() {
        return mIsForeground;
    }

    public static void setIsForeground(boolean isForeground) {
        mIsForeground = isForeground;
    }
}

