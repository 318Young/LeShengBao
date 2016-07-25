package com.young.leshengbao.ansy;

import android.os.AsyncTask;

/**
 * Created by Administrator on 2016/7/25.
 */
public abstract  class AnsyFactory {

    public abstract <T extends AsyncTask> T createAnsyProduct(Class<T> clz);
}
