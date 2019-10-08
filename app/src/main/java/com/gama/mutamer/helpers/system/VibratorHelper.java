package com.gama.mutamer.helpers.system;

import android.content.Context;
import android.os.Vibrator;

public class VibratorHelper {

    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null)
            vibrator.vibrate(milliseconds);
    }
}
