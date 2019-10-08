package com.gama.mutamer.viewModels.utils;

import com.gama.mutamer.helpers.LanguageHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mustafa on 8/22/17.
 * Release the GEEK
 */

public class NumberCategoryViewModel  {

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


    @Expose
    @SerializedName("Numbers")
    private ArrayList<ImportantNumberViewModel> mNumbers;



    public ArrayList<ImportantNumberViewModel> getNumbers() {
        return mNumbers;
    }
}
