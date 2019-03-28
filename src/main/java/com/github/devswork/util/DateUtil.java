package com.github.devswork.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * @author devswork
 */

public class DateUtil {


    public static final String FORMAT_STR = "0000-00-00 00:00";

    public static final String FORMAT_STANDARD_19 = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_STANDARD_16 = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_STANDARD_10 = "yyyy-MM-dd";

    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat(FORMAT_STANDARD_19);
        }
    };

    public static Timestamp getTimestamp() {
        Date d = new Date();
        return getTimestamp(d);
    }

    public static Timestamp getTimestamp(Date d) {
        Timestamp time = new Timestamp(d.getTime());
        return time;
    }

    public static String getStandard19DateAndTime(Date date) {
        String result = "";
        if (date != null) {
            result = threadLocal.get().format(date);
        }
        return result;
    }

    public static Date getByStandard19DateAndTime(String dateStr) {
        try {
            return threadLocal.get().parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    public static String getDate(String format) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDate(Date date, String format) {
        String rs = "";
        if (date != null) {
            SimpleDateFormat f = new SimpleDateFormat(format);
            try {
                rs = f.format(date);
            } catch (Exception e) {
                rs = FORMAT_STR;
            }
        }
        return rs;
    }

    public static String getDate(Date d) {
        String date = "";
        if (d != null) {
            DateFormat df = DateFormat.getDateInstance();
            try {
                date = df.format(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static String getLocalDate(Date d) {
        String date = "";
        if (d != null) {
            Locale l = new Locale("zh", "CN");
            DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, l);
            try {
                date = df.format(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static String getLocalDate() {
        String date = "";
        Locale l = new Locale("zh", "CN");
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, l);
        try {
            date = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getLocalTime() {
        Locale l = new Locale("zh", "CN");
        DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG, l);
        String time = "";
        try {
            time = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getLocalTime(Date d) {
        Locale l = new Locale("zh", "CN");
        DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG, l);
        String time = "";
        try {
            time = df.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getTime(Date d) {
        String time = "";
        if (d != null) {
            DateFormat df = DateFormat.getTimeInstance();
            try {
                time = df.format(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    public static String getTime() {
        DateFormat df = DateFormat.getTimeInstance();
        String time = "";
        try {
            time = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String get19DateAndTime() {
        return getDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateAndTime(Date d) {
        return getDate(d, FORMAT_STANDARD_19);
    }

    public static String getDateAndTime16(Date d) {
        return getDate(d, FORMAT_STANDARD_16);
    }

    public static String getLocalDateAndTime() {
        Locale l = new Locale("zh", "CN");
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT, l);
        String dateAndTime = "";
        try {
            dateAndTime = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateAndTime;
    }

    public static String getLocalDateAndTime(Date d) {
        String dateAndTime = "";
        if (d != null) {
            Locale l = new Locale("zh", "CN");
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, l);
            try {
                dateAndTime = df.format(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateAndTime;
    }

    public static int getYear() {
        return new GregorianCalendar().get(Calendar.YEAR);
    }

    public static int getYear(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth() {
        return new GregorianCalendar().get(Calendar.MONTH) + 1;
    }

    public static int getMonth(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        return new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public static int getDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static Date toDate(String date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                return sdf.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static Date subHours(Date date, int hours) {
        return addHours(date, -hours);
    }

    public static Long toSeconds(Date createTime) {
        if (createTime != null) {
            return createTime.getTime() / 1000;
        }
        return null;
    }

    public static Date secondsToDate(Long seconds) {
        return new Date(seconds * 1000);
    }

    public static int getSecTodayLeave() {
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return (int) (tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
    }


    public static Long dateToStamp(String dateStr) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(dateStr);
        long ts = date.getTime();
        return  ts;
    }
}
