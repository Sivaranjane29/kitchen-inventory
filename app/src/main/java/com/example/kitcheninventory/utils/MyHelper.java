/*
 * *
 *  * Created by PromptTech Middle East Pvt Ltd. on 21/1/20 5:22 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 16/1/20 4:20 PM
 *
 */

package com.example.kitcheninventory.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyHelper {

    public static String getCurrentDateForDatabase() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATABASE_DATE_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentTimeForDatabase() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATABASE_TIME_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATABASE_DATE_TIME);
        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentDateForView() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATE_FOR_VIEW);
        return dateFormat.format(calendar.getTime());
    }
    public static String getDateTimeForView() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATE_TIME_VIEW);
        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentTimeForView() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATABASE_TIME_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    public static String getDateForViewCalendar(Calendar mCalendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return dateFormat.format(mCalendar.getTime());
    }

    public static String getDateForDatabase(Calendar mCalendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATABASE_DATE_FORMAT);
        return dateFormat.format(mCalendar.getTime());
    }

    public static Calendar stringToCalendar(String strDate) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat(GlobalConstants.DATABASE_DATE_FORMAT);
        Date d = format.parse(strDate);
        Calendar c = Calendar.getInstance();
        assert d != null;
        c.setTime(d);
        return c;
    }

    public static String dbStringDateToLocalFormat(String strDate) {

        String returnString = strDate;
        SimpleDateFormat format = new SimpleDateFormat(GlobalConstants.DATABASE_DATE_FORMAT);
        Date date;
        Calendar mCalendar = Calendar.getInstance();
        try {
            date = format.parse(strDate);
            assert date != null;
            mCalendar.setTime(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            returnString = dateFormat.format(mCalendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public static Double getRound(Double dblValue) {
        String strValue = String.format("%.2f", dblValue);
        dblValue = Double.valueOf(strValue);
        return dblValue;
    }

}
