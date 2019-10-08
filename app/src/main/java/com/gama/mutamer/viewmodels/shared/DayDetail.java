package com.gama.mutamer.viewModels.shared;

/**
 * Created by mustafa on 8/10/17.
 * Release the GEEK
 */

public class DayDetail {
    private int mIcon;
    private String mTitle;
    private int mType;
    private long mRef;

    public DayDetail() {

    }

    public DayDetail(int icon, String title, int type, long ref) {
        setIcon(icon);
        setTitle(title);
        setType(type);
        setRef(ref);
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
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

    public long getRef() {
        return mRef;
    }

    public void setRef(long ref) {
        mRef = ref;
    }
}
