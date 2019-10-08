package com.gama.mutamer.viewModels.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.gama.mutamer.utils.Constants;

import io.realm.RealmList;

public class NationalitiesList {
    @Expose
    @SerializedName(Constants.COUNTRIES)
    private RealmList<NameViewModel> Nationalties;

    public RealmList<NameViewModel> getNationalties() {
        return Nationalties;
    }
}
