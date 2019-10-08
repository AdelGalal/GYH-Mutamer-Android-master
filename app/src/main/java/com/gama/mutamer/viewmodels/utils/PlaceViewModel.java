package com.gama.mutamer.viewModels.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gama.mutamer.helpers.LanguageHelper;

import java.io.Serializable;

/**
 * Created by mustafa on 8/14/16.
 * Release the GEEK
 */
public class PlaceViewModel implements Serializable {

    @Expose
    @SerializedName("Id")
    private int mId;

    @Expose
    @SerializedName("EnglishName")
    private String mEnglishName;

    @Expose
    @SerializedName("ArabicName")
    private String mArabicName;

    @Expose
    @SerializedName("EnglishDescription")
    private String mEnglishDescription;

    @Expose
    @SerializedName("ArabicDescription")
    private String mArabicDescription;

    @Expose
    @SerializedName("Latitude")
    private double mLatitude;

    @Expose
    @SerializedName("Longitude")
    private double mLongitude;

    public String getName(String lang) {
        return lang.equals(LanguageHelper.LANGUAGE_ARABIC )  ? getArabicName() : getEnglishName();
    }

    public String getDescription(String lang) {
        return lang.equals(LanguageHelper.LANGUAGE_ARABIC )  ? getArabicDescription() : getEnglishDescription();
    }

    public int getId() {
        return mId;
    }

    public String getEnglishName() {
        return mEnglishName;
    }

    public String getArabicName() {
        return mArabicName;
    }

    public String getEnglishDescription() {
        return mEnglishDescription;
    }

    public String getArabicDescription() {
        return mArabicDescription;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }


}
