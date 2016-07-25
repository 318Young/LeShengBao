package com.young.leshengbao.view;

import android.app.Application;

/**
 * Created by Administrator on 2016/7/25.
 */
public class YoungApplication  extends Application{
    public static YoungApplication instance = null ;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this ;
    }

    public synchronized static YoungApplication getInstance() {
        return instance;
    }
}
