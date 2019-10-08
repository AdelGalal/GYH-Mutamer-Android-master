package com.gama.mutamer.webServices.responses;

import com.gama.mutamer.data.models.PilgrimData.Agent;
import com.gama.mutamer.data.models.PilgrimData.Arrival;
import com.gama.mutamer.data.models.PilgrimData.Departure;
import com.gama.mutamer.data.models.PilgrimData.Hotel;
import com.gama.mutamer.data.models.PilgrimData.Pilgrim;
import com.gama.mutamer.data.models.PilgrimData.Transportation;
import com.gama.mutamer.data.models.PilgrimData.UmrahCompany;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mustafa on 12/10/18.
 * Release the GEEK
 */
public class PilgrimProfileResponse extends BaseServiceResponse {

    @Expose
    @SerializedName(Constants.PILGRIM)
    private Pilgrim mPilgrim;

    @Expose
    @SerializedName(Constants.AGENT)
    private Agent mAgent;

    @Expose
    @SerializedName(Constants.UMRAH_COMPANY)
    private UmrahCompany mCompany;

    @Expose
    @SerializedName(Constants.HOTELS)
    private ArrayList<Hotel> mHotels;

    @Expose
    @SerializedName(Constants.TRANSPORTATION)
    private ArrayList<Transportation> mTransportations;

    @Expose
    @SerializedName(Constants.ARRIVAL)
    private Arrival mArrival;

    @Expose
    @SerializedName(Constants.DEPARTUTE)
    private Departure mDeparture;


    public Pilgrim getPilgrim() {
        return mPilgrim;
    }

    public Agent getAgent() {
        return mAgent;
    }

    public UmrahCompany getCompany() {
        return mCompany;
    }

    public ArrayList<Hotel> getHotels() {
        return mHotels;
    }

    public ArrayList<Transportation> getTransportations() {
        return mTransportations;
    }

    public Arrival getArrival() {
        return mArrival;
    }

    public Departure getDeparture() {
        return mDeparture;
    }
}
