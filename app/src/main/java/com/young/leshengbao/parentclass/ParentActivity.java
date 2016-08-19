package com.young.leshengbao.parentclass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.young.leshengbao.R;
import com.young.leshengbao.utils.ToastUtil;
import com.young.leshengbao.view.YoungApplication;

/**
 * Created by Administrator on 2016/7/23.
 */
public abstract class ParentActivity extends AppCompatActivity {
    private String TAG = "ParentActivity";

    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (0 != getLayoutID()) {
            setContentView(getLayoutID());
        } else {
            Log.e(TAG, "add layout");
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != toolbar){
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
