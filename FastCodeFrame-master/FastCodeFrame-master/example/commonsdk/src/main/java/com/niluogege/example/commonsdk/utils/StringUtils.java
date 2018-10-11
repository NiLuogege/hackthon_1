package com.niluogege.example.commonsdk.utils;

import android.text.TextUtils;

/**
 * Created by niluogege on 2018/8/23.
 *
 * String 工具类
 */

public class StringUtils {

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }
        sb.append(utfString.substring(pos));
        return sb.toString();
    }

    /**
     * 是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }


    /**
     * 是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
