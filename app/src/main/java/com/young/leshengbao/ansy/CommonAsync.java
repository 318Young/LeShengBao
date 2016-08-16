package com.young.leshengbao.ansy;

import android.content.Context;
import android.os.AsyncTask;

import com.young.leshengbao.R;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.options.customviews.CustomProgressDialog;
import com.young.leshengbao.service.WebServiceOpforBt;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/25.登录
 */
public class CommonAsync extends AsyncTask<Map<String, Object>, Integer, TryLogin> {
    Map<String, Object> param = null;
    WebServiceOpforBt webServiceOpforBt = null;
    private String url = "";

    public LoginBack getLoginBack() {
        return loginBack;
    }

    public void setLoginBack(LoginBack loginBack) {
        this.loginBack = loginBack;
    }

    private LoginBack loginBack = null;

    private Context contxt = null;

    private String requsetMethod = "";

    private String message = "正在加载中";

    public void setMessage(String message) {
        this.message = message;
    }

    public Context getContxt() {
        return contxt;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setContxt(Context contxt) {
        this.contxt = contxt;
    }

    public void setRequestMethod(String requestMethod) {
        this.requsetMethod = requestMethod;
    }

    private CustomProgressDialog customProgressDialog = null ;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        customProgressDialog = new CustomProgressDialog(contxt ,  message ,R.anim.frame);
        customProgressDialog.show();
    }

    @Override
    protected TryLogin doInBackground(Map<String, Object>... params) {
        param = params[0];
        webServiceOpforBt = new WebServiceOpforBt();
        TryLogin tryLogin = null;
        SoapObject soapObject = null;
        try {
            soapObject = webServiceOpforBt.LoadResult(contxt.getString(R.string.name_space), url, requsetMethod, param);

            if (soapObject == null)
                return null;
            int result = Integer.parseInt(soapObject.getProperty("Value").toString());

            String memo = soapObject.getProperty("Memo").toString();

            tryLogin = new TryLogin();
            tryLogin.setValue(result);
            tryLogin.setMemo(memo);


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return tryLogin;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(TryLogin tryLogin) {
        super.onPostExecute(tryLogin);
        loginBack.loginSuc(requsetMethod, tryLogin);
        if(customProgressDialog != null)
            customProgressDialog.dismiss();
    }
}
