package com.young.leshengbao.ansy;

import android.os.AsyncTask;

/**
 * Created by Administrator on 2016/7/25.
 */
public class ConcreFactory extends AnsyFactory{

    @Override
    public <T extends AsyncTask> T createAnsyProduct(Class<T> clz) {
        AsyncTask asyncTask = null ;
        try{
            asyncTask =(AsyncTask)Class.forName(clz.getName()).newInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
        return (T)asyncTask;
    }
}
