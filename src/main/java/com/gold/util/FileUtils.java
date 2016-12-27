package com.gold.util;

/**
 * Created by huzuxing on 2016/9/24.
 */
public class FileUtils {

    public static String getFileName(String args) {
        return args.substring(0, args.lastIndexOf(".")).replace("\"", "").trim();
    }

    public static String getFileExt(String args) {
        return args.substring(args.lastIndexOf(".")).replace("\"", "").trim();
    }
}
