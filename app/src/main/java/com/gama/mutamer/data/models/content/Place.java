package com.gama.mutamer.data.models.content;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 8/3/17.
 * Release the GEEK
 */

public class Place extends RealmObject implements Serializable {

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


    public String getName(String mLanguage) {
        return mLanguage.equals(LanguageHelper.LANGUAGE_ARABIC) ? ArabicName : EnglishName;
    }

    public String getBody(String mLanguage) {
        return mLanguage.equals(LanguageHelper.LANGUAGE_ARABIC) ? ArabicBody : EnglishBody;
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


    public Date getLastUpdate() {
        return LastUpdateDate;
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


    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public void setArabicName(String arabicName) {
        ArabicName = arabicName;
    }

    public void setEnglishBody(String englishBody) {
        EnglishBody = englishBody;
    }

    public void setArabicBody(String arabicBody) {
        ArabicBody = arabicBody;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        LastUpdateDate = lastUpdateDate;
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

    public Date getLastUpdateDate() {
        return LastUpdateDate;
    }
}
