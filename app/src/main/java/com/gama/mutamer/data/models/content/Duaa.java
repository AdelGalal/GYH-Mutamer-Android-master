package com.gama.mutamer.data.models.content;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/***
 * Duaa Database Model
 */
public class Duaa extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName(Constants.ID)
    private long Id;

    @Expose
    @SerializedName(Constants.CATEGORY_ID)
    private long CategoryId;

    @Expose
    @SerializedName(Constants.ENGLISH_NAME)
    private String EnglishName;

    @Expose
    @SerializedName(Constants.ARABIC_NAME)
    private String ArabicName;

    @Expose
    @SerializedName(Constants.ENGLISH_BODY)
    private String EnglishBody;

    @Expose
    @SerializedName(Constants.ARABIC_BODY)
    private String ArabicBody;

    @Expose
    @SerializedName(Constants.TIME)
    private Date Time;

    @Expose
    @SerializedName(Constants.LATITUDE)
    private double Latitude;

    @Expose
    @SerializedName(Constants.LONGITUDE)
    private double Longitude;

    @Expose
    @SerializedName(Constants.LAST_UPDATE_DATE)
    private Date LastUpdateDate;

    @Expose
    @SerializedName(Constants.IS_DELETED)
    private boolean Deleted;


    private boolean Alert;

    private boolean Favorites;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getEnglishName() {
        return EnglishName;
    }


    public String getArabicName() {
        return ArabicName;
    }


    public String getEnglishBody() {
        return EnglishBody;
    }


    public String getArabicBody() {
        return ArabicBody;
    }


    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }


    public boolean isAlert() {
        return Alert;
    }

    public void setAlert(boolean alert) {
        Alert = alert;
    }

    public boolean isFavorites() {
        return Favorites;
    }

    public void setFavorites(boolean favorites) {
        Favorites = favorites;
    }

    public String getName(String language) {
        return language.equals(LanguageHelper.LANGUAGE_ARABIC) ? getArabicName() : getEnglishName();
    }

    public String getBody(String language) {
        return language.equals(LanguageHelper.LANGUAGE_ARABIC) ? getArabicBody() : getEnglishBody();
    }



}
