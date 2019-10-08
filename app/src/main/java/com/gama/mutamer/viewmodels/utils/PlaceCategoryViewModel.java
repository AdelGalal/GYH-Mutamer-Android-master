package com.gama.mutamer.viewModels.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gama.mutamer.helpers.LanguageHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mustafa on 8/14/16.
 * Release the GEEK
 */
public class PlaceCategoryViewModel implements Serializable {

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
    @SerializedName("Items")
    private ArrayList<PlaceViewModel> mItems;

    public String getName(String lang) {
        return lang.equals(LanguageHelper.LANGUAGE_ARABIC )  ? getArabicName() : getEnglishName();
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

    public ArrayList<PlaceViewModel> getItems() {
        return mItems;
    }
}
