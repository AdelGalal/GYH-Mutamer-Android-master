package com.gama.mutamer.data.models.content;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 12/3/18.
 * Release the GEEK
 */
public class PlaceCategory extends RealmObject implements Serializable {

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
    @SerializedName(Constants.IS_DELETED)
    private boolean Deleted;

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
}
