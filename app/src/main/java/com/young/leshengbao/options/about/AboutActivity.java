package com.young.leshengbao.options.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.young.leshengbao.R;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.CommonUtils;
import com.young.leshengbao.view.YoungApplication;

/**
 * Created by OnceDaily on 2016/8/20.
 */
public class AboutActivity extends ParentActivity{

    private TextView aboutVersion;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    public void initViews() {
        aboutVersion = (TextView) findViewById(R.id.about_version);
    }

    @Override
    public void initDates() {
        aboutVersion.setText("V"
                + YoungApplication.appInfo.get(CommonUtils.VERSIONNAME)
                .toString());
    }
}
