package com.young.leshengbao.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.young.leshengbao.model.GetInfo;
import com.young.leshengbao.view.YoungApplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2016/7/26.
 */
public class CommonUtils {
    public static final String VERSIONCODE = "versionCode";
    public static final String VERSIONNAME = "versionName";
    public static final String PACKAGENAME = "packageName";

    public static boolean matchPhoneNum(String phoneNum) {

        Pattern pattern = Pattern.compile("^1[3-8][0-9][0-9]{8}$");
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }

    public static String getXml(Context context) {

        List<GetInfo> listJson = new ArrayList<>();
        GetInfo info = new GetInfo();
        info.setUserId(SharedPreferencesUtils.getStringValue(context,PreConstants.LSB_USERID, ""));
        info.setUserPwd(SharedPreferencesUtils.getStringValue(context,PreConstants.LSB_USERPWD, ""));
        listJson.add(info);
        String json = new Gson().toJson(listJson);
        Log.d("Json", "getXml: " + json);
        return Base64.encodeToString(json.getBytes(), Base64.NO_WRAP);

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


    /**
     *
     * @param context
     * @return
     */
    public static Map<String, Object> getAppInfo(Context context) {
        Map<String, Object> map = null;
        PackageManager manager = null;
        PackageInfo info = null;
        try {
            map = new HashMap<>();
            manager = context.getPackageManager();
            info = manager.getPackageInfo(context.getPackageName(), 0);
            map.put(VERSIONCODE, info.versionCode);
            map.put(VERSIONNAME, info.versionName);
            map.put(PACKAGENAME, info.packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }
}
