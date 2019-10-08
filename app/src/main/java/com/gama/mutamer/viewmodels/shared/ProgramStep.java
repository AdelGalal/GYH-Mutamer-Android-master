package com.gama.mutamer.viewModels.shared;

import java.util.Date;

/**
 * Created by mustafa on 8/9/17.
 * Release the GEEK
 */

public class ProgramStep {
    private String mCityName, mDetails, mTitle;
    private int mType;
    private Date mStartDate, mEndDate;
    private double mLat, mLng;


    public ProgramStep() {

    }

    public ProgramStep(int type, String cityName, String details, Date startDate, Date endDate, String title, double lat, double lng) {
        setCityName(cityName);
        setDetails(details);
        setEndDate(endDate);
        setStartDate(startDate);
        setType(type);
        setTitle(title);
        setLat(lat);
        setLng(lng);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLng() {
        return mLng;
    }

    public void setLng(double lng) {
        mLng = lng;
    }
}
