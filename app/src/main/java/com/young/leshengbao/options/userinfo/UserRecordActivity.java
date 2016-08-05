package com.young.leshengbao.options.userinfo;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.young.leshengbao.R;
import com.young.leshengbao.adapter.UserRecordAdapter;
import com.young.leshengbao.ansy.AnsyFactory;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.model.UserInfo;
import com.young.leshengbao.model.UserRecord;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.CommonUtils;
import com.young.leshengbao.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流水账单
 */

public class UserRecordActivity extends ParentActivity implements LoginBack{

    private AnsyFactory ansyFactory = null;

    private CommonAsync loginAsync = null;

    private ListView user_record_listView = null ;

    private UserRecordAdapter userRecordAdapter = null ;

    private  List<UserRecord> datas = null ;

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
        user_record_listView = (ListView)findViewById(R.id.user_record_listView);
        userRecordAdapter = new UserRecordAdapter(this);
        datas = new ArrayList<>();
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
                Log.e("memo", tryLogin.getMemo());
                String json = new String(Base64.decode(tryLogin.getMemo().getBytes(), Base64.NO_WRAP));
                Log.e("json", json.substring(1, json.length() - 1));
                try {

                    JSONArray jsonArray = (JSONArray)new JSONArray(json);

                    Gson gson = new Gson();

                    for(int i = 0 ; i< jsonArray.length() ; i++){
                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                        UserRecord userRecord = gson.fromJson(jsonObject.toString(), UserRecord.class);
                        datas.add(userRecord);
                    }

                    if(datas == null  || datas.isEmpty()){
                        ToastUtil.showInfo(this,"流水单报文格式错误");
                        return ;
                    }
                    userRecordAdapter.setDatas(datas);
                    user_record_listView.setAdapter(userRecordAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }else{

                String errorMsg = tryLogin.getMemo();
                ToastUtil.showInfo(this,"流水账单获取失败:"+errorMsg);
            }


        }

    }

    public void getUserRecord(){
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            map.put("xml", CommonUtils.getXml(UserRecordActivity.this));
            System.out.println("xml-----"+CommonUtils.getXml(UserRecordActivity.this));
            map.put("xml", CommonUtils.getXml(UserRecordActivity.this));
            map.put("pageindex" , 0);
            map.put("pagecount", 6);
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
