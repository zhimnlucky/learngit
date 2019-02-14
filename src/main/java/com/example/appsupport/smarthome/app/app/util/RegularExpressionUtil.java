package com.example.appsupport.smarthome.app.app.util;

import java.util.regex.Pattern;

public class RegularExpressionUtil {


    /**
     * 验证字符串是否为整数
     *
     * @param str
     * @return
     */
    public static boolean isInt(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证字符串是否为整数或有小数点为.5的数字     例：10  或 10.5
     *
     * @param str
     * @return
     */
    public static boolean isNumer(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+(.5)?$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证字符串是否为"1,2,3,4..."格式
     *
     * @param str
     * @return
     */
    public static boolean isRepeatRule(String str) {
//        Pattern pattern = Pattern.compile("\\d+(,\\d+)*");
        Pattern pattern = Pattern.compile("[1-7](,[1-7]){0,6}");
        return pattern.matcher(str).matches();

    }

    /**
     * 验证时间格式HH:MM
     *
     * @param str
     * @return
     */
    public static boolean isActionTime(String str) {
        Pattern pattern = Pattern.compile("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$");
        return pattern.matcher(str).matches();
    }


    /**
     * 验证时间格式HH:MM-HH:MM
     *
     * @param str
     * @return
     */
    public static boolean isEffecttiveTime(String str) {
        Pattern pattern = Pattern.compile("^([0-1][0-9]|[2][0-3]):([0-5][0-9])-([0-1][0-9]|[2][0-3]):([0-5][0-9])$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证经纬度格式112.43,28.116(经度的范围是0-180，保留6位小数，维度的范围是0−90，保留6位小数)
     *
     * @param str
     * @return
     */
    public static boolean isLocation(String str) {
        Pattern pattern = Pattern.compile("(^(([1-9]\\d?)|(1[0-7]\\d))(\\.\\d{1,6})?|180|0(\\.\\d{1,6})?),((([1-8]\\d?)|([1-8]\\d))(\\.\\d{1,6})?|90|0(\\.\\d{1,6})?)$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证模式是否正确 0或1或2或3或4
     *
     * @param str
     * @return
     */
    public static boolean isMode(String str) {
        Pattern pattern = Pattern.compile("[01234]");
        return pattern.matcher(str).matches();
    }
}
