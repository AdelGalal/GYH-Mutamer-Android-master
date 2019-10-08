package com.gama.mutamer.viewModels.shared;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mustafa on 8/5/17.
 * Release the GEEK
 */

public class CalendarDay {
    private Date mDate;
    private ArrayList<DayDetail> mDetails;

    public CalendarDay() {
        setDetails(new ArrayList<DayDetail>());
    }

    public CalendarDay(Date date, ArrayList<DayDetail> details) {
        setDate(date);
        setDetails(details);
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public ArrayList<DayDetail> getDetails() {
        return mDetails;
    }

    public void setDetails(ArrayList<DayDetail> details) {
        mDetails = details;
    }
}
