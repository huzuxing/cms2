package com.gold.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huzuxing on 2016/10/29.
 */
public class commonUtils {

    public static String dateFormat(String format, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
