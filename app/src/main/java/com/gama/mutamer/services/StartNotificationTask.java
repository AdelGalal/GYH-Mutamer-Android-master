package com.gama.mutamer.services;

import android.content.Context;
import android.content.Intent;

import com.gama.mutamer.helpers.Utils;
import com.gama.mutamer.helpers.WakeLock;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.prayTimes.Preferences;

public class StartNotificationTask implements Runnable {

    private final Context context;
    private final Intent intent;

    StartNotificationTask(Context c, Intent i) {
        context = c;
        intent = i;
    }

    @Override
    public void run() {
        short timeIndex = intent.getShortExtra(Constants.EXTRA_TIME_INDEX, (short) -1);
        if (Utils.getIsForeground()) {
            long actualTime = intent.getLongExtra(Constants.EXTRA_ACTUAL_TIME, 0);
            NotificationService.notify(context, timeIndex, actualTime);
        } else {
            StartNotificationReceiver.setNext(context);
        }
        if (timeIndex == -1) { // Got here from boot
            Preferences preferences = Preferences.getInstance(context);
            if (!preferences.getBasmalaEnabled()) {
                WakeLock.release();
            }
        } else {
            // Notify the user for the current time, need to do this last since it releases the WakeLock
            long actualTime = intent.getLongExtra(Constants.EXTRA_ACTUAL_TIME, 0);
            NotificationService.notify(context, timeIndex, actualTime);
        }
    }
}
