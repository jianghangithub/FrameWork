package com.jh.framework.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by JH on 2018/3/27.
 */

public class StrUtil {

    public static String doubleTo2Str(double d) {
        try {

            DecimalFormat df = new DecimalFormat("######0.00");
            return df.format(d);
        } catch (Exception e) {
            return d + "";
        }
    }

    //中国石油
    public static boolean isCHNShiYou(String cardnum) {
        String telRegex = "^90[0-9]{14}$";
        if (TextUtils.isEmpty(cardnum))
            return false;
        else
            return cardnum.matches(telRegex);
    }//中国石化

    public static boolean isCHNShiHua(String cardnum) {
        String telRegex = "^100011[0-9]{13}$";
        if (TextUtils.isEmpty(cardnum))
            return false;
        else
            return cardnum.matches(telRegex);
    }

    public static String getTwoStr(double d) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(d);
    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }


    public static boolean isCode(String code) {
        String telRegex = "^\\d{6}$";
        if (TextUtils.isEmpty(code))
            return false;
        else
            return code.matches(telRegex);
    }

    public static int isPwd(String pwd) {
        String telRegex = "^[0-9A-Za-z]{6,20}$";
        if (TextUtils.isEmpty(pwd))
            return -1;
        else if (pwd.matches(telRegex)) {
            return 1;
        } else {
            return 0;
        }
    }

    public static boolean isPassword(String pwd) {
        String telRegex = "^[0-9A-Za-z]{6,20}$";
        if (TextUtils.isEmpty(pwd))
            return false;
        else
            return pwd.matches(telRegex);

    }

    public static boolean isATOZ(String s) {
        String telRegex = "^[A-Z]$";
        if (TextUtils.isEmpty(s))
            return false;
        else
            return s.matches(telRegex);

    }public static boolean isLicense(String s) {
        String telRegex = "^(([\u4e00-\u9fa5]{1}[A-Z]{1})[-]?|([wW][Jj][\u4e00-\u9fa5]{1}[-]?)|([a-zA-Z]{2}))([A-Za-z0-9]{5}|[DdFf][A-HJ-NP-Za-hj-np-z0-9][0-9]{4}|[0-9]{5}[DdFf])$";
        if (TextUtils.isEmpty(s))
            return false;
        else
            return s.matches(telRegex);

    }
}
