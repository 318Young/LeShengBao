package com.young.leshengbao.view;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/7/25.
 */
public class YoungApplication  extends Application{

    public static SharedPreferences mPreference;
    public static YoungApplication instance = null ;
    @Override
    public void onCreate() {
        super.onCreate();
        mPreference = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        instance = this ;
    }

    public synchronized static YoungApplication getInstance() {
        return instance;
    }
}
