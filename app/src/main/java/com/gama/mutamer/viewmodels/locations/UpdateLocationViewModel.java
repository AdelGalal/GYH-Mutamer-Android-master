package com.gama.mutamer.viewModels.locations;

import java.io.Serializable;

/**
 * Created by mustafa on 5/7/16.
 * Release the GEEK
 */
public class UpdateLocationViewModel implements Serializable {
    private String mUserId;
    private String mName;
    private double mLatitude;
    private double mLongitude;
    private String mType;

    public UpdateLocationViewModel(String userId, String name, double latitude, double longitude, String type) {
        setUserId(userId);
        setName(name);
        setLatitude(latitude);
        setLongitude(longitude);
        setType(type);
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }
}
