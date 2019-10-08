package com.gama.mutamer.viewModels.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gama.mutamer.utils.Constants;

import java.io.Serializable;

public class AppViewModel implements Serializable {

    @Expose
    @SerializedName(Constants.NAME)
    private String Name;

    @Expose
    @SerializedName(Constants.IMAGE_NAME)
    private String ImageName;

    @Expose
    @SerializedName(Constants.DESCRIPTION)
    private String Description;

    @Expose
    @SerializedName(Constants.URL)
    private String Url;

    public String getName() {
        return Name;
    }

    public String getImageName() {
        return ImageName;
    }

    public String getDescription() {
        return Description;
    }

    public String getUrl() {
        return Url;
    }
}
