package com.gama.mutamer.data.models.PilgrimData;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by mustafa on 12/10/18.
 * Release the GEEK
 */
public class Driver extends RealmObject implements Serializable {


    @Expose
    @SerializedName(Constants.ENGLISH_NAME)
    private String EnglishName;


    @Expose
    @SerializedName(Constants.ARABIC_NAME)
    private String ArabicName;

    @Expose
    @SerializedName(Constants.NUMBER)
    private String mNumber;

    @Expose
    @SerializedName(Constants.NATIONALITY_ID)
    private long mNationalityId;

    public String getEnglishName() {
        return EnglishName;
    }

    public String getArabicName() {
        return ArabicName;
    }

    public String getNumber() {
        return mNumber;
    }

    public long getNationalityId() {
        return mNationalityId;
    }

    /***
     * Helper Methods
     */
    public String getName(String language) {
        return language.equalsIgnoreCase(LanguageHelper.LANGUAGE_ENGLISH) ? getEnglishName() : getArabicName();
    }
}
