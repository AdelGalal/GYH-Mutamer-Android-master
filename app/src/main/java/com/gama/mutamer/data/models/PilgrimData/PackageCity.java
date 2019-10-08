package com.gama.mutamer.data.models.PilgrimData;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by mustafa on 12/11/18.
 * Release the GEEK
 */
public class PackageCity extends RealmObject implements Serializable {


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
    @SerializedName(Constants.COUNTRY_ID)
    private long CountryId;


    public long getId() {
        return Id;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public String getArabicName() {
        return ArabicName;
    }

    /***
     * Helper Methods
     */
    public String getName(String language) {
        return language.equalsIgnoreCase(LanguageHelper.LANGUAGE_ENGLISH) ? getEnglishName() : getArabicName();
    }

    public long getCountryId() {
        return CountryId;
    }
}
