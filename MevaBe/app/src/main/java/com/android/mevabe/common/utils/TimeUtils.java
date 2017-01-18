package com.android.mevabe.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hoangnm on 1/8/16.
 */
public class TimeUtils {
    public static String FORMAT_1 = "yyyy/MM/dd h:m:s";
    public static String FORMAT_2 = "dd/MM/yyyy";

    public static String getTimeMilisecon() {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }

    public static long parseStringDateTimePillEvent(String pDateTime, String string_format_date) {
        long value = -1;
        SimpleDateFormat dateFormat = new SimpleDateFormat(string_format_date);
        Date date;
        try {
            date = dateFormat.parse(pDateTime);
            value = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getDateTimeYYYYMMDD(String time_source, String format,String format_expect) {
        String time = "";
        try {
            Date date = new Date(parseStringDateTimePillEvent(time_source,format));
            SimpleDateFormat SDF = new SimpleDateFormat(format_expect);
            time = SDF.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            time = "";
        }
        return time;
    }
}
