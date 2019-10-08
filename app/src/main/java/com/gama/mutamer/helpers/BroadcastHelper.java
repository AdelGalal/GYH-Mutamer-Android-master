package com.gama.mutamer.helpers;


import android.content.Context;
import android.content.Intent;

public class BroadcastHelper {

    /***
     * Flags
     */
    public static final String BROADCAST_EXTRA_METHOD_NAME = "method";
    public static final String ACTION_NAME = "com.gama.mutamer";


    public static void sendInform(Context context, String method) {
        Intent intent = new Intent();
        intent.setAction(ACTION_NAME);
        intent.putExtra(BROADCAST_EXTRA_METHOD_NAME, method);
        try {
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
