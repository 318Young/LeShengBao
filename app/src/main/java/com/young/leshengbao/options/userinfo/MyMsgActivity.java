package com.young.leshengbao.options.userinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.young.leshengbao.R;
import com.young.leshengbao.adapter.MyMsgAdapter;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.MyMsg;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.CommonUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MyMsgActivity extends ParentActivity implements LoginBack {


    private ListView lvMyMsg;
    private ConcreFactory ansyFactory;

    private CommonAsync getMsgsAsync;

    private MyMsgAdapter msgAdapter;
    private List<MyMsg> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutID() {
        return R.layout.my_msg;
    }

    @Override
    public void initViews() {
        lvMyMsg = (ListView) findViewById(R.id.lv_my_msg);
        ansyFactory = new ConcreFactory();
        datas = new ArrayList<>();
    }

    @Override
    public void initDates() {
        initMsgs();
    }

    private void initMsgs() {
        try {
            getMsgsAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            Log.d("MyMsg---", "getXml: "+CommonUtils.getXml(MyMsgActivity.this));
            map.put("xml", CommonUtils.getXml(MyMsgActivity.this));
            map.put("pageindex", 0);
            map.put("pagecount", 10);
            getMsgsAsync.setLoginBack(this);
            getMsgsAsync.setContxt(this);
            getMsgsAsync.setUrl(getString(R.string.userInfo_url));
            getMsgsAsync.setRequestMethod(getString(R.string.getMyMsg_method));
            getMsgsAsync.execute(map, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginSuc(String requestMethod, TryLogin tryLogin) {
        try {
            if (null != tryLogin) {
                if (getString(R.string.getMyMsg_method).equals(requestMethod)) {
                    if (1 == tryLogin.getValue()){
                        String json = new String(Base64.decode(tryLogin.getMemo().getBytes(),Base64.NO_WRAP));
                        JSONArray ja = new JSONArray(json);
                        Gson gson = new Gson();
                        for (int i = 0; i <ja.length() ; i++) {
                            datas.add(gson.fromJson(ja.get(i).toString(),MyMsg.class));
                        }
                        msgAdapter = new MyMsgAdapter(this,datas);
                        lvMyMsg.setAdapter(msgAdapter);
                        Log.e("json",json);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
