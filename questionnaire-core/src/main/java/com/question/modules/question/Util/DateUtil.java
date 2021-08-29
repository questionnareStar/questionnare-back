package com.question.modules.question.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getCron(java.util.Date  date){
        String dateFormat="s mm HH * * *";
        return formatDateByPattern(date, dateFormat);
    }
    /***
     *  修改cron参数格式
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
}
