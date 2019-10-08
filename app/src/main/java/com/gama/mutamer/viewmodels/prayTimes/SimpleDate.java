package com.gama.mutamer.viewModels.prayTimes;

import java.util.GregorianCalendar;

/**
 * Created by mustafa on 5/15/18.
 * Release the GEEK
 */
public class SimpleDate {

    int day;

    int month;

    int year;


    public SimpleDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public SimpleDate(GregorianCalendar gCalendar) {
        this.day = gCalendar.get(GregorianCalendar.DATE);
        this.month = gCalendar.get(GregorianCalendar.MONTH) + 1;
        this.year = gCalendar.get(GregorianCalendar.YEAR);
    }

    public SimpleDate copy() {
        return new SimpleDate(day, month, year);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
