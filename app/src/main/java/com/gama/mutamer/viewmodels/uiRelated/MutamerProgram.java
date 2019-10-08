package com.gama.mutamer.viewModels.uiRelated;

import com.gama.mutamer.data.models.PilgrimData.Arrival;
import com.gama.mutamer.data.models.PilgrimData.Departure;
import com.gama.mutamer.data.models.PilgrimData.Hotel;
import com.gama.mutamer.data.models.PilgrimData.Transportation;

import java.util.ArrayList;

/**
 * Created by mustafa on 12/10/18.
 * Release the GEEK
 */
public class MutamerProgram {


    private ArrayList<Hotel> mHotels;


    private ArrayList<Transportation> mTransportations;


    private Arrival mArrival;


    private Departure mDeparture;

    public ArrayList<Hotel> getHotels() {
        return mHotels;
    }

    public void setHotels(ArrayList<Hotel> hotels) {
        mHotels = hotels;
    }

    public ArrayList<Transportation> getTransportations() {
        return mTransportations;
    }

    public void setTransportations(ArrayList<Transportation> transportations) {
        mTransportations = transportations;
    }

    public Arrival getArrival() {
        return mArrival;
    }

    public void setArrival(Arrival arrival) {
        mArrival = arrival;
    }

    public Departure getDeparture() {
        return mDeparture;
    }

    public void setDeparture(Departure departure) {
        mDeparture = departure;
    }
}
