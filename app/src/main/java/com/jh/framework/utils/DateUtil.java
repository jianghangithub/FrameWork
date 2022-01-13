package com.jh.framework.utils;


import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JH on 2018/3/29.
 */

public class DateUtil {
    private static final String DATA_FORM = "yyyy-MM-dd HH:mm:ss";
    private static final String DATA_FORM1 = "MM-dd HH:mm";

    //String转Date
    public static Date getFormatDateByString(String timeString) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(DATA_FORM);
        if (TextUtils.isEmpty(timeString)) {
            timeString = "1900-00-00 00:00";
        }
        Date date = new Date();
        try {
            date = format.parse(timeString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    //Date转String
    public static String getFormatStringByDate(Date timeDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat(DATA_FORM);
        if (timeDate == null) {
            timeDate = new Date(1900, 00, 00, 00, 00);
        }
        return fmt.format(timeDate);
    }

    //long转String
    public static String getFormatStringByLong(long timeDate) {
        Date date = new Date(timeDate * 1000);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat(DATA_FORM1);
        return fmt.format(date);
    } //Date转String


    public static String getFormatStringByLong(long timeDate, String format) {
        Date date = new Date(timeDate * 1000);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }

    public static int getNowTime() {
        Calendar date = Calendar.getInstance();
        // 得到24小时机制
//        date.get(Calendar.HOUR_OF_DAY);
        // 得到12小时机制
        //date.get(Calendar.HOUR);
        return date.get(Calendar.HOUR_OF_DAY);
    }


}
