package com.young.leshengbao.options.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.young.leshengbao.R;
import com.young.leshengbao.ansy.CommonAsync;
import com.young.leshengbao.ansy.ConcreFactory;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.parentclass.ParentActivity;
import com.young.leshengbao.utils.Code;
import com.young.leshengbao.utils.CommonUtils;
import com.young.leshengbao.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

public class SafeSettingActivity extends ParentActivity implements View.OnClickListener, LoginBack {

    private EditText  et_yzm ,et_safe_num , et_again_new_safe_password , et_old_safe_num;

    private LinearLayout lyOldSafeNum;

    private Button  bt_confirm;

    private TextView next_page ;

    private ImageView image_yzm ;

    private Code code  ;

    private Map<String ,Object> params = new HashMap<>();

    private  ConcreFactory   ansyFactory = null;

    private CommonAsync loginAsync = null;

    private Intent intent = null ;

    private  String flag ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_safe_setting;
    }

    @Override
    public void initViews() {

        et_yzm = (EditText) findViewById(R.id.et_yzm);
        et_safe_num = (EditText) findViewById(R.id.et_safe_num);
        et_again_new_safe_password = (EditText) findViewById(R.id.et_again_new_safe_password);

        bt_confirm = (Button) findViewById(R.id.bt_confirm);

        next_page = (TextView) findViewById(R.id.next_page);

        image_yzm = (ImageView) findViewById(R.id.image_get_yzm);

        lyOldSafeNum = (LinearLayout) findViewById(R.id.ly_old_safe_num);

        et_old_safe_num = (EditText)findViewById(R.id.et_old_safe_num);

        intent = getIntent();
        flag =  intent.getStringExtra("flag");
        if(TextUtils.isEmpty(flag) || "setting".equals(flag))
            lyOldSafeNum.setVisibility(View.GONE);
        else if(!TextUtils.isEmpty(flag) && "update".equals(flag))
            lyOldSafeNum.setVisibility(View.VISIBLE);
    }

    public void setListenser(){
        bt_confirm.setOnClickListener(this);
        next_page.setOnClickListener(this);
    }
    @Override
    public void initDates() {
        image_yzm.setImageBitmap(code.getInstance().createBitmap()
        );
        setListenser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_confirm:/*确定*/

                if(!TextUtils.isEmpty(flag) && "update".equals(flag)){
                    if(TextUtils.isEmpty(et_old_safe_num.getText().toString())){
                        ToastUtil.showInfo(this,"请输入旧安全码");
                        return ;
                    }
                }else if(TextUtils.isEmpty(et_safe_num.getText().toString())){
                    ToastUtil.showInfo(this,"请输入安全码");
                    return ;
                }else if(et_safe_num.getText().toString().length() < 6 || et_safe_num.getText().toString().length() >6 ){
                    ToastUtil.showInfo(this,"请输入6位数安全码");
                    return ;
                }else if(TextUtils.isEmpty(et_again_new_safe_password.getText().toString()) ){
                    ToastUtil.showInfo(this,"请再次输入安全码");
                    return ;
                }  else if(!et_safe_num.getText().toString().equals(et_again_new_safe_password.getText().toString())){
                    ToastUtil.showInfo(this,"俩次输入的安全码不一致，请重新输入");
                    return ;
                }else if(TextUtils.isEmpty(et_yzm.getText().toString())){
                    ToastUtil.showInfo(this,"请输入验证码");
                    return ;
                }else if(!et_yzm.getText().toString().equals(code.getInstance().getCode().toString().trim())){
                    ToastUtil.showInfo(this,"code："+code.getInstance().getCode().toString().trim());
                    ToastUtil.showInfo(this,"验证码不正确");
                    return ;
                }else{
                    setSafePwd();
                }

                break;
            case R.id.next_page:/*换验证码*/
                image_yzm.setImageBitmap(code.getInstance().createBitmap());
                break;

        }
}
    public void setSafePwd() {
        try {
            ansyFactory = new ConcreFactory();
            loginAsync = ansyFactory.createAnsyProduct(CommonAsync.class);
            params.put("xml", CommonUtils.getXml(this));
            params.put("oldpwd" ,et_old_safe_num.getText().toString());
            params.put("newpwd" ,et_safe_num.getText().toString());
            loginAsync.setLoginBack(this);
            loginAsync.setContxt(this);
            loginAsync.setUrl(getString(R.string.userInfo_url));
            loginAsync.setRequestMethod(getString(R.string.ChangeSafePwd));
            loginAsync.execute(params, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginSuc(String requestMethod, TryLogin tryLogin) {
        try {
            if (null != tryLogin) {
                if (1 == tryLogin.getValue()) {
                    ToastUtil.showInfo(this,"操作成功");
                } else {
                    ToastUtil.showInfo(this, tryLogin.getMemo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
