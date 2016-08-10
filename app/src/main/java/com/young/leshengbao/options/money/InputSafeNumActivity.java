package com.young.leshengbao.options.money;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.young.leshengbao.R;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by OnceDaily on 2016/8/9.
 */
public class InputSafeNumActivity extends ParentNoActionBarActivity implements View.OnClickListener, LoginBack {

    private TextView transferTo;
    private TextView transferCharge;
    private EditText safeNum;

    private String transferId;

    private ConcreFactory concreF ;

    private CommonAsync transferConfirmAsync;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Display dp = getWindowManager().getDefaultDisplay();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = (int) (dp.getWidth() * 0.8);
            lp.height = (int) (dp.getHeight() * 0.4);
            getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_input_safe_num;
    }

    @Override
    public void initViews() {

        transferTo = (TextView) findViewById(R.id.tv_transfer_to);
        transferCharge = (TextView) findViewById(R.id.tv_transfer_charge);
        safeNum = (EditText) findViewById(R.id.et_safe_num);
        findViewById(R.id.bt_confirm).setOnClickListener(this);

    }

    @Override
    public void initDates() {
        concreF = new ConcreFactory();
        Intent intent = getIntent();
        transferId = intent.getStringExtra("transferId");
        transferTo.setText("向" + intent.getStringExtra("transferNum") + "转账");
        transferCharge.setText( intent.getStringExtra("transferCharge") + "元" );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm:
                transferConfirm();
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
        map.put("safePwd", safeNum.getText().toString());
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
}
