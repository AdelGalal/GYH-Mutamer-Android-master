package com.gama.mutamer.helpers.system;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;

public class NotificationsHelper {


    public static void showNotification(Context context, String title, String text, int id) {
        try {
            NotificationManager mNotificationManager;
            NotificationCompat.Builder mBuilder;
            final String NOTIFICATION_CHANNEL_ID = "10001";
            Intent resultIntent = new Intent(context, MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(R.drawable.ic_stat_manasikana_logo);
            mBuilder.setContentTitle(title)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.ic_launcher))
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(resultPendingIntent);

            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }


}
