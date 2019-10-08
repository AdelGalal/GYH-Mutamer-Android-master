package com.gama.mutamer.data.models.content;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 8/3/17.
 * Release the GEEK
 */

public class DuaaCategory extends RealmObject {

    @PrimaryKey
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

    public String getName(String language) {
        return language.equals(LanguageHelper.LANGUAGE_ARABIC) ? ArabicName : EnglishName;
    }


    public void setId(long id) {
        Id = id;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public String getArabicName() {
        return ArabicName;
    }



}
