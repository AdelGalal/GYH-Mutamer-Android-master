package com.gama.mutamer.helpers.uiHelpers;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by mustafa on 12/11/18.
 * Release the GEEK
 */
public class ResourcesHelper {
    public static int getFlagIcon(@NonNull Activity activity, long id) {
        return activity.getResources().getIdentifier(String.format("a%s", id), "drawable",
                activity.getPackageName());
    }
}
