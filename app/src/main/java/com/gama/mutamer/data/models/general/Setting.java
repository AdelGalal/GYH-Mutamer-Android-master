package com.gama.mutamer.data.models.general;

import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Setting extends RealmObject implements Serializable {

    @PrimaryKey
    private int Id;

    @Expose
    @SerializedName(Constants.LANGUAGE)
    private int Language;

    @Expose
    @SerializedName(Constants.PRAYER_METHOD)
    private int PrayerMethod;

    @Expose
    @SerializedName(Constants.UNIT_METHOD)
    private int UnitType;

    @Expose
    @SerializedName(Constants.PRAYER_NOTIFICATION_TYPE)
    private int PrayerNotificationType;

    @Expose
    @SerializedName(Constants.LAST_UPDATE_DATE)
    private Date LastUpdateDate;


    public int getPrayerMethod() {
        return PrayerMethod;
    }

    public void setPrayerMethod(int prayerMethod) {
        PrayerMethod = prayerMethod;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUnitType() {
        return UnitType;
    }

    public void setUnitType(int unitType) {
        UnitType = unitType;
    }

    public int getPrayerNotificationType() {
        return PrayerNotificationType;
    }

    public void setPrayerNotificationType(int prayerNotificationType) {
        PrayerNotificationType = prayerNotificationType;
    }

    public int getLanguage() {
        return Language;
    }

    public void setLanguage(int language) {
        Language = language;
    }

    public int getId() {
        return Id;
    }

    public Date getLastUpdateDate() {
        return LastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        LastUpdateDate = lastUpdateDate;
    }
}
