package com.gama.mutamer.helpers;

import android.util.Log;

import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;

import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HijriDateHelper {
    public String getCurrentDate(Locale locale) {
        try {
            Chronology iso = ISOChronology.getInstanceUTC();
            Chronology hijri = IslamicChronology.getInstanceUTC();
            Log.v("Date", Calendar.getInstance().get(Calendar.YEAR) + "" + Calendar.getInstance().get(Calendar.MONTH) + "" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "");
            LocalDate todayIso = new LocalDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), iso);
            LocalDate todayHijri = new LocalDate(todayIso.toDateTimeAtStartOfDay(),
                    hijri);
            return String.format(locale, "%d-%d-%d", todayHijri.getDayOfMonth() + 1, todayHijri.getMonthOfYear(), todayHijri.getYear());
        }catch (Exception e){
            FirebaseErrorEventLog.SaveEventLog(e);
            return "";
        }
    }

}
