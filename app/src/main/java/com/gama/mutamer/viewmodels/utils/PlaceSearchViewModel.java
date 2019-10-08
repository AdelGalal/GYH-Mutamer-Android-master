package com.gama.mutamer.viewModels.utils;

import com.gama.mutamer.R;

import java.io.Serializable;

/**
 * Created by mustafa on 7/15/16.
 * Release the GEEK
 */
public class PlaceSearchViewModel implements Serializable {
    public int mDistance = 500;
    private String mType = "hospital";
    private int mImage = R.drawable.ic_hospital;


    public int getDistance() {
        return mDistance;
    }

    public void setDistance(int distance) {
        mDistance = distance;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }
}
