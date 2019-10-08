package com.gama.mutamer.viewModels.webServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mustafa on 8/11/16.
 * Release the GEEK
 */
public class GeometryViewModel implements Serializable {

    public GeometryViewModel(){

    }

    public GeometryViewModel(LocationViewModel locationViewModel){
        setmLocation(locationViewModel);
    }
    @Expose
    @SerializedName("location")
    private LocationViewModel mLocation;

    public LocationViewModel getLocation() {
        return mLocation;
    }

    public void setmLocation(LocationViewModel mLocation) {
        this.mLocation = mLocation;
    }
}
