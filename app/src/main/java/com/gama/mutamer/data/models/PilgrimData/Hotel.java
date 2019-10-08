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
public class Hotel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName(Constants.ID)
    private long Id;

    @Expose
    @SerializedName(Constants.HOTEL_DETAILS)
    private HotelDetail mDetail;


    @Expose
    @SerializedName(Constants.START_DATE)
    private String mStartDate;

    @Expose
    @SerializedName(Constants.END_DATE)
    private String mEndDate;

    public long getId() {
        return Id;
    }

    public HotelDetail getDetail() {
        return mDetail;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getEndDate() {
        return mEndDate;
    }
}
