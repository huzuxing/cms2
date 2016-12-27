package com.gold.util;

/**
 * Created by huzuxing on 2016/9/25.
 */
public class QueryUtils {

    public static Integer pageNoCheck(Integer pageNo) {
        return null == pageNo ? 1 : pageNo;
    }

    public static Integer pageSizeCheck(Integer pageSize) {
        return null == pageSize ? 10 : pageSize;
    }
}
