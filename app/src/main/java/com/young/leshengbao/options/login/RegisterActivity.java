package com.young.leshengbao.options.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.young.leshengbao.utils.CommonUtils;
import com.young.leshengbao.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/7/25.
 */
public class RegisterActivity extends ParentActivity implements View.OnClickListener, LoginBack {


    private Toolbar toolbar;

    private Button btGetYzm,btRegister;
    private EditText etUserName,etYzm,etPwd, etConfirmPwd;

    private AnsyFactory ansyFactory = null;

    private CommonAsync registerAsync = null;


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
        btRegister = (Button) findViewById(R.id.bt_register);
        btRegister.setOnClickListener(this);
        etUserName = (EditText) findViewById(R.id.et_username);


        etYzm = (EditText) findViewById(R.id.et_yzm);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etConfirmPwd = (EditText) findViewById(R.id.et_confirm_pwd);
    }

    @Override
    public void initDates() {

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_get_yzm:
                if (TextUtils.isEmpty(etUserName.getText().toString())){
                    ToastUtil.showInfo(this,getString(R.string.register_no_username));
                    return;
                }
                if (!CommonUtils.matchPhoneNum(etUserName.getText().toString())){
                    ToastUtil.showInfo(this,getString(R.string.input_wrong_phone_num));
                    return;
                }
                getYzm();

                break;

            case R.id.bt_register:
                if (TextUtils.isEmpty(etUserName.getText().toString())){
                    ToastUtil.showInfo(this,getString(R.string.register_no_username));
                    return;
                }
                if (TextUtils.isEmpty(etYzm.getText().toString())){
                    ToastUtil.showInfo(this,getString(R.string.register_no_yzm));
                    return;
                }
                if (etPwd.getText().toString().toCharArray().length < 6){
                    ToastUtil.showInfo(this,getString(R.string.et_pdw_less));
                    return;
                }
                if (TextUtils.isEmpty(etPwd.getText().toString())||TextUtils.isEmpty(etConfirmPwd.getText().toString())){
                    ToastUtil.showInfo(this,getString(R.string.register_no_pdw));
                    return;
                }else if (!etPwd.getText().toString().equals(etConfirmPwd.getText().toString())){
                    ToastUtil.showInfo(this,getString(R.string.register_pdw_different));
                    return;
                }else {
                    register();
                }
                break;

            default:
                break;
        }

    }


    public void getYzm() {
        try {
            if (null == ansyFactory)
            ansyFactory = new ConcreFactory();
            registerAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            map.put("phonenum", etUserName.getText().toString());
            registerAsync.setLoginBack(this);
            registerAsync.setContxt(this);
            registerAsync.setRequestMethod(getString(R.string.get_yzm_method));
            registerAsync.execute(map, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void register() {
        try {
            if (null == ansyFactory)
            ansyFactory = new ConcreFactory();
            registerAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            map.put("phonenum", etUserName.getText().toString());
            map.put("pcode", etYzm.getText().toString());
            map.put("pwd", etPwd.getText().toString());
            registerAsync.setLoginBack(this);
            registerAsync.setContxt(this);
            registerAsync.setRequestMethod(getString(R.string.register_method));
            registerAsync.execute(map, null, null);

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
                return;
            }else if (getString(R.string.register_method).equals(requestMethod)){
                if(1 == tryLogin.getValue()){
                    ToastUtil.showInfo(this, "register success");
                    setResult(0,new Intent().putExtra("userName",etUserName.getText().toString()));
                    this.finish();
                }
                ToastUtil.showInfo(this, tryLogin.getMemo());
                return;
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
