package com.gama.mutamer.data.models;

import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 8/8/17.
 * Release the GEEK
 */

public class Issue extends RealmObject {

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
    @SerializedName(Constants.DATE)
    private Date CreatedDate;

    @Expose
    @SerializedName(Constants.LAST_UPDATE_DATE)
    private Date LastUpdateDate;

    @Expose
    @SerializedName(Constants.STATUS)
    private int Status;

    @Expose
    @SerializedName(Constants.HAS_ATTACHMENTS)
    private boolean HasAttachments;


    public long getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getBody() {
        return Body;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public Date getLastUpdate() {
        return LastUpdateDate;
    }

    public int getStatus() {
        return Status;
    }

    public boolean isHasAttachments() {
        return HasAttachments;
    }
}
