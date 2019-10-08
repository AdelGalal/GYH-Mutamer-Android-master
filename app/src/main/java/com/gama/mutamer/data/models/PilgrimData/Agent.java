package com.gama.mutamer.data.models.PilgrimData;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 11/27/18.
 * Release the GEEK
 */
public class Agent extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName(Constants.ID)
    private long Id;

    @Expose
    @SerializedName(Constants.PHONE_NUMBER)
    private String mPhoneNumber;


    @Expose
    @SerializedName(Constants.ENGLISH_NAME)
    private String EnglishName;


    @Expose
    @SerializedName(Constants.ARABIC_NAME)
    private String ArabicName;

    @Expose
    @SerializedName(Constants.EMAIL_ADDRESS)
    private String mEmailAddress;

    @Expose
    @SerializedName(Constants.CONTACT_PERSON)
    private String mContactPerson;

    @Expose
    @SerializedName(Constants.MOBILE_NUMBER)
    private String mMobileNumber;


    public long getId() {
        return Id;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public String getArabicName() {
        return ArabicName;
    }


    public String getName(String language) {
        return language.equalsIgnoreCase(LanguageHelper.LANGUAGE_ENGLISH) ? getEnglishName() : getArabicName();
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public String getContactPerson() {
        return mContactPerson;
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }
}
