package com.gama.mutamer.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.AzanActivity;
import com.gama.mutamer.helpers.LocaleManager;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.helpers.Utils;
import com.gama.mutamer.helpers.WakeLock;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.helpers.system.NotificationsHelper;
import com.gama.mutamer.helpers.system.VibratorHelper;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.prayTimes.Preferences;


public class NotificationService extends IntentService {


    public NotificationService() {
        super(Constants.NOTIFICATIONS_SERVICE_TAG);
    }

    public static void notify(Context context, int timeIndex, long actualTime) {
        try {
            if (timeIndex == Constants.NEXT_FAJR) {
                timeIndex = Constants.FAJR;
            }
            Preferences preferences = Preferences.getInstance(context);
            final int notificationMethod = preferences.getNotificationMethod(timeIndex);
            if (Constants.NOTIFICATION_NONE == notificationMethod) {
                WakeLock.release();
                return;
            }
            if (context == null) return;
            LocaleManager.getInstance(context, true);

            // if (notificationMethod != Constants.NOTIFICATION_PLAY) {
            //final TelephonyManager telephonyManager
            //        = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //if (telephonyManager != null && telephonyManager.getCallState() == TelephonyManager.CALL_STATE_IDLE) {
            if (timeIndex == 0) timeIndex++;
            String name = context.getString(Constants.TIME_NAMES[timeIndex - 1]);
            issueNotification(timeIndex, name, context);
            //}
            //}

            StartNotificationReceiver.setNext(context);
//            Intent intent = new Intent(context, NotificationService.class);
//            intent.setAction(Constants.NOTIFICATION_SERVICE_ACTION_NOTIFY);
//            intent.putExtra(Constants.EXTRA_TIME_INDEX, timeIndex);
//            intent.putExtra(Constants.EXTRA_ACTUAL_TIME, actualTime);
//            context.startService(intent);
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    public static void issueNotification(int timeIndex, String name, Context context) {
        try {
            if (context == null) return;
            if (timeIndex < 0) {
                return;
            }
            if (timeIndex == 0) timeIndex++;
            int prayerNotifications = SharedPrefHelper.getSharedInt(context, SharedPrefHelper.PRAYER_NOTIFICATIONS);
            switch (prayerNotifications) {
                case Utils.PRAY_NOTIFICATION:
                    NotificationsHelper.showNotification(context, context.getString(R.string.pray_time), context.getString(R.string.app_name), timeIndex);
                    break;
                case Utils.PRAY_AZAN:
                    Intent intent = new Intent(context, AzanActivity.class);
                    intent.putExtra("azan", timeIndex);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    break;
                case Utils.PRAY_VIBRATE:
                    VibratorHelper.Vibrate(context, 3000);
                    break;

            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
