package com.gama.mutamer.viewModels.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WeatherState {
    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("main")
    private String mMain;
    @Expose
    @SerializedName("description")
    private String mDescription;
    @Expose
    @SerializedName("icon")
    private String mIcon;

    public int getId() {
        return mId;
    }

    public String getMain() {
        return mMain;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getIcon() {
        return mIcon;
    }
}
