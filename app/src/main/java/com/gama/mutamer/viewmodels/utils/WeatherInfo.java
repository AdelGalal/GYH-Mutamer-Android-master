package com.gama.mutamer.viewModels.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WeatherInfo {


    @Expose
    @SerializedName("temp")
    private double mTemp;
    @Expose
    @SerializedName("pressure")
    private double mPressure;

    @Expose
    @SerializedName("humidity")
    private double mHumidity;

    public double getHumidity() {
        return mHumidity;
    }

    public double getTemp() {
        return mTemp;
    }

    public double getPressure() {
        return mPressure;
    }
}
