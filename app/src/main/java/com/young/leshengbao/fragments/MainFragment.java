package com.young.leshengbao.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import com.google.gson.Gson;
import com.young.leshengbao.R;
import com.young.leshengbao.adapter.MainFragmentAdapter;
import com.young.leshengbao.ansy.AnsyFactory;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.ShopModel;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.options.customviews.OnRefreshListener;
import com.young.leshengbao.options.customviews.RefreshListView;
import com.young.leshengbao.utils.CommonUtils;
import com.young.leshengbao.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhe on 2016/7/19.
 */
public class MainFragment extends Fragment implements LoginBack, OnRefreshListener {
    private Activity mActivity;
    private RefreshListView mListView;
    private AnsyFactory ansyFactory = null;
    private MainFragmentAdapter mAdapter = null;

    private CommonAsync loginAsync = null;
    private ArrayList<ShopModel> dataList = new ArrayList<>();
    private int currentPageIndex = 0;
    private boolean refreshOrMore = false;
    private boolean isRequesting = false;/*这个下拉刷新有问题，会出现判断不准确，下拉和上拉同时发生，暂时用这个区分*/
    // 搜索框
    ImageButton clearSearch;
    EditText query;
    private InputMethodManager inputMethodManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_list, null);
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mListView = (RefreshListView) mActivity.findViewById(R.id.rlv);
        inputMethodManager = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        clearSearch = (ImageButton)mActivity.findViewById(R.id.search_clear);
        query = (EditText)mActivity.findViewById(R.id.query);
        query.setHint("请输入商户名称");
        mAdapter = new MainFragmentAdapter(mActivity, dataList);
        mListView.setAdapter(mAdapter);
//        ToolUtils.setListViewHeightBasedOnChildren(mListView);
        mListView.setOnRefreshListener(this);
        setListener();
        getAccountRecord("",true);

    }

    public void setListener(){
        query.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mAdapter.getFilter().filter(s);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {

                System.out.println("商户名称:"+s.toString());
                refreshOrMore = true ;
                getAccountRecord(s.toString(),true);
                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                } else {
                    clearSearch.setVisibility(View.INVISIBLE);

                }
            }
        });
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.getText().clear();
                hideSoftKeyboard();
            }
        });
    }

    void hideSoftKeyboard() {
        if (mActivity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (mActivity.getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    @Override
    public void loginSuc(String requestMethod, TryLogin tryLogin) {

        isRequesting = false;
        if(refreshOrMore)
            mListView.hideHeaderView();
        else
            mListView.hideFooterView();

        if(tryLogin != null){
            if(tryLogin.getValue() == 1){
                /*获取成功*/
                Log.e("memo", tryLogin.getMemo());
                String json = new String(Base64.decode(tryLogin.getMemo().getBytes(), Base64.NO_WRAP));
                Log.e("json", json.substring(1, json.length() - 1));
                try {

                    JSONArray jsonArray = new JSONArray(json);

                    Gson gson = new Gson();

                    if(refreshOrMore)
                        dataList.clear();

                    ArrayList<ShopModel> tempList = new ArrayList<>();
                    for(int i = 0 ; i< jsonArray.length() ; i++){
                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                        ShopModel shopModel = gson.fromJson(jsonObject.toString(), ShopModel.class);
                        tempList.add(shopModel);
                    }
                    dataList.addAll(tempList);
                    if(dataList == null  || dataList.isEmpty()){
                        ToastUtil.showInfo(mActivity,"格式错误");
                        return ;
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{

                String errorMsg = tryLogin.getMemo();
                ToastUtil.showInfo(mActivity,"商户信息获取失败:"+errorMsg);
            }


        }
    }


    public void getAccountRecord(String name ,boolean flag){
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            Map<String, Object> map = new HashMap();
            map.put("xml", CommonUtils.getXml(mActivity));
            System.out.println("xml-----" + CommonUtils.getXml(mActivity));
            map.put("xml", CommonUtils.getXml(mActivity));
            map.put("pageindex" , currentPageIndex);
            map.put("pagecount", 10);
            map.put("name", name);
            loginAsync.setLoginBack(this);
            loginAsync.setContxt(mActivity);
            loginAsync.setUrl(getString(R.string.userInfo_url));
            loginAsync.setRequestMethod(getString(R.string.getAccountRecord));
            loginAsync.setProgressFlag(flag);
            loginAsync.execute(map, null, null);
            isRequesting = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDownPullRefresh() {
        currentPageIndex = 0 ;
        refreshOrMore = true;
        getAccountRecord("",true);
    }

    @Override
    public void onLoadingMore() {
        if (!isRequesting){
            currentPageIndex++;
            refreshOrMore = false ;
            getAccountRecord("",true);
        }

    }
}
