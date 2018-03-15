package com.tamic.statinterface.stats.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  DateUtil
 *
 */
public class DateUtil {

    /**
     *
     * @return currentTimeMillis
     */
    public static long getCurrentTime(){
        long time = System.currentTimeMillis();
        return time ;
    }

    /**
     * getDate
     * yyyy-MM-dd HH:mm:ss
     *
     * @param
     * @param format
     *            如yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateString(long milliseconds, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(milliseconds));
    }

}
