package com.young.leshengbao.options.money;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.young.leshengbao.R;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.CommonUtils;
import com.young.leshengbao.utils.PreConstants;
import com.young.leshengbao.utils.SharedPreferencesUtils;
import com.young.leshengbao.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by OnceDaily on 2016/8/9.
 */
public class TransferMoneyActivity extends ParentActivity implements View.OnClickListener, LoginBack {


    private EditText otherNum;
    private EditText charge;
    private ConcreFactory concreFactory;
    private CommonAsync transferAsync;

    private Button transferConfirm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_transfer_money;
    }

    @Override
    public void initViews() {
        otherNum = (EditText) findViewById(R.id.et_other_num);
        charge = (EditText) findViewById(R.id.et_charge);

        transferConfirm = (Button) findViewById(R.id.bt_confirm);
        transferConfirm.setOnClickListener(this);

    }

    @Override
    public void initDates() {
        concreFactory = new ConcreFactory();

        otherNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(charge.getText().toString()) && !TextUtils.isEmpty(s.toString())){
                    transferConfirm.setEnabled(true);
                }else {
                    transferConfirm.setEnabled(false);
                }
            }
        });
        charge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(otherNum.getText().toString()) && !TextUtils.isEmpty(s.toString())){
                    transferConfirm.setEnabled(true);
                }else {
                    transferConfirm.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm:
                getTransferMsgID();
                break;
        }
    }

    private void getTransferMsgID() {
        transferAsync = concreFactory.createAnsyProduct(CommonAsync.class);
        Map<String, Object> map = new HashMap();
        map.put("xml", CommonUtils.getXml(this));
        map.put("code", SharedPreferencesUtils.getStringValue(this, PreConstants.LSB_USERID,""));
        map.put("oldAccount", otherNum.getText().toString());
        map.put("xtb", charge.getText().toString());
        transferAsync.setLoginBack(this);
        transferAsync.setContxt(this);
        transferAsync.setUrl(getString(R.string.user_url));
        transferAsync.setRequestMethod(getString(R.string.getTransferMsgID));
        transferAsync.execute(map, null, null);
    }

    @Override
    public void loginSuc(String requestMethod, TryLogin tryLogin) {
        if (null != tryLogin){
            if (1 == tryLogin.getValue()){
                if(getString(R.string.getTransferMsgID).equals(requestMethod)){
                    Intent intent = new Intent(this,InputSafeNumActivity.class);
                    intent.putExtra("transferNum",otherNum.getText().toString());
                    intent.putExtra("transferCharge",charge.getText().toString());
                    intent.putExtra("transferId",tryLogin.getMemo());
                    startActivity(intent);
                }
            }else {
                ToastUtil.showInfo(this,tryLogin.getMemo());
            }
        }
    }
}
