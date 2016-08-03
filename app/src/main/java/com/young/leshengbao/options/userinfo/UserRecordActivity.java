package com.young.leshengbao.options.userinfo;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.young.leshengbao.R;
import com.young.leshengbao.ansy.AnsyFactory;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.model.UserRecord;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.CommonUtils;
import com.young.leshengbao.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流水账单
 */

public class UserRecordActivity extends ParentActivity implements LoginBack{

    private AnsyFactory ansyFactory = null;

    private CommonAsync loginAsync = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_user_record;
    }

    @Override
    public void initViews() {
        toolbar.setTitle(getString(R.string.title_activity_user_record));
    }

    @Override
    public void initDates() {
        getUserRecord();
    }

    @Override
    public void loginSuc(String requestMethod, TryLogin tryLogin) {

        if(tryLogin != null){
            if(tryLogin.getValue() == 1){
                /*流水账单获取成功*/
                String json = new String(Base64.decode(tryLogin.getMemo().getBytes(), Base64.NO_WRAP));
                Log.e("json", json.substring(1, json.length() - 1));
                List<UserRecord> datas = new Gson().fromJson( json.substring(1,json.length()-1),  new
                        TypeToken<List<UserRecord>>() {
                        }.getType());
                for(UserRecord userRecord : datas){
                    System.out.println("创建时间:"+userRecord.getCreatetime());
                }
            }else
                ToastUtil.showInfo(this,"流水账单获取失败");

        }

    }

    public void getUserRecord(){
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            map.put("xml", CommonUtils.getXml());
            loginAsync.setLoginBack(this);
            loginAsync.setContxt(this);
            loginAsync.setUrl(getString(R.string.userInfo_url));
            loginAsync.setRequestMethod(getString(R.string.getUserRecord));
            loginAsync.execute(map, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
