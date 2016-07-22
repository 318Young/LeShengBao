package com.young.leshengbao;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.young.leshengbao.utils.LogUtil;
import com.young.leshengbao.utils.ToastUtil;
import com.young.leshengbao.view.stereo.CustomEdittext;
import com.young.leshengbao.view.stereo.CustomTextView;
import com.young.leshengbao.view.stereo.RippleView;
import com.young.leshengbao.view.stereo.StereoView;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private CustomEdittext etUsername;
    private CustomEdittext etEmail;
    private CustomEdittext etPassword;
    private RippleView rvUsername;
    private RippleView rvEmail;
    private RippleView rvPassword;
    private StereoView stereoView;
    private LinearLayout lyWelcome;
    private CustomTextView tvWelcome;
    private int translateY;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//test
        initView();
        stereoView.setStartScreen(1);
        stereoView.post(new Runnable() {
            @Override
            public void run() {
                int[] location = new int[2];
                stereoView.getLocationOnScreen(location);
                translateY = location[1];
            }
        });
        stereoView.setiStereoListener(new StereoView.IStereoListener() {
            @Override
            public void toPre(int curScreen) {
                LogUtil.m("跳转到前一页 "+curScreen);
            }

            @Override
            public void toNext(int curScreen) {
                LogUtil.m("跳转到下一页 "+curScreen);
            }
        });
    }

    private void initView() {
        stereoView = (StereoView) findViewById(R.id.stereoView);
        etUsername = (CustomEdittext) findViewById(R.id.et_username);
        etPassword = (CustomEdittext) findViewById(R.id.et_password);
        rvUsername = (RippleView) findViewById(R.id.rv_username);
        rvPassword = (RippleView) findViewById(R.id.rv_password);
        lyWelcome = (LinearLayout) findViewById(R.id.ly_welcome);
        tvWelcome = (CustomTextView) findViewById(R.id.tv_welcome);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("登录");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvUsername.setOnClickListener(this);
        rvPassword.setOnClickListener(this);
        tvWelcome.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rv_username:
                rvUsername.setiRippleAnimListener(new RippleView.IRippleAnimListener() {
                    @Override
                    public void onComplete(View view) {
                        stereoView.toPre();
                    }
                });
                break;
//            case R.id.rv_email:
//                rvEmail.setiRippleAnimListener(new RippleView.IRippleAnimListener() {
//                    @Override
//                    public void onComplete(View view) {
//                        stereoView.toPre();
//                    }
//                });
//                break;
            case R.id.rv_password:
                rvPassword.setiRippleAnimListener(new RippleView.IRippleAnimListener() {
                    @Override
                    public void onComplete(View view) {
                        stereoView.toPre();
                    }
                });
                break;
            case R.id.tv_welcome:
                if (TextUtils.isEmpty(etUsername.getText())) {
                    ToastUtil.showInfo(LoginActivity.this,"请输入用户名!");
                    stereoView.setItem(2);
                    return;
                }
//                if (TextUtils.isEmpty(etEmail.getText())) {
//                    ToastUtil.showInfo(LoginActivity.this,"请输入邮箱!");
//                    stereoView.setItem(1);
//                    return;
//                }
                if (TextUtils.isEmpty(etPassword.getText())) {
                    ToastUtil.showInfo(LoginActivity.this,"请输入密码!");
                    stereoView.setItem(0);
                    return;
                }
                startExitAnim();
                break;
        }
    }

    private void startExitAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(stereoView, "translationY", 0, 1000, -translateY);
        animator.setDuration(200).start();
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
}
