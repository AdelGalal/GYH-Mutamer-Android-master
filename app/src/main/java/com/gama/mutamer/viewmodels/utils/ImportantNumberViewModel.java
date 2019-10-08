package com.gama.mutamer.viewModels.utils;

import com.gama.mutamer.data.models.content.ImportantNumber;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mustafa on 8/22/17.
 * Release the GEEK
 */

public class ImportantNumberViewModel  implements Serializable {


    public ImportantNumberViewModel(){

    }

    public ImportantNumberViewModel(ImportantNumber number){
        setId(number.getId());
        setCategoryId(number.getCategoryId());
        setArabicName(number.getArabicName());
        setEnglishName(number.getEnglishName());
        setNumber(number.getNumber());
    }

    @Expose
    @SerializedName(Constants.ID)
    public long Id;

    @Expose
    @SerializedName(Constants.CATEGORY_ID)
    public long CategoryId;

    @Expose
    @SerializedName(Constants.ENGLISH_NAME)
    public String EnglishName;

    @Expose
    @SerializedName(Constants.ARABIC_NAME)
    public String ArabicName;

    @Expose
    @SerializedName(Constants.NUMBER)
    public String Number;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(long categoryId) {
        CategoryId = categoryId;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getArabicName() {
        return ArabicName;
    }

    public void setArabicName(String arabicName) {
        ArabicName = arabicName;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getName(String lang) {
        return lang.equals(LanguageHelper.LANGUAGE_ARABIC )  ? getArabicName() : getEnglishName();
    }


}
