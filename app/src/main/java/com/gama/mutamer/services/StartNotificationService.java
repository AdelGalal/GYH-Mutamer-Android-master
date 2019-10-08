package com.gama.mutamer.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gama.mutamer.helpers.Schedule;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.utils.Constants;

import java.util.Calendar;


public class StartNotificationService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            //Setting setting = new CommonRepository().getSettings();
            int calcMethod = SharedPrefHelper.getSharedInt(this, SharedPrefHelper.PRAYER_CALC);
            Schedule today = Schedule.today(this, calcMethod);
            short nextTimeIndex = today.nextTimeIndex();
            Calendar actualTime = today.getTimes()[nextTimeIndex];
            intent = new Intent();
            intent.putExtra(Constants.EXTRA_ACTUAL_TIME, actualTime.getTimeInMillis());
            intent.putExtra(Constants.EXTRA_TIME_INDEX, nextTimeIndex);
        }
        new Thread(new StartNotificationTask(this, intent)).start();

        return START_NOT_STICKY;
    }


}
