package com.young.leshengbao.options.userinfo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.young.leshengbao.R;
import com.young.leshengbao.ansy.AnsyFactory;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.model.UserInfo;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.CommonUtils;
import com.young.leshengbao.utils.SharedPreferencesUtils;
import com.young.leshengbao.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/7/27.
 */
public class UserInfoActivity extends ParentActivity implements LoginBack, View.OnClickListener {


    private AnsyFactory ansyFactory = null;

    private CommonAsync loginAsync = null;
    private LinearLayout ll_message;
    private LinearLayout ll_me_lb;
    private LinearLayout ll_create_time;
    private LinearLayout ll_ls_zd;
    private TextView tv_logout;

    private TextView tv_userName;
    private TextView tv_msg_count;
    private TextView tv_lb_value;
    private TextView tv_chongzhi;
    private TextView tv_time;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initViews() {
        toolbar.setTitle("");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ll_message = (LinearLayout) findViewById(R.id.ll_message);
        ll_me_lb = (LinearLayout) findViewById(R.id.ll_me_lb);
        ll_create_time = (LinearLayout) findViewById(R.id.ll_create_time);
        ll_ls_zd = (LinearLayout) findViewById(R.id.ll_ls_zd);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_msg_count = (TextView) findViewById(R.id.tv_msg_count);
        tv_lb_value = (TextView) findViewById(R.id.tv_lb_value);
        tv_chongzhi = (TextView) findViewById(R.id.tv_chongzhi);
        tv_time = (TextView) findViewById(R.id.tv_time);

        ll_message.setOnClickListener(this);
        ll_me_lb.setOnClickListener(this);
        ll_create_time.setOnClickListener(this);
        ll_ls_zd.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
        tv_chongzhi.setOnClickListener(this);
    }

    @Override
    public void initDates() {

        getUserInfo();
        getNoReadMsgCount();
    }


    public void getUserInfo() {
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            map.put("xml", CommonUtils.getXml(UserInfoActivity.this));
            loginAsync.setLoginBack(this);
            loginAsync.setContxt(this);
            loginAsync.setUrl(getString(R.string.userInfo_url));
            loginAsync.setRequestMethod(getString(R.string.getUserInfo_method));
            loginAsync.execute(map, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getNoReadMsgCount() {
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            map.put("xml", CommonUtils.getXml(UserInfoActivity.this));
            loginAsync.setLoginBack(this);
            loginAsync.setContxt(this);
            loginAsync.setUrl(getString(R.string.userInfo_url));
            loginAsync.setRequestMethod(getString(R.string.getNoReadMsgCount));
            loginAsync.execute(map, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginSuc(String requestMethod, TryLogin tryLogin) {
        try {
            if (null != tryLogin) {
                if (1 == tryLogin.getValue()) {
                    if (getString(R.string.getUserInfo_method).equals(requestMethod)) {
                        String json = new String(Base64.decode(tryLogin.getMemo().getBytes(), Base64.NO_WRAP));
                        JSONObject jb = (JSONObject) new JSONArray(json).get(0);
                        UserInfo info = new Gson().fromJson(jb.toString(), UserInfo.class);
                        tv_userName.setText(info.getU_mobile());
                        NumberFormat nf = new DecimalFormat("0.00¥");
                        String str = nf.format(info.getU_xtb());
                        tv_lb_value.setText(str);
                        tv_time.setText(info.getU_createtime());
                        ToastUtil.showInfo(this, info.toString());
                    } else if (getString(R.string.getNoReadMsgCount).equals(requestMethod)) {
                        int noReadMsgcount = Integer.valueOf(tryLogin.getMemo());/*未读信息条数*/
                        tv_msg_count.setText(String.valueOf(noReadMsgcount));
                        ToastUtil.showInfo(this, noReadMsgcount + "");
                    }
                } else {
                    ToastUtil.showInfo(this, tryLogin.getMemo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_message:

                break;
            case R.id.ll_me_lb:

                break;
            case R.id.tv_chongzhi:

                break;
            case R.id.ll_create_time:

                break;
            case R.id.ll_ls_zd:

                break;
            case R.id.tv_logout:
                SharedPreferencesUtils.clearAll(UserInfoActivity.this);
                finish();
                break;

        }
    }
}
