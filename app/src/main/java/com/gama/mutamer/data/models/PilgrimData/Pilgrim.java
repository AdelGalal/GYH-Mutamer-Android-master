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
public class Pilgrim extends RealmObject implements Serializable {


    @PrimaryKey
    @Expose
    @SerializedName(Constants.ID)
    private long mId;

    @Expose
    @SerializedName(Constants.ENGLISH_NAME)
    private String EnglishName;


    @Expose
    @SerializedName(Constants.ARABIC_NAME)
    private String ArabicName;

    @Expose
    @SerializedName(Constants.GENDER)
    private int mGender;

    @Expose
    @SerializedName(Constants.AGE)
    private int mAge;

    @Expose
    @SerializedName(Constants.DATE_OF_BIRTH)
    private String mDateOfBirth;

    @Expose
    @SerializedName(Constants.BLOOD_TYPE)
    private String mBloodType;

    @Expose
    @SerializedName(Constants.PASSPORT_NUMBER)
    private String mPassportNumber;

    @Expose
    @SerializedName(Constants.MOFA_NUMBER)
    private long mMofaNumber;

    @Expose
    @SerializedName(Constants.VISA_NUMBER)
    private long mVisaNumber;

    @Expose
    @SerializedName(Constants.MOI_NUMBER)
    private long mMoiNumber;

    @Expose
    @SerializedName(Constants.CITY)
    private String City;

    @Expose
    @SerializedName(Constants.NATIONALITY)
    private Nationality mNationality;


    public long getId() {
        return mId;
    }

    public int getGender() {
        return mGender;
    }

    public String getAge() {
        return String.valueOf(mAge);
    }

    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    public String getBloodType() {
        return mBloodType;
    }

    public String getPassportNumber() {
        return mPassportNumber;
    }

    public String getMofaNumber() {
        return String.valueOf(mMofaNumber);
    }

    public String getVisaNumber() {
        return String.valueOf(mVisaNumber);
    }

    public String getMoiNumber() {
        return String.valueOf(mMoiNumber);
    }

    public Nationality getNationality() {
        return mNationality;
    }

    public String getCity() {
        return City;
    }

    public String getEnglishName() {
        return EnglishName.trim().length() > 0 ? EnglishName : ArabicName;
    }

    public String getArabicName() {
        return ArabicName.trim().length() > 0 ? ArabicName : EnglishName;
    }

    public String getName(String lang) {
        return lang.equalsIgnoreCase(LanguageHelper.LANGUAGE_ENGLISH) ? getEnglishName() : getArabicName();
    }
}
