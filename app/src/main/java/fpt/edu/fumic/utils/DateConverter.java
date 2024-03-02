package fpt.edu.fumic.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateConverter {
    static SimpleDateFormat spDate = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat spDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static Date stringToDate(String date) throws ParseException {
        // chuyển kiểu String -> Date
        return spDate.parse(date);
    }

    public static String dateToString(Date date){
        // chuyển kiểu Date -> String
        return spDate.format(date);
    }

    public static Date stringToDateTime(String dateTime) throws ParseException {
        // chuyển kiểu String -> DateTime
        return spDateTime.parse(dateTime);
    }

    public static String DateTimeToString(Date dateTime){
        // chuyển kiểu DateTime -> String
        return spDateTime.format(dateTime);
    }
}
