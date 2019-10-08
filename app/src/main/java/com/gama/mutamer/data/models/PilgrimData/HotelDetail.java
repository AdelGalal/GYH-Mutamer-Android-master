package com.gama.mutamer.data.models.PilgrimData;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 12/10/18.
 * Release the GEEK
 */
public class HotelDetail extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName(Constants.ID)
    private long Id;

    @Expose
    @SerializedName(Constants.ENGLISH_NAME)
    private String EnglishName;


    @Expose
    @SerializedName(Constants.ARABIC_NAME)
    private String ArabicName;

    @Expose
    @SerializedName(Constants.CITY_ID)
    private long mCityId;

    @Expose
    @SerializedName(Constants.CITY_ARABIC_NAME)
    private String mCityArabicName;

    @Expose
    @SerializedName(Constants.CITY_ENGLISH_NAME)
    private String mCityEnglishName;

    public long getId() {
        return Id;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public String getArabicName() {
        return ArabicName;
    }

    public long getCityId() {
        return mCityId;
    }

    public String getCityArabicName() {
        return mCityArabicName;
    }

    public String getCityEnglishName() {
        return mCityEnglishName;
    }


    public String getName(String language) {
        return language.equalsIgnoreCase(LanguageHelper.LANGUAGE_ENGLISH) ? getEnglishName() : getArabicName();
    }

    public String getCityName(String language) {
        return language.equalsIgnoreCase(LanguageHelper.LANGUAGE_ENGLISH) ? getCityEnglishName() : getCityArabicName();
    }
}
