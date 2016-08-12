package com.young.leshengbao.options.money;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.young.leshengbao.R;
import com.young.leshengbao.adapter.SafeKeyboardAdapter;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.parentclass.ParentNoActionBarActivity;
import com.young.leshengbao.utils.CommonUtils;
import com.young.leshengbao.utils.PreConstants;
import com.young.leshengbao.utils.SharedPreferencesUtils;
import com.young.leshengbao.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OnceDaily on 2016/8/9.
 */
public class InputSafeNumActivity extends ParentNoActionBarActivity implements View.OnClickListener, LoginBack, AdapterView.OnItemClickListener {

    private TextView transferTo;
    private TextView transferCharge;

    private String transferId;

    private ConcreFactory concreF ;

    private CommonAsync transferConfirmAsync;

    private GridView safeKeyboard;

    private TextView[] tvNum ;

    private String safeNum;

    private int currentIndex = -1;

    private LinearLayout llKeyboard;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_input_safe_num;
    }

    @Override
    public void initViews() {

        transferTo = (TextView) findViewById(R.id.tv_transfer_to);
        transferCharge = (TextView) findViewById(R.id.tv_transfer_charge);
        safeKeyboard = (GridView) findViewById(R.id.gv_keyboard);
        findViewById(R.id.ll_keyboard).setOnClickListener(this);

        tvNum = new TextView[6];
        tvNum[0] = (TextView) findViewById(R.id.num_1);
        tvNum[1] = (TextView) findViewById(R.id.num_2);
        tvNum[2] = (TextView) findViewById(R.id.num_3);
        tvNum[3] = (TextView) findViewById(R.id.num_4);
        tvNum[4] = (TextView) findViewById(R.id.num_5);
        tvNum[5] = (TextView) findViewById(R.id.num_6);

    }

    @Override
    public void initDates() {
        concreF = new ConcreFactory();
        Intent intent = getIntent();
        transferId = intent.getStringExtra("transferId");
        transferTo.setText("向" + intent.getStringExtra("transferNum") + "转账");
        transferCharge.setText( intent.getStringExtra("transferCharge") + "元" );

        findViewById(R.id.iv_back).setOnClickListener(this);

        safeKeyboard.setAdapter(new SafeKeyboardAdapter(this,getKeyboardNum()));

        safeKeyboard.setOnItemClickListener(this);

        tvNum[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1){
                    safeNum = "";
                    for (int i = 0; i < tvNum.length ; i++) {
                        safeNum += tvNum[i].getText().toString().trim();
                    }
                    transferConfirm();
                }
            }
        });
    }

    private List<String> getKeyboardNum() {
        List<String> keyboardNum = new ArrayList<>();
        for (int i = 1; i < 13 ; i++) {
            if (i<10){
                keyboardNum.add(String.valueOf(i));
            }else if (i == 10){
                keyboardNum.add("清空");
            }else if (i == 11){
                keyboardNum.add(String.valueOf(0));
            }else if (i == 12){
                keyboardNum.add("×");
            }
        }
        return keyboardNum;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_keyboard:
                if (safeKeyboard.getVisibility() == View.GONE){
                    safeKeyboard.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void transferConfirm() {
        transferConfirmAsync = concreF.createAnsyProduct(CommonAsync.class);
        Map<String, Object> map = new HashMap();
        map.put("xml", CommonUtils.getXml(this));
        map.put("code", SharedPreferencesUtils.getStringValue(this, PreConstants.LSB_USERID,""));
        map.put("safePwd", safeNum);
        map.put("transferid", transferId);
        transferConfirmAsync.setLoginBack(this);
        transferConfirmAsync.setContxt(this);
        transferConfirmAsync.setUrl(getString(R.string.user_url));
        transferConfirmAsync.setRequestMethod(getString(R.string.transferConfirmMethod));
        transferConfirmAsync.execute(map, null, null);
    }

    @Override
    public void loginSuc(String requestMethod, TryLogin tryLogin) {
        if (null != tryLogin){
            if (1 == tryLogin.getValue()){
                if(getString(R.string.transferConfirmMethod).equals(requestMethod)){
                    ToastUtil.showInfo(this,tryLogin.getMemo());
                    finish();
                }
            }else {
                ToastUtil.showInfo(this,tryLogin.getMemo());
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position<11 && position != 9){
            if(currentIndex>=-1 && currentIndex < 5){
                tvNum[++currentIndex].setText(String.valueOf( parent.getItemAtPosition(position)));
            }
        }else if (position == 11){
            if (currentIndex - 1 >= -1){
                tvNum[currentIndex--].setText("");
            }
        }else if (position == 9){
            for (int i = 0; i < tvNum.length; i++) {
                tvNum[i].setText("");
            }
            currentIndex = -1;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if (safeKeyboard.getVisibility() == View.VISIBLE){
                safeKeyboard.setVisibility(View.GONE);
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
