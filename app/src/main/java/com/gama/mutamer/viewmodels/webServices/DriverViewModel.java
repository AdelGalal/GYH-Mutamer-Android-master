package com.gama.mutamer.viewModels.webServices;

import com.gama.mutamer.helpers.LanguageHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mustafa on 8/22/16.
 * Release the GEEK
 */
public class DriverViewModel  {

    @Expose
    @SerializedName("MobileNumber")
    private String mMobileNumber;

    @Expose
    @SerializedName("Location")
    private LocViewModel mLocation;

    @Expose
    @SerializedName("Id")
    private int Id;

    @Expose
    @SerializedName("EnglishName")
    private String EnglishName;

    @Expose
    @SerializedName("ArabicName")
    private String ArabicName;

    @Expose
    @SerializedName("CurrancyCode")
    private String CurrancyCode;

    public int getId() {
        return Id;
    }


    public String getEnglishName() {
        return EnglishName;
    }

    public String getArabicName() {
        return ArabicName;
    }

    public String getCurrancyCode() {
        return CurrancyCode;
    }

    public String getName(String lang) {
        return lang.equals(LanguageHelper.LANGUAGE_ARABIC )  ? getArabicName() : getEnglishName();
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public LocViewModel getLocation() {
        return mLocation;
    }


}
