package com.gama.mutamer.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

/**
 * Created by mustafa on 7/26/17.
 * Release the GEEK
 */

public class FccRegistrationService extends FirebaseInstanceIdService {
    private final String TAG = "FCM";

    public FccRegistrationService() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        try {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + refreshedToken);
//            FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GENERAL);
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

}
