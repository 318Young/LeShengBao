package com.young.leshengbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.young.leshengbao.R;
import com.young.leshengbao.model.UserRecord;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class UserRecordAdapter extends BaseAdapter {

    private Context cx = null ;

    private List<UserRecord> datas = null ;

    private LayoutInflater inflater;

    private String mouth = "*月" ;

    private String createtime ;

    public UserRecordAdapter(Context cx){
        this.cx = cx ;
        inflater = LayoutInflater.from(cx);
    }

    public List<UserRecord> getDatas() {
        return datas;
    }

    public void setDatas(List<UserRecord> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return this.datas.size();
    }

    @Override
    public Object getItem(int position) {
        return this.datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int result = 0 ;
        if(mouth.equals(createtime))
            result = 0;
        else
            result = 1 ;

        return result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 viewHolder1 = null ;
        ViewHolder2 viewHolder2 = null ;

        String mouth = "" ,mouth_day , time ;
        try {
            UserRecord userRecord = (UserRecord)getItem(position);
            createtime = userRecord.getCreatetime().split("-")[1];
            int type = getItemViewType(position);
            System.out.println("type:"+type);
            this.mouth = this.createtime ;
            if(convertView == null){
                switch(type){
                    case 1:
                        convertView = inflater.inflate(R.layout.user_record_item_one,parent , false);
                        viewHolder1 = new ViewHolder1();
                        viewHolder1.tv_mouth = (TextView)convertView.findViewById(R.id.tv_mouth);

                        viewHolder1.header = (ImageView)convertView.findViewById(R.id.header);

                        viewHolder1.tv_day = (TextView)convertView.findViewById(R.id.tv_day);
                        viewHolder1.tv_week = (TextView)convertView.findViewById(R.id.tv_week);

                        viewHolder1.tv_typecn = (TextView)convertView.findViewById(R.id.tv_typecn);
                        viewHolder1.tv_type_info = (TextView)convertView.findViewById(R.id.tv_type_info);

                        convertView.setTag(viewHolder1);

                        break;
                    case 0:
                        convertView = inflater.inflate(R.layout.user_record_item_two,parent , false);

                        viewHolder2 = new ViewHolder2();

                        viewHolder2.header = (ImageView)convertView.findViewById(R.id.header);

                        viewHolder2.tv_day = (TextView)convertView.findViewById(R.id.tv_day);
                        viewHolder2.tv_week = (TextView)convertView.findViewById(R.id.tv_week);

                        viewHolder2.tv_typecn = (TextView)convertView.findViewById(R.id.tv_typecn);
                        viewHolder2.tv_type_info = (TextView)convertView.findViewById(R.id.tv_type_info);

                        convertView.setTag(viewHolder2);

                        break;
                }
            }else{
                switch(type){
                    case 1:
                        viewHolder1 = (ViewHolder1)convertView.getTag();
                        break;
                    case 0:
                        viewHolder2 = (ViewHolder2)convertView.getTag();
                        break;
                }
            }
        /*设置资源*/
            switch(type){
                case 1:
                    mouth = userRecord.getCreatetime().substring(5,7) ;
                    mouth_day= userRecord.getCreatetime().substring(5,10) ;
                    time = userRecord.getCreatetime_d().substring(11,19);
                    if(mouth.startsWith("0"))
                        viewHolder1.tv_mouth.setText(mouth.substring(1, 2)+cx.getString(R.string.mouth));
                    else
                        viewHolder1.tv_mouth.setText(mouth+cx.getString(R.string.mouth));

                    viewHolder1.tv_day.setText(mouth_day);
                    viewHolder1.tv_week.setText(time);
                    if("1".equals(userRecord.getFlag())){
                        viewHolder1.tv_typecn.setText(userRecord.getXtb());
                        viewHolder1.tv_type_info.setText("充值"+userRecord.getXtb());
                    }

                    else if("2".equals(userRecord.getFlag())){
                        viewHolder1.tv_typecn.setText("-"+userRecord.getXtb());
                        viewHolder1.tv_type_info.setText("消费"+userRecord.getXtb());
                    }


                    break;
                case 0:
                    mouth_day = userRecord.getCreatetime().substring(5,10) ;
                    time = userRecord.getCreatetime_d().substring(11,19);

                    viewHolder2.tv_day.setText(mouth_day);
                    viewHolder2.tv_week.setText(time);

                    if("1".equals(userRecord.getFlag())){
                        viewHolder2.tv_typecn.setText(userRecord.getXtb());
                        viewHolder2.tv_type_info.setText("充值"+userRecord.getXtb());
                    }else if("2".equals(userRecord.getFlag())){
                        viewHolder2.tv_typecn.setText("-"+userRecord.getXtb());
                        viewHolder2.tv_type_info.setText("消费"+userRecord.getXtb());
                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

     class ViewHolder1 {
         TextView  tv_mouth , tv_week , tv_day , tv_typecn , tv_type_info ;
         ImageView header ;
     }

    class ViewHolder2 {
        TextView  tv_week , tv_day , tv_typecn , tv_type_info ;
        ImageView header ;
    }
}
