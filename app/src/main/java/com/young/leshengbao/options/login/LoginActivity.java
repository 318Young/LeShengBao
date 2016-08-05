package com.young.leshengbao.options.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.young.leshengbao.R;
import com.young.leshengbao.ansy.AnsyFactory;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.CommonUtils;
import com.young.leshengbao.utils.PreConstants;
import com.young.leshengbao.utils.SharedPreferencesUtils;
import com.young.leshengbao.utils.ToastUtil;
import com.young.leshengbao.view.YoungApplication;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends ParentActivity implements View.OnClickListener, LoginBack {

    private EditText etUsername;
    private EditText etPassword;


    private AnsyFactory ansyFactory = null;

    private CommonAsync loginAsync = null;

    public static final int REGISTER_RESULT_CODE = 1;
    private String userPhone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.bt_register).setOnClickListener(this);

        toolbar.setTitle(getString(R.string.bt_login));


    }

    @Override
    public void initDates() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                if (TextUtils.isEmpty(etUsername.getText().toString())) {
                    ToastUtil.showInfo(LoginActivity.this, getString(R.string.register_no_username));
                    return;
                }
                if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    ToastUtil.showInfo(LoginActivity.this, getString(R.string.register_no_pdw));
                    return;
                }
                if (!CommonUtils.matchPhoneNum(etUsername.getText().toString())){
                    ToastUtil.showInfo(this,getString(R.string.input_wrong_phone_num));
                    return;
                }
                login();

                break;
            case R.id.bt_register:
                startActivityForResult(new Intent(this, RegisterActivity.class),REGISTER_RESULT_CODE);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_RESULT_CODE){
            if (resultCode == 0){
                if (null != data)
                    etUsername.setText(data.getStringExtra("userName"));
            }
        }
    }

    @Override
    public void loginSuc(String requestMethod,TryLogin tryLogin) {

        if (tryLogin != null) {
            int value = tryLogin.getValue();

            String memo = tryLogin.getMemo();

            if (value != 1) {
                ToastUtil.showInfo(this, memo);
            } else {
                ToastUtil.showInfo(this, "login success");
                SharedPreferencesUtils.setStringValue(LoginActivity.this, PreConstants.LSB_USERID, memo.split(",")[0]);
                SharedPreferencesUtils.setStringValue(LoginActivity.this, PreConstants.LSB_USERPWD, CommonUtils.getMD5(etPassword.getText()+memo.split(",")[1]));
                SharedPreferencesUtils.setStringValue(LoginActivity.this, PreConstants.LSB_USERPHONE, etUsername.getText().toString());
                SharedPreferencesUtils.setBooleanValue(LoginActivity.this, PreConstants.LSB_ISLOGIN, true);
                setResult(0,new Intent().putExtra("userName",etUsername.getText().toString()));
                finish();
            }
        }

    }


    public void login() {
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            map.put("loginName", etUsername.getText().toString());
            map.put("Pwd", etPassword.getText().toString());
            loginAsync.setLoginBack(this);
            loginAsync.setContxt(this);
            loginAsync.setUrl(getString(R.string.login_url));
            loginAsync.setRequestMethod(getString(R.string.login_method));
            loginAsync.execute(map, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
