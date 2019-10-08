package com.gama.mutamer.viewModels.shared;

/**
 * Created by mustafa on 5/15/18.
 * Release the GEEK
 */
/***
 * Language Class to used for app localization
 */
public class Language {
    private int mNameResource;
    private int mImage;

    public Language() {

    }

    public Language(int nameResource, int image) {
        setNameResource(nameResource);
        setImage(image);
    }

    public int getNameResource() {
        return mNameResource;
    }

    public void setNameResource(int nameResource) {
        mNameResource = nameResource;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }
}

