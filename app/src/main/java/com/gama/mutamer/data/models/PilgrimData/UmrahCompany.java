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
public class UmrahCompany extends RealmObject implements Serializable {

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
    @SerializedName(Constants.PHONE_NUMBER)
    private String mPhoneNumber;


    @Expose
    @SerializedName(Constants.EMAIL_ADDRESS)
    private String mEmailAddress;

    @Expose
    @SerializedName(Constants.ADDRESS)
    private String mAddress;

    @Expose
    @SerializedName(Constants.WEB_SITE)
    private String mWebSite;

    public long getId() {
        return Id;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public String getArabicName() {
        return ArabicName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getWebSite() {
        return mWebSite;
    }

    public String getName(String lang) {
        return lang.equalsIgnoreCase(LanguageHelper.LANGUAGE_ENGLISH) ? getEnglishName() : getArabicName();
    }
}
