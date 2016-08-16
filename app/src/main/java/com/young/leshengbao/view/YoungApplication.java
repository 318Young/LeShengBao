package com.young.leshengbao.view;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/7/25.
 */
public class YoungApplication  extends Application{

    public static YoungApplication instance = null ;

    public static boolean loginStatic = false ; /*false 未登录 true  登录成功*/

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this ;
    }

    public synchronized static YoungApplication getInstance() {
        return instance;
    }
}
