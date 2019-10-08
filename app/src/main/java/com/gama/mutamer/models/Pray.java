package com.gama.mutamer.models;


import java.io.Serializable;
import java.util.Date;

public class Pray implements Serializable {
    private String mName, mDate;
    private boolean mEnabled, mCurrent;
    private int mId;
    private Date mDateTime;

    public Date getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Date dateTime) {
        mDateTime = dateTime;
    }

    public boolean isCurrent() {
        return mCurrent;
    }

    public void setCurrent(boolean current) {
        mCurrent = current;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }
}
