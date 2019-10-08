package com.gama.mutamer.helpers.firebase;


import com.crashlytics.android.Crashlytics;
import com.gama.mutamer.utils.MutamerApp;

/***
 * Helper for Firebase Crash error actions
 */
public class FirebaseErrorEventLog {

    /***
     * Save Event Log to firebase crachlytics system, Used to log silent exception
     * @param exception Exception object
     */
    public static void SaveEventLog(Exception exception){
        try{
            exception.printStackTrace();
            Crashlytics.logException(exception);
        }catch (Exception e){
            e.printStackTrace();
            exception.printStackTrace();
            if(MutamerApp.DEBUG){
                throw e;
            }
            //Ignore
        }
    }
}
