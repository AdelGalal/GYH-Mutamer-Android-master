package com.gama.mutamer.helpers.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.gama.mutamer.utils.Constants;

public class FirebaseSubscribeHelper {

    public static void SubScribeToUser(String userId) {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_GENERAL);
            FirebaseMessaging.getInstance().subscribeToTopic(String.format("User%s", userId));
            FirebaseMessaging.getInstance().subscribeToTopic("Users");
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    public static void SubScribeToLanguage(String oldLanguage,String newLanguage){
        try {
            if(oldLanguage != null)
                FirebaseMessaging.getInstance().unsubscribeFromTopic("Language"+oldLanguage);
            FirebaseMessaging.getInstance().subscribeToTopic("Language"+newLanguage);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_GENERAL);
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    public static void UnsubscribeToUser(String userId){
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(String.format("User%s", userId));
            FirebaseMessaging.getInstance().unsubscribeFromTopic("Users");
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
            e.printStackTrace();
        }
    }
}
