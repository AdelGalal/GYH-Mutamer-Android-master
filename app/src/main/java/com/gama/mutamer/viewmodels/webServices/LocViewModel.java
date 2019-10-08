package com.gama.mutamer.viewModels.webServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mustafa on 8/22/16.
 * Release the GEEK
 */
public class LocViewModel implements Serializable {

    @Expose
    @SerializedName("Latitude")
    private double mLatitude;
    @Expose
    @SerializedName("Longitude")
    private double mLongitude;

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
}
