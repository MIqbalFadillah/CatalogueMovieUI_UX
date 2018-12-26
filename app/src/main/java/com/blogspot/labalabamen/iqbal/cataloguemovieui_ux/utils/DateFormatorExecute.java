package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatorExecute {
    public final static String DATE_FORMAT = "dd MMMM yyyy";
    public final static String DATE_FORMAT_DAY = "EEEE, MMM d, yyyy";

    private static String format(String date, String format) {
        String result = "";

        DateFormat old = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = old.parse(date);
            DateFormat newFormat = new SimpleDateFormat(format);
            result = newFormat.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getDate(String date) {
        return format(date, DATE_FORMAT);
    }

    public static String getDateDay(String date) {
        return format(date, DATE_FORMAT_DAY);
    }

}
