package com.gama.mutamer.webServices.requests;

import com.gama.mutamer.R;

/**
 * Created by mustafa on 8/11/16.
 * Release the GEEK
 */
public class GooglePlaceRequest {
    private Double mLat, mLng;
    private int mIcon = R.drawable.police_station;
    private int mRadius,  mFullIcon;
    private String Language,mType;

    public GooglePlaceRequest(Double lat, Double lng, int radius, String type, int icon, int fullIcon, String language) {
        setLat(lat);
        setLng(lng);
        setRadius(radius);
        setType(type);
        setIcon(icon);
        setFullIcon(fullIcon);
        setLanguage(language);
    }

    public String getServiceUrl() {
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + getLat() + "," + getLng() + "&radius=" + getRadius() + "&types=" + getType() + "&sensor=true&language=" + getLanguage() + "&key=AIzaSyB5LX7VTsbNMGGB6zFJVYUQ50YI09yIS74";

    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public Double getLng() {
        return mLng;
    }

    public void setLng(Double lng) {
        mLng = lng;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        mRadius = radius;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public int getFullIcon() {
        return mFullIcon;
    }

    public void setFullIcon(int fullIcon) {
        mFullIcon = fullIcon;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }
}
