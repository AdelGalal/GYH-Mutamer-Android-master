package com.gama.mutamer.viewModels.utils;

import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gama.mutamer.helpers.LanguageHelper;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 7/11/16.
 * Release the GEEK
 */
public class NameViewModel extends RealmObject implements Serializable {

    @Expose
    @SerializedName(Constants.ID)
    @PrimaryKey
    private int Id;

    @Expose
    @SerializedName(Constants.ENGLISH_NAME)
    private String EnglishName;

    @Expose
    @SerializedName(Constants.ARABIC_NAME)
    private String ArabicName;

    @Expose
    @SerializedName(Constants.CURRENCY_CODE)
    private String CurrencyCode;

    public long getId() {
        return Id;
    }


    public String getEnglishName() {
        return EnglishName;
    }

    public String getArabicName() {
        return ArabicName;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public String getName(String lang) {
        return lang.equals(LanguageHelper.LANGUAGE_ARABIC )  ? getArabicName() : getEnglishName();
    }
}
