package com.gama.mutamer.viewModels.shared;

import com.gama.mutamer.helpers.LanguageHelper;

import java.io.Serializable;

public class Company implements Serializable {

    private String ArabicName;
    private String EnglishName;
    private String Phone;


    public Company(String arabicName, String englishName, String phone) {
        setArabicName(arabicName);
        setEnglishName(englishName);
        setPhone(phone);

    }

    public String getArabicName() {
        return ArabicName;
    }

    public void setArabicName(String arabicName) {
        ArabicName = arabicName;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


    public String getName(String lang) {
        return lang.equals(LanguageHelper.LANGUAGE_ARABIC )  ? getArabicName() : getEnglishName();
    }
}
