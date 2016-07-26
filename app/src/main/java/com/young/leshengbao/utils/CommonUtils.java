package com.young.leshengbao.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2016/7/26.
 */
public class CommonUtils {


    public static boolean matchPhoneNum(String phoneNum){

        Pattern pattern = Pattern.compile("^1[3-8][0-9][0-9]{8}$");
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }
}
