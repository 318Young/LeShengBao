package com.young.leshengbao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.young.leshengbao.ansy.AnsyFactory;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.ansy.LoginAsync;
import com.young.leshengbao.interfaces.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.service.WebServiceOpforBt;
import com.young.leshengbao.utils.ToastUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends ParentActivity implements View.OnClickListener ,LoginBack {

    private EditText etUsername;
    private EditText etPassword;

    private Toolbar toolbar;

    private AnsyFactory ansyFactory = null ;

    private LoginAsync loginAsync = null;

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

        toolbar.setTitle("登录");
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
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void loginSuc(TryLogin tryLogin) {

        if(tryLogin!= null){
            int value = tryLogin.getValue();

            String memo = tryLogin.getMemo();

            if(value != 1){
                ToastUtil.showInfo(this , memo);
            }else{
            /*登录成功，返回主菜单页面*/
            }
        }

    }

    public void login(){
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(LoginAsync.class);
            Map<String , String> map = new HashMap();
            map.put("loginName",etUsername.getText().toString());
            map.put("Pwd",etPassword.getText().toString());
            loginAsync.setLoginBack(this);
            loginAsync.setContxt(this);
            loginAsync.execute(map,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
