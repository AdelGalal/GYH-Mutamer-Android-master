package com.gama.mutamer.helpers.uiHelpers;

import android.app.Activity;

import java.lang.ref.WeakReference;

/***
 * Helper to handle Weak Reference  getting and setting
 */
public class WeakReferencesHelper {


    /***
     * Get Unwrapped activity weak reference
     * @param weakReference Weak Reference of an activity
     * @return Unwrapped Activity or null
     */
    public static Activity getActivity(WeakReference<Activity> weakReference){
        Activity activity = weakReference.get();
        if (activity == null || activity.isFinishing()){
            return null;
        }

        return activity;
    }
}
