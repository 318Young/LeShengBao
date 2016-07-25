package com.young.leshengbao.options.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.young.leshengbao.R;
import com.young.leshengbao.ansy.AnsyFactory;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.ansy.LoginAsync;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends ParentActivity implements View.OnClickListener, LoginBack {

    private EditText etUsername;
    private EditText etPassword;

    private Toolbar toolbar;

    private AnsyFactory ansyFactory = null;

    private CommonAsync loginAsync = null;

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.bt_register).setOnClickListener(this);

        toolbar.setTitle(getString(R.string.bt_login));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void initDates() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                if (TextUtils.isEmpty(etUsername.getText())) {
                    ToastUtil.showInfo(LoginActivity.this, "请输入用户名!");
                    return;
                }
                if (TextUtils.isEmpty(etPassword.getText())) {
                    ToastUtil.showInfo(LoginActivity.this, "请输入密码!");
                    return;
                }
                login();

                break;
            case R.id.bt_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default:
                break;
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
            /*登录成功，返回主菜单页面*/
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
            loginAsync.setRequestMethod(getString(R.string.login_method));
            loginAsync.execute(map, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
