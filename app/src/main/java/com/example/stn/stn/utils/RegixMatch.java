package com.example.stn.stn.utils;

import java.util.regex.Pattern;

/**
 * Name: RegixMatch
 * Author: xulong
 * Comment: 正则验证类: true   验证成功
 *                     false  验证失败
 * Date: 2016-09-24 13:17.
 */
public class RegixMatch {

    /**
     * 6位数字
     */
    public static boolean regix6Math(String str){
        boolean matches = Pattern.matches("^\\d{6}$", str);
        return matches;
    }


    /**
     * 8位数字
     */
    public static boolean regix8Math(String str){
        boolean matches = Pattern.matches("^\\d{8}$", str);
        return matches;
    }


    /**
     * 11位数字
     */
    public static boolean regix11Math(String str){
        boolean matches = Pattern.matches("^\\d{11}$", str);
        return matches;
    }

    /**
     * 邮箱
     */
    public static boolean regixEmail(String str){
        boolean matches = Pattern.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?", str);
        return matches;
    }


    /**
     * 身份证：前17为必须为数字 最后一位可以为X或者数字
     */
    public static boolean regixIDCard(String str){
        boolean matches = Pattern.matches("^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$", str);
        return matches;
    }




}
