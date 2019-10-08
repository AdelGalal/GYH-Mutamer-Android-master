package com.gama.mutamer.helpers;

import android.content.Context;

import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.prayTimes.LocationVM;
import com.gama.mutamer.viewModels.prayTimes.Method;
import com.gama.mutamer.viewModels.prayTimes.Prayer;
import com.gama.mutamer.viewModels.prayTimes.Preferences;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by mustafa on 5/15/18.
 * Release the GEEK
 */
public class Schedule {

    private static Schedule today;
    private final GregorianCalendar[] schedule = new GregorianCalendar[7];
    private final boolean[] extremes = new boolean[7];
    private final fi.joensuu.joyds1.calendar.Calendar hijriDate;

    public Schedule(Context context, GregorianCalendar day, int prayerCalc) {
        Preferences preferences = Preferences.getInstance(context);
        Method method = Constants.CALCULATION_METHODS[prayerCalc].copy();
        method.setRound(Constants.ROUNDING_METHODS[preferences.getRoundingMethodIndex()]);

        LocationVM location = preferences.getJitlLocation(context);
        Jitl jitl = new Jitl(location, method);
        Prayer[] dayPrayers = jitl.getPrayerTimes(day).getPrayers();
        Prayer[] allTimes = new Prayer[]{dayPrayers[0], dayPrayers[1], dayPrayers[2], dayPrayers[3], dayPrayers[4], dayPrayers[5], jitl.getNextDayFajr(day)};

        for (short i = Constants.FAJR; i <= Constants.NEXT_FAJR; i++) {
            // Set the times on the schedule
            schedule[i] = new GregorianCalendar(day.get(Calendar.YEAR), day.get(Calendar.MONTH), day.get(Calendar.DAY_OF_MONTH), allTimes[i].getHour(), allTimes[i].getMinute(), allTimes[i].getSecond());
            schedule[i].add(Calendar.MINUTE, preferences.getOffsetMinutes());
            extremes[i] = allTimes[i].isExtreme();
        }
        schedule[Constants.NEXT_FAJR].add(Calendar.DAY_OF_MONTH, 1/* next fajr is tomorrow */);

        hijriDate = new fi.joensuu.joyds1.calendar.IslamicCalendar();
    }

    public static Schedule today(Context context, int calculationMethod) {
        GregorianCalendar now = new GregorianCalendar();
        today = new Schedule(context, now, calculationMethod);
        return today;
    }

    public static void setSettingsDirty() {
        // Force re-instantiation of new today
        today = null;
        Utils.isRestartNeeded = true;
    }

    public static double getGMTOffset() {
        Calendar now = new GregorianCalendar();
        int gmtOffset = now.getTimeZone().getOffset(now.getTimeInMillis());
        return gmtOffset / 3600000;
    }

    public static boolean isDaylightSavings() {
        Calendar now = new GregorianCalendar();
        return now.getTimeZone().inDaylightTime(now.getTime());
    }

    public GregorianCalendar[] getTimes() {
        return schedule;
    }

    public boolean isExtreme(int i) {
        return extremes[i];
    }

    public short nextTimeIndex() {
        Calendar now = new GregorianCalendar();
        if (now.before(schedule[Constants.FAJR])) return Constants.FAJR;
        for (short i = Constants.FAJR; i < Constants.NEXT_FAJR; i++) {
            if (now.after(schedule[i]) && now.before(schedule[i + 1])) {
                return ++i;
            }
        }
        return Constants.NEXT_FAJR;
    }

    private boolean currentlyAfterSunset() {
        Calendar now = new GregorianCalendar();
        return now.after(schedule[Constants.MAGHRIB]);
    }


}
