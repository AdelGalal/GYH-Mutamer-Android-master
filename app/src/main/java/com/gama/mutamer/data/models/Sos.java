package com.gama.mutamer.data.models;

import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 7/27/17.
 * Release the GEEK
 */

public class Sos extends RealmObject implements Serializable {

    /***
     * Fields
     */
    //TODO: Documentation
    @PrimaryKey
    @Expose
    @SerializedName(Constants.ID)
    private int Id;

    //TODO: Documentation
    @Expose
    @SerializedName(Constants.DATE)
    private Date Date;

    //TODO: Documentation
    @Expose
    @SerializedName(Constants.LATITUDE)
    private double Latitude;

    //TODO: Documentation
    @Expose
    @SerializedName(Constants.LONGITUDE)
    private double Longitude;

    //TODO: Documentation
    @Expose
    @SerializedName(Constants.STATUS)
    private int Status;

    //TODO: Documentation
    @Expose
    @SerializedName(Constants.LAST_UPDATE_DATE)
    private Date LastUpdateDate;

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public Date getLastUpdate() {
        return LastUpdateDate;
    }


}
