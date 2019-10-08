package com.gama.mutamer.webServices.responses;

import android.location.Location;

import com.gama.mutamer.viewModels.webServices.GooglePlaceResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mustafa on 8/11/16.
 * Release the GEEK
 */
public class GooglePlacesResponse {
    @Expose
    @SerializedName("results")
    private ArrayList<GooglePlaceResult> mPlaces;

    public ArrayList<GooglePlaceResult> getPlaces() {
        return mPlaces;
    }

    public void setPlaces(ArrayList<GooglePlaceResult> places) {
        mPlaces = places;
    }

    public void UpdateTypes(String type) {
        if (getPlaces() != null)
            for (GooglePlaceResult item : getPlaces())
                item.setType(type);
    }

    public void UpdateIcons(int icon) {
        if (getPlaces() != null) {
            for (GooglePlaceResult item : getPlaces()) {
                item.setIcon(icon);
                item.setFullIcon(icon);
            }
        }
    }

    public void UpdateFullIcons(int icon) {
        if (getPlaces() != null)
            for (GooglePlaceResult item : getPlaces())
                item.setFullIcon(icon);
    }

    public void UpdateDistance(Location location) {
        if (getPlaces() != null)
            for (GooglePlaceResult item : getPlaces())
                item.UpdateDistance(location);
    }
}
