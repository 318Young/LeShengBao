package com.young.leshengbao.view;

import android.app.Application;
import android.content.SharedPreferences;

import com.young.leshengbao.utils.CommonUtils;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/25.
 */
public class YoungApplication  extends Application{

    public static YoungApplication instance = null ;
    public static Map<String, Object> appInfo = null ;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this ;

        appInfo = CommonUtils.getAppInfo(this);
    }

    public synchronized static YoungApplication getInstance() {
        return instance;
    }
}
