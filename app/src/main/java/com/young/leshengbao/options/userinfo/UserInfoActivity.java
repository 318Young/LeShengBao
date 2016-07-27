package com.young.leshengbao.options.userInfo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.young.leshengbao.R;
import com.young.leshengbao.ansy.AnsyFactory;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.GetInfo;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.ToastUtil;
import com.young.leshengbao.view.YoungApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/7/27.
 */
public class UserInfoActivity extends ParentActivity implements LoginBack{


    private AnsyFactory ansyFactory = null;

    private CommonAsync loginAsync = null;

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
        toolbar.setTitle("用戶中心");
    }

    @Override
    public void initDates() {

        getUserInfo();
    }


    public void getUserInfo() {
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();

            GetInfo info = new GetInfo();
            info.setUserId(YoungApplication.mPreference.getString("userId",""));
            info.setUserPwd(YoungApplication.mPreference.getString("userPwd",""));



            String json = "["+new Gson().toJson(info) + "]";
            String xml = Base64.encodeToString(json.getBytes(),Base64.NO_WRAP);

            System.out.println(json);
            Log.w("xml",xml);
            map.put("xml", xml);
            loginAsync.setLoginBack(this);
            loginAsync.setContxt(this);
            loginAsync.setUrl(getString(R.string.userInfo_url));
            loginAsync.setRequestMethod(getString(R.string.getUserInfo_method));
            loginAsync.execute(map, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginSuc(String requestMethod, TryLogin tryLogin) {
        if (getString(R.string.getUserInfo_method).equals(requestMethod)){
            if (null != tryLogin)
                ToastUtil.showInfo(this,tryLogin.getMemo());
        }
    }
}
