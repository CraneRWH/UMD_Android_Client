package com.ymd.client.utils;

import java.util.Date;

/**
 * 作者:rongweihe
 * 日期:2018/12/8  时间:21:22
 * 描述:
 * 修改历史:
 */
public class DateUtil {

    /**
     * 计算两个时间的间隔时分秒
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getSpacingTime(Date startDate, Date endDate){

        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        StringBuffer dTime = new StringBuffer();
        dTime.append(elapsedHours).append(":").append(elapsedMinutes).append(":").append(elapsedSeconds);
        return dTime.toString();

    }

    /**
     * 获取与当前时间的间隔时间
     * @param endDate
     * @return
     */
    public static String getSpacingTime(Date endDate){
        return getSpacingTime(new Date(), endDate);
    }

}
