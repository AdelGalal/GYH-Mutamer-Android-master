package com.gama.mutamer.data.models.content;

import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 12/4/18.
 * Release the GEEK
 */
public class Slider extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName(Constants.ID)
    private long Id;

    @Expose
    @SerializedName(Constants.TITLE)
    private String Title;

    @Expose
    @SerializedName(Constants.BODY)
    private String Body;

    @Expose
    @SerializedName(Constants.URL)
    private String Url;

    @Expose
    @SerializedName(Constants.IS_DELETED)
    private boolean Deleted;

    public long getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getBody() {
        return Body;
    }

    public String getUrl() {
        return Url;
    }
}
