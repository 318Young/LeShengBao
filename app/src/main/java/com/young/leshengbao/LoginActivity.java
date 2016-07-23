package com.young.leshengbao;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.service.WebServiceOpforBt;
import com.young.leshengbao.utils.LogUtil;
import com.young.leshengbao.utils.ToastUtil;
import com.young.leshengbao.view.stereo.CustomEdittext;
import com.young.leshengbao.view.stereo.CustomTextView;
import com.young.leshengbao.view.stereo.RippleView;
import com.young.leshengbao.view.stereo.StereoView;

import org.ksoap2.serialization.SoapObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends ParentActivity implements View.OnClickListener{

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private LinearLayout lyWelcome;
    private CustomTextView tvWelcome;
    private int translateY;

    private Toolbar toolbar;

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
        lyWelcome = (LinearLayout) findViewById(R.id.ly_welcome);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

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
//            case R.id.rv_email:
//                rvEmail.setiRippleAnimListener(new RippleView.IRippleAnimListener() {
//                    @Override
//                    public void onComplete(View view) {
//                        stereoView.toPre();
//                    }
//                });
//                break;
            case R.id.ly_welcome:
                if (TextUtils.isEmpty(etUsername.getText())) {
                    ToastUtil.showInfo(LoginActivity.this,"请输入用户名!");
                    return;
                }
//                if (TextUtils.isEmpty(etEmail.getText())) {
//                    ToastUtil.showInfo(LoginActivity.this,"请输入邮箱!");
//                    stereoView.setItem(1);
//                    return;
//                }
                if (TextUtils.isEmpty(etPassword.getText())) {
                    ToastUtil.showInfo(LoginActivity.this,"请输入密码!");
                    return;
                }
                startExitAnim();
                break;
        }
    }

    private void startExitAnim() {
        ToastUtil.showInfo(LoginActivity.this,"登录成功 =.=");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loginTest(){
        WebServiceOpforBt webServiceOpforBt = new WebServiceOpforBt();
        Map map = new HashMap<>();
        map.put("loginName","admin");
        map.put("Pwd","123");
        SoapObject soapObject = null ;
        try {
            soapObject = webServiceOpforBt.LoadResult(this.getString(R.string.name_space) ,this.getString(R.string.url_address),this.getString(R.string.login_method),map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
