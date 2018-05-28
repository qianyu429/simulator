package com.shhxzq.bs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kangyonggan
 * @since 16/5/19
 */
public class DateUtil {

    private static SimpleDateFormat format8 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat format14 = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat format17 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    private static SimpleDateFormat format19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat serialNoFormat = new SimpleDateFormat("mssSSS");

    public static String format8Date(Date date) {
        return format8.format(date);
    }

    public static String format14Date(Date date) {
        return format14.format(date);
    }

    public static String format17Date(Date date) {
        return format17.format(date);
    }

    public static String format19Date(Date date) {
        return format19.format(date);
    }

    public static Date parasDateFromTemplate8(String str) {
        try {
            return format8.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 由时间后6位生成流水号，重复也无所谓，没多大用。
     *
     * @return
     */
    public static String genSerialNo() {
        return serialNoFormat.format(new Date());
    }

}
