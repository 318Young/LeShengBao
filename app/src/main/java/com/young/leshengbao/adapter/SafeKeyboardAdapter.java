package com.young.leshengbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.young.leshengbao.R;

import java.util.List;

/**
 * Created by OnceDaily on 2016/8/12.
 */
public class SafeKeyboardAdapter extends BaseAdapter {

    private List<String> datas;
    private LayoutInflater inflater;

    public SafeKeyboardAdapter(Context context, List<String> datas) {
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.safe_keyboard_item, null);
            holder.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.tvNum.setText(getItem(position));
        return convertView;
    }

    class ViewHolder {
        TextView tvNum;
    }
}
