package com.young.leshengbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.young.leshengbao.R;

import java.util.ArrayList;

/**
 * Created by chenhe on 2016/7/19.
 */
public class TestAdapter extends BaseAdapter{

    Context context;
    ArrayList<String> dataList = new ArrayList<String>();
    public TestAdapter(Context context){
        this.context = context;
        for (int i = 0; i < 66; i++){
            dataList.add("XXXXXX" + i);
        }
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.adapter_test, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(dataList.get(i));
        return view;
    }
}
