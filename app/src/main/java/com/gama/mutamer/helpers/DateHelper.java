package com.gama.mutamer.helpers;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/***
 * Date Helper for all related Date based methods
 */
public class DateHelper {


    public static Date getDateWithExtraMillisecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime() + 1);
        return c.getTime();
    }

    public static String ToDotNetDate(Date date, Locale locale) {
        if (date == null) return null;
        SimpleDateFormat format;

        format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.UK);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public static String formatDateTime(Date date, Locale locale) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format;

        format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa", locale);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public static String formatDate(Date date, Locale locale) {
        if (date == null) return null;
        SimpleDateFormat format;

        format = new SimpleDateFormat("dd-MM-yyyy", locale);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public static String formatMonth(Date date, Locale locale) {
        if (date == null) return null;
        SimpleDateFormat format;

        format = new SimpleDateFormat("MMM", locale);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public static String formatYear(Date date, Locale locale) {
        if (date == null) return null;
        SimpleDateFormat format;

        format = new SimpleDateFormat("yyyy", locale);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public static String formatDayName(Date date, Locale locale) {
        if (date == null) return null;
        SimpleDateFormat format;

        format = new SimpleDateFormat("EEE", locale);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public static String formatMonthName(Date date, Locale locale) {
        if (date == null) return null;
        SimpleDateFormat format;

        format = new SimpleDateFormat("MMMM", locale);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public static String formatDay(Date date, Locale locale) {
        if (date == null) return null;
        SimpleDateFormat format;

        format = new SimpleDateFormat("dd", locale);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public static String formatJustTime(Date date, Locale locale) {
        SimpleDateFormat format = new SimpleDateFormat("kk:mm", locale);
        //format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public static String formatTime(long date, Locale locale) {
        long seconds = date / 1000;
        long mins = seconds / 60;
        long hours = mins / 60;
        long minutes = mins - (hours * 60);
        String hoursString = hours <= 9 ? String.format(locale, "%d%d", 0, hours) : String.format(locale, "%d", hours);
        String minutesString = minutes <= 9 ? String.format(locale, "%d%d", 0, minutes) : String.format(locale, "%d", minutes);
        return hoursString + ":" + minutesString;
    }

    public static String getMonths(Date date1, Date date2, Locale locale) {
        Date date = new Date(date1.getTime() - date2.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format(locale, "%d", calendar.get(Calendar.MONTH));
//        SimpleDateFormat format = new SimpleDateFormat("MM", locale);
//        format.setTimeZone(TimeZone.getTimeZone("AST"));
//        return format.format(calendar.);
    }

    public static int getDays(Date date1, Date date2) {
        Date date = new Date(date1.getTime() - date2.getTime());
        SimpleDateFormat format = new SimpleDateFormat("dd", Locale.UK);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        String result = format.format(date);
        return Integer.parseInt(result);
    }

    public static long getMiliseconds(Date date1, Date date2) {
        Log.v("MILI", date1.getTime() + "");
        Log.v("MILI", date2.getTime() + "");
        Date date = new Date(date1.getTime() - date2.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("ss", Locale.UK);
//        format.setTimeZone(TimeZone.getTimeZone("AST"));
//        String result = format.format(date);
        return date.getTime();
    }

    public static String getDays(Date date1, Date date2, Locale locale) {
        Date date = new Date(date1.getTime() - date2.getTime());
        SimpleDateFormat format = new SimpleDateFormat("dd", locale);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public static String getHours(Date date1, Date date2, Locale locale) {
        Date date = new Date(date1.getTime() - date2.getTime());
        SimpleDateFormat format = new SimpleDateFormat("hh", locale);
        format.setTimeZone(TimeZone.getTimeZone("AST"));
        return format.format(date);
    }

    public String getCurrentDate(Locale locale) {
        return String.format(locale, "%d-%d-%d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR));
    }
}
