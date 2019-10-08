package com.gama.mutamer.data.models.content;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ImportantNumberCategory extends RealmObject {

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

    public void setId(long id) {
        Id = id;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public void setArabicName(String arabicName) {
        ArabicName = arabicName;
    }


    public String getName(String lang) {
        return lang.equals(LanguageHelper.LANGUAGE_ARABIC) ? getArabicName() : getEnglishName();
    }
}
