package com.young.leshengbao.inter;

import com.young.leshengbao.model.TryLogin;

/**
 * Created by Administrator on 2016/7/25.
 */
public interface LoginBack {

    /**
     * 请求回调接口
     * @param requestMethod 请求方法，用来区分同一页面多个请求
     * @param tryLogin
     */

    public void loginSuc(String requestMethod,TryLogin tryLogin);

}
