package com.young.leshengbao.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public static String getMD5(String str) {

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            byte[] bytes = md5.digest();
            return getString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getString(byte[] bytes) {

        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            if (Integer.toHexString(0xFF & bytes[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & bytes[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
        }
        return md5StrBuff.toString();
    }
}
