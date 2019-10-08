package com.gama.mutamer.viewModels.shared;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gama.mutamer.viewModels.utils.NameViewModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mustafa on 8/22/17.
 * Release the GEEK
 */

public class CountriesResponse implements Serializable {

    @Expose
    @SerializedName("Countries")
    private ArrayList<NameViewModel> Countries;

    public CountriesResponse() {
        setCountries(new ArrayList<NameViewModel>());
    }

    public ArrayList<NameViewModel> getCountries() {
        return Countries;
    }

    public void setCountries(ArrayList<NameViewModel> countries) {
        Countries = countries;
    }
}
