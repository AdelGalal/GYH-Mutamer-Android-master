package com.gama.mutamer.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.gama.mutamer.helpers.Schedule;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PrayServices extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        try {
            //Realm.init(getApplicationContext());
            //Setting setting = new CommonRepository().getSettings();
            int method = SharedPrefHelper.getSharedInt(this, SharedPrefHelper.PRAYER_CALC);
            Schedule today = Schedule.today(this, method);
            short nextTimeIndex = today.nextTimeIndex();
            Calendar actualTime = today.getTimes()[nextTimeIndex];
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_ACTUAL_TIME, actualTime.getTimeInMillis());
            intent.putExtra(Constants.EXTRA_TIME_INDEX, nextTimeIndex);

            new Thread(new StartNotificationTask(this, intent)).start();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
