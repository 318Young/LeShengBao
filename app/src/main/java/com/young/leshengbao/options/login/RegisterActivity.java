package com.young.leshengbao.options.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.young.leshengbao.R;
import com.young.leshengbao.ansy.AnsyFactory;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/7/25.
 */
public class RegisterActivity extends ParentActivity implements View.OnClickListener, LoginBack {


    private Toolbar toolbar;

    private Button btGetYzm;
    private EditText etUserName;

    private AnsyFactory ansyFactory = null;

    private CommonAsync loginAsync = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.bt_register));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btGetYzm = (Button) findViewById(R.id.bt_get_yzm);
        btGetYzm.setOnClickListener(this);
        etUserName = (EditText) findViewById(R.id.et_username);
    }

    @Override
    public void initDates() {

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_get_yzm:
                getYzm();

                break;

            default:
                break;
        }

    }


    public void getYzm() {
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            map.put("phonenum", etUserName.getText().toString());
            loginAsync.setLoginBack(this);
            loginAsync.setContxt(this);
            loginAsync.setRequestMethod(getString(R.string.get_yzm_method));
            loginAsync.execute(map, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginSuc(String requestMethod, TryLogin tryLogin) {
        if (null != tryLogin) {
            if (getString(R.string.get_yzm_method).equals(requestMethod)) {
                if (1 == tryLogin.getValue()) {
                    btGetYzm.setClickable(false);
                    new MyCount(120000, 1000).start();
                }
                ToastUtil.showInfo(this, tryLogin.getMemo());
            }
        }

    }

    public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            btGetYzm.setText(getString(R.string.bt_get_yzm));
            btGetYzm.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btGetYzm.setText(millisUntilFinished / 1000 + "秒后重新获取");
        }

    }

}
