package com.young.leshengbao.options.userinfo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Base64;

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
import com.young.leshengbao.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/7/27.
 */
public class UserInfoActivity extends ParentActivity implements LoginBack {


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
        getNoReadMsgCount();
    }


    public void getUserInfo() {
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            map.put("xml", CommonUtils.getXml());
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
            map.put("xml", CommonUtils.getXml());
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
                        ToastUtil.showInfo(this, info.toString());
                    } else if (getString(R.string.getNoReadMsgCount).equals(requestMethod)) {
                        int noReadMsgcount = Integer.valueOf(tryLogin.getMemo());/*未读信息条数*/
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
}
