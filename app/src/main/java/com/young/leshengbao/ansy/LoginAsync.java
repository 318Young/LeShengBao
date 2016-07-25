package com.young.leshengbao.ansy;

import android.content.Context;
import android.os.AsyncTask;

import com.young.leshengbao.R;
import com.young.leshengbao.inter.LoginBack;
import com.young.leshengbao.model.TryLogin;
import com.young.leshengbao.service.WebServiceOpforBt;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/25.登录
 */
public class LoginAsync extends AsyncTask<Map<String ,String> , Integer ,TryLogin>{
    Map<String ,String> param = null ;
    WebServiceOpforBt webServiceOpforBt = null ;

    public LoginBack getLoginBack() {
        return loginBack;
    }

    public void setLoginBack(LoginBack loginBack) {
        this.loginBack = loginBack;
    }

    private LoginBack loginBack = null ;

    private Context contxt = null ;

    public Context getContxt() {
        return contxt;
    }

    public void setContxt(Context contxt) {
        this.contxt = contxt;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected TryLogin doInBackground(Map<String, String>... params) {
        param = params[0];
        String loginName = param.get("loginName");
        String passWord = param.get("Pwd");
        webServiceOpforBt = new WebServiceOpforBt();
        TryLogin tryLogin = null ;
        Map map = new HashMap<>();
        map.put("loginName", loginName);
        map.put("Pwd", passWord);
        SoapObject soapObject = null;
        try {
            soapObject = webServiceOpforBt.LoadResult(contxt.getString(R.string.name_space), contxt.getString(R.string.url_address), contxt.getString(R.string.login_method), map);

            if(soapObject == null)
                return null;
            int result = Integer.parseInt(soapObject.getProperty("Value").toString());

            String memo =soapObject.getProperty("Memo").toString();

            tryLogin= new TryLogin();
            tryLogin.setValue(result);
            tryLogin.setMemo(memo);


        } catch (Exception e) {
            e.printStackTrace();
            return null ;
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
        loginBack.loginSuc(tryLogin);
    }
}
