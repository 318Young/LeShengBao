package com.young.leshengbao.parentclass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Administrator on 2016/7/23.
 */
public abstract class ParentActivity extends AppCompatActivity {
    private String TAG = "ParentActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (0 != getLayoutID()) {
            setContentView(getLayoutID());
        } else {
            Log.e(TAG, "add layout");
        }

        initViews();

        initDates();
    }

    /**
     * 添加布局文件
     * @return
     */
    public abstract int getLayoutID();

    /**
     * 初始化控件
     */
    public abstract void initViews();


    /**
     * 初始化数据
     */
    public abstract void initDates();
}
