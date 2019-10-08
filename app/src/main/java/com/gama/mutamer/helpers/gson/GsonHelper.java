package com.gama.mutamer.helpers.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {
    public static Gson getGson(){
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Long.class,new LongTypeAdapter());
        builder.registerTypeAdapter(Double.class,new DoubleTypeAdapter());
        return builder.create();
    }
}
