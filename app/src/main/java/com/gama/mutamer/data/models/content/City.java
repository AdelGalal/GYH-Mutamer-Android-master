package com.gama.mutamer.data.models.content;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class City extends RealmObject {


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
        return language.equals(LanguageHelper.LANGUAGE_ARABIC) ? getArabicName() : getEnglishName();
    }

    public void setId(long id) {
        Id = id;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public void setArabicName(String arabicName) {
        ArabicName = arabicName;
    }

}
