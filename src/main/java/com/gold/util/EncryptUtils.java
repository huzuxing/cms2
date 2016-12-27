package com.gold.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by huzuxing on 2016/9/30.
 */
public class EncryptUtils {

    public static String digest(String str) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] array = str.getBytes();
            digest.update(array);
            byte[] resut = digest.digest(array);
            return byteArrayToHex(resut);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String originDigest(String str) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] resut = digest.digest(str.getBytes());
            return byteArrayToString(resut);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String byteArrayToString(byte[] array) {
        StringBuffer buffer = new StringBuffer();
        for (byte b:
             array) {
            int number = b & 0xff;
            String s = Integer.toHexString(number);
            if (s.length() == 1)
                buffer.append("0");
            buffer.append(s);
        }
        return buffer.toString();
    }

    public static String byteArrayToHex(byte[] array) {
        // 初始化一个字符数组，用来存放16进制字符
        char[] hexDigts = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // new 一个数组，存放结果字符串
        char[] result = new char[array.length * 2];
        //遍历字节数组，通过位运算，转换成字符
        int index = 0;
        for (byte b: array) {
            result[index++] = hexDigts[b >>> 4 & 0xf];
            result[index++] = hexDigts[b & 0xf];
        }
        return new String(result);
    }

    public static void main(String[] args) {
        String password = "abcd1234123456";
        System.out.println(originDigest(password));
    }
}
