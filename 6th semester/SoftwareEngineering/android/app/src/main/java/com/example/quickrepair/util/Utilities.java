package com.example.quickrepair.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Utilities
{
    /**
     * get the day, month, year of a Calendar
     * don't include the hour, minute, second
     *
     * @param actualDate
     */
    public static GregorianCalendar getYearMonthDay(GregorianCalendar actualDate)
    {
        int day = actualDate.get(actualDate.DAY_OF_MONTH);
        int month = actualDate.get(actualDate.MONTH);
        int year = actualDate.get(actualDate.YEAR);

        return new GregorianCalendar(year, month, day);
    }
    public  static String getToString(GregorianCalendar date){
        return date.get(Calendar.HOUR) + " : " + date.get(Calendar.MINUTE) + "  " + date.get(Calendar.DAY_OF_MONTH) + " / " + date.get(Calendar.MONTH) +" / " + date.get(Calendar.YEAR);
    }
}
