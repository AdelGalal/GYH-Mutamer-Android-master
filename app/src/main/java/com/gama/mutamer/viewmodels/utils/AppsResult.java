package com.gama.mutamer.viewModels.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gama.mutamer.utils.Constants;

import java.io.Serializable;
import java.util.List;

public class AppsResult implements Serializable {
    @Expose
    @SerializedName(Constants.APPS)
    private List<AppViewModel> Apps;

    public List<AppViewModel> getApps() {
        return Apps;
    }
}
