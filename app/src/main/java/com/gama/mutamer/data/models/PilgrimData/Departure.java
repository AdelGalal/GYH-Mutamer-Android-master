package com.gama.mutamer.data.models.PilgrimData;

import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 12/10/18.
 * Release the GEEK
 */
public class Departure extends RealmObject implements Serializable {

    @PrimaryKey
    private long mId;

    @Expose
    @SerializedName(Constants.NUMBER)
    private String mNumber;

    @Expose
    @SerializedName(Constants.AIR_LINE)
    private AirLine mAirLine;

    @Expose
    @SerializedName(Constants.FROM_CITY)
    private PackageCity mSource;

    @Expose
    @SerializedName(Constants.TO_CITY)
    private PackageCity mDestination;

    @Expose
    @SerializedName(Constants.DATE)
    private String mDate;

    @Expose
    @SerializedName(Constants.TIME)
    private String mTime;

    @Expose
    @SerializedName(Constants.VEHICLE)
    private Vehicle mVehicle;

    @Expose
    @SerializedName(Constants.DRIVER)
    private Driver mDriver;


    public long getId() {
        return mId;
    }

    public String getNumber() {
        return mNumber;
    }

    public AirLine getAirLine() {
        return mAirLine;
    }

    public PackageCity getSource() {
        return mSource;
    }

    public PackageCity getDestination() {
        return mDestination;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public Vehicle getVehicle() {
        return mVehicle;
    }

    public Driver getDriver() {
        return mDriver;
    }
}
