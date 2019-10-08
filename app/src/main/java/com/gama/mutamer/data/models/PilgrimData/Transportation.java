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
public class Transportation extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName(Constants.ID)
    private long Id;

    @Expose
    @SerializedName(Constants.FROM)
    private NameModel mSource;

    @Expose
    @SerializedName(Constants.TO)
    private NameModel mDestination;

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
        return Id;
    }

    public NameModel getSource() {
        return mSource;
    }

    public NameModel getDestination() {
        return mDestination;
    }

    public String getDate() {
        return mDate;
    }

    public Vehicle getVehicle() {
        return mVehicle;
    }

    public Driver getDriver() {
        return mDriver;
    }

    public String getTime() {
        return mTime;
    }
}
