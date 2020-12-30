package com.nepo.fevercat.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sm on 2019/3/12.
 */

public class DateUtil {
    //根据传入的日期，获取对应年月份
    public static void getDateSplit(String dateStr, int[] out) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            out[0] = calendar.get(Calendar.YEAR);
            out[1] = calendar.get(Calendar.MONTH) + 1;
            out[2] = calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式不正确");
        }
    }

    public static void getDaySplit(String dateStr, int[] out) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            out[0] = calendar.get(Calendar.HOUR_OF_DAY);
            out[1] = calendar.get(Calendar.MINUTE);
            out[2] = calendar.get(Calendar.SECOND);
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式不正确");
        }
    }

    public static long parse(String dateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式不正确");
        }
    }

    public static String parseDateToString(long time) {
        int hour = (int) ((time / 1000) / (60 * 60));
        int min = (int) ((time / 1000 / 60) % 60);
        int sec = (int) ((time / 1000) % 60);
        return hour + "时" + min + "分" + sec + "秒";
    }

    public static String parseDateToString2(long time) {
        return new SimpleDateFormat("HH:mm:ss").format(time);
    }

    public static String parseDateToString3(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

}
