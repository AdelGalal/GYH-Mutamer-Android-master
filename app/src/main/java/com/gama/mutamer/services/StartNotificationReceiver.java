package com.gama.mutamer.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.gama.mutamer.helpers.Schedule;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.helpers.WakeLock;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

import java.util.Calendar;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class StartNotificationReceiver extends BroadcastReceiver {
    public static void setNext(Context context) {
        try {

            int prayerCalc = SharedPrefHelper.getSharedInt(context, SharedPrefHelper.PRAYER_CALC);
            Schedule today = Schedule.today(context, prayerCalc);
            short nextTimeIndex = today.nextTimeIndex();
            set(context, nextTimeIndex, today.getTimes()[nextTimeIndex]);
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    private static void set(Context context, short timeIndex, Calendar actualTime) {
        try {
            if (Calendar.getInstance().after(actualTime)) {
                // Somehow current time is greater than the prayer time
                return;
            }
            if (context == null) return;
            Intent intent = new Intent(context, StartNotificationReceiver.class);
            intent.putExtra(Constants.EXTRA_ACTUAL_TIME, actualTime.getTimeInMillis());
            intent.putExtra(Constants.EXTRA_TIME_INDEX, timeIndex);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (am != null) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    am.setExact(AlarmManager.RTC_WAKEUP, actualTime.getTimeInMillis(),
                            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                } else {
                    am.set(AlarmManager.RTC_WAKEUP, actualTime.getTimeInMillis(),
                            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                }
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            //String action = intent.getAction();
            WakeLock.acquire(context);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ComponentName componentName = new ComponentName(context, PrayServices.class);
//                JobInfo jobInfo = new JobInfo.Builder(12, componentName)
//                        .setRequiresCharging(false)
//                        .build();
//                JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
//                if (jobScheduler != null)
//                    jobScheduler.schedule(jobInfo);
//            } else {
                //context.startService(new Intent(context, StartNotificationService.class).putExtras(intent));
//            }
            if (intent == null) {
                //Setting setting = new CommonRepository().getSettings();
                int calcMethod = SharedPrefHelper.getSharedInt(context, SharedPrefHelper.PRAYER_CALC);
                Schedule today = Schedule.today(context, calcMethod);
                short nextTimeIndex = today.nextTimeIndex();
                Calendar actualTime = today.getTimes()[nextTimeIndex];
                intent = new Intent();
                intent.putExtra(Constants.EXTRA_ACTUAL_TIME, actualTime.getTimeInMillis());
                intent.putExtra(Constants.EXTRA_TIME_INDEX, nextTimeIndex);
            }
            new Thread(new StartNotificationTask(context, intent)).start();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }
}
