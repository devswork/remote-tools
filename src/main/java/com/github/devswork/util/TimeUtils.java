package com.github.devswork.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtils {


    public static String StringToTimestamp(String time) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return null;
    }

    public static Long zeroTimeStamp(Long timeStamp) {
        Long oneDayTimestamps = Long.valueOf(60 * 60 * 24 * 1000);
        return timeStamp - (timeStamp + 60 * 60 * 8 * 1000) % oneDayTimestamps;
    }

    public static Long fullTimeStamp(Long timeStamp) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        System.out.println(sdf.format(new Date(timeStamp)));
        timeStamp += 60 * 60 * 24 * 1000;
        Long oneDayTimestamps = Long.valueOf(60 * 60 * 24 * 1000);
        return timeStamp - (timeStamp + 60 * 60 * 8 * 1000) % oneDayTimestamps;
    }


    public static Date timeZeroToDate(String time) {
        Date date = new Date();
        try {
            String time1 = time + " 00:00:00";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(time1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date timeFullToDate(String time) {
        Date date = new Date();
        try {
            String time1 = time + " 23:59:59";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(time1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    public static Integer getTodayLeaveSeconds(Date currentDate) {

        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),

                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)

                .withSecond(0).withNano(0);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),

                ZoneId.systemDefault());

        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);

        return (int) seconds;

    }

}
