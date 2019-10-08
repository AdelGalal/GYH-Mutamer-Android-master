package com.gama.mutamer.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

/**
 * Created by mustafa on 7/26/17.
 * Release the GEEK
 */

public class FcmMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Log.d(Constants.FIREBASE_MESSAGING_SERVICE, "From: " + remoteMessage.getFrom());

            if (remoteMessage.getNotification() != null && remoteMessage.getNotification().getBody() != null) {
                Log.d(Constants.FIREBASE_MESSAGING_SERVICE, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                //startService(new Intent(this, SyncService.class));
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }
}
