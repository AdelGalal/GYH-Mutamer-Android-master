package com.gama.mutamer.viewModels.webServices;

import android.location.Location;
import android.util.Log;

import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mustafa on 8/11/16.
 * Release the GEEK
 */
public class GooglePlaceResult implements Serializable {
    @Expose
    @SerializedName("geometry")
    private GeometryViewModel mGeometry;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("vicinity")
    private String mAddress;

    private int mIcon;
    private String mType;
    private int mFullIcon;
    private double mDistance;

    @Expose
    @SerializedName(Constants.ICON)
    private String mLogo;

    public void UpdateDistance(Location location) {
        float[] res = new float[99];
        android.location.Location.distanceBetween(location.getLatitude(), location.getLongitude(), getGeometry().getLocation().getLatitude(), getGeometry().getLocation().getLongitude(), res);
        setDistance(res[0]);
        Log.v("Distance", res[0] + " -" + getName());
    }


    public double getDistance() {
        return mDistance;
    }

    public void setDistance(double distance) {
        mDistance = distance;
    }

    public int getFullIcon() {
        return mFullIcon;
    }

    public void setFullIcon(int fullIcon) {
        mFullIcon = fullIcon;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public GeometryViewModel getGeometry() {
        return mGeometry;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public void setmGeometry(GeometryViewModel mGeometry) {
        this.mGeometry = mGeometry;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public void setmFullIcon(int mFullIcon) {
        this.mFullIcon = mFullIcon;
    }

    public void setmDistance(double mDistance) {
        this.mDistance = mDistance;
    }

    public String getLogo() {
        return mLogo;
    }
}
