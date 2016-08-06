package com.young.leshengbao.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.young.leshengbao.R;
import com.young.leshengbao.model.MyMsg;
import com.young.leshengbao.view.YoungApplication;

import java.util.List;

/**
 * Created by OnceDaily on 2016/8/2.
 */
public class MyMsgAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<MyMsg> datas;
    private Handler mHandler;

    public MyMsgAdapter(Context context, List<MyMsg> datas, Handler mHandler) {
        inflater = LayoutInflater.from(context);
        this.datas = datas;
        this.mHandler = mHandler;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public MyMsg getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final MyMsg msg = getItem(position);
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.my_msg_item, null);
            holder.myMsgType = (TextView) convertView.findViewById(R.id.tv_msg_type);
            holder.myMsgIsRead = (TextView) convertView.findViewById(R.id.tv_msg_is_read);
            holder.myMsgCreateTime = (TextView) convertView.findViewById(R.id.tv_msg_create_time);
            holder.myMsgContent = (TextView) convertView.findViewById(R.id.tv_msg_content);
            holder.myMsgIsShow = (CheckBox) convertView.findViewById(R.id.cb_msg_is_show);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.myMsgType.setText(msg.getM_type());
        switch (msg.getM_isread()) {
            case 1:
                holder.myMsgIsRead.setText("已读");
                break;
            case 0:
                holder.myMsgIsRead.setText("未读");
                holder.myMsgIsRead.setTextColor(YoungApplication.getInstance().getResources().getColor(android.R.color.holo_red_dark));
                break;
        }
        holder.myMsgCreateTime.setText(msg.getM_createtime());

        holder.myMsgIsShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    holder.myMsgContent.setVisibility(View.VISIBLE);
                }else {
                    holder.myMsgContent.setVisibility(View.GONE);
                }
                if (msg.getM_isread() == 0){
                    Message message = new Message();
                    message.arg1 = position;
                    mHandler.sendMessage(message);
                }
            }
        });
        holder.myMsgContent.setText(msg.getM_content());
        return convertView;
    }

    class ViewHolder {
        TextView myMsgType;
        TextView myMsgIsRead;
        TextView myMsgCreateTime;
        TextView myMsgContent;
        CheckBox myMsgIsShow;
    }
}
