package com.young.leshengbao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chenhe on 2016/8/5.
 */
public class DateUtils {
    public static String[] weekName = { "周日", "周一", "周二", "周三", "周四", "周五","周六" };

    /**
     * 判断指定的某一天是星期几
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int getWeekDayFromDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month, day));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }

    public static Date getDateFromString(int year, int month, int day) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month))
                + "-" + (day > 9 ? day : ("0" + day));
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }
}
