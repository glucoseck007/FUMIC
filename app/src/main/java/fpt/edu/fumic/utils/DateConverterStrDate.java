package fpt.edu.fumic.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateConverterStrDate {
    static SimpleDateFormat spDate = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat spDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static Date stringToDate(String date) throws ParseException {
        return spDate.parse(date);
    }

    public static String dateToString(Date date){
        return spDate.format(date);
    }

    public static Date stringToDateTime(String dateTime) throws ParseException {
        return spDateTime.parse(dateTime);
    }

    public static String DateTimeToString(Date dateTime){
        return spDateTime.format(dateTime);
    }
}
