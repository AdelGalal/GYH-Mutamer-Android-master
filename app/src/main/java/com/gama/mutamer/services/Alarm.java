package com.gama.mutamer.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.gama.mutamer.data.models.content.Duaa;
import com.gama.mutamer.helpers.DateHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.helpers.system.NotificationsHelper;
import com.gama.mutamer.utils.Constants;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

public class Alarm extends BroadcastReceiver {
    public static void setAlarm(Context context, Date date, int code) {
        if (date == null || context == null) return;
        Calendar myAlarmDate = Calendar.getInstance();
        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
        myAlarmDate.setTime(date);
        long diff = DateHelper.getMiliseconds(myAlarmDate.getTime(), Calendar.getInstance().getTime());
        if (diff < 0) return;
        Log.v("ALARM", diff + "");
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.v("ALARM", "Alarm Null " + (am == null));
        if (am == null) return;
        Intent i = new Intent(context, Alarm.class);
        i.putExtra(Constants.DUAA_ALARM_NOTIFICATION, code);
        PendingIntent pi = PendingIntent.getBroadcast(context, code, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, diff, pi);
    }

    public static void cancelAlarm(Context context, int code) {
        Intent intent = new Intent(context, Alarm.class);

        PendingIntent sender = PendingIntent.getBroadcast(context, code, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;
        alarmManager.cancel(sender);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try (Realm realm = Realm.getDefaultInstance()) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (pm == null) return;
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
            if (wl == null) return;
            wl.acquire(10 * 1000);
            int id = intent.getIntExtra(Constants.DUAA_ALARM_NOTIFICATION, 0);
            Duaa duaa = realm.where(Duaa.class).equalTo(Constants.ID, id).findFirst();
            if (duaa == null) return;
            String lang = new LanguageHelper().getCurrentLanguage(context);
            NotificationsHelper.showNotification(context, duaa.getName(lang), duaa.getBody(lang), (int) duaa.getId());
            wl.release();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }
}