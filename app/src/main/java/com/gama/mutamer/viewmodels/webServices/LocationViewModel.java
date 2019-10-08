package com.gama.mutamer.viewModels.webServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mustafa on 8/11/16.
 * Release the GEEK
 */
public class LocationViewModel implements Serializable {




    @Expose
    @SerializedName("lat")
    private Double mLatitude;

    @Expose
    @SerializedName("lng")
    private Double mLongitude;

    public LocationViewModel() {

    }

    public LocationViewModel(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }
}
