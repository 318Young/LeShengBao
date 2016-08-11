package com.young.leshengbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.young.leshengbao.R;
import com.young.leshengbao.model.UserRecord;
import com.young.leshengbao.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class UserRecordAdapter extends BaseAdapter {

    private Context cx = null ;

    private List<UserRecord> datas = null ;

    private LayoutInflater inflater;

    private String lastmouth = "*月" ;

    private String currentMouth ;

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

//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        int result = 0 ;
//        if(mouth.equals(createtime))
//            result = 0;
//        else
//            result = 1 ;
//
//        return result;
//    }

    public int getItemType(String lastMouth, String currentMouth) {

        int result = 0;
        if (lastMouth.equals(currentMouth))
            result = 0;
        else
            result = 1;

        return result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 viewHolder1 = null ;
        ViewHolder2 viewHolder2 = null ;

        String mouth_day , time ;
        String[] year_mouth_day ;
        try {
            UserRecord userRecord = (UserRecord)getItem(position);

            currentMouth = userRecord.getCreatetime().split("-")[1];

            if(position > 0)
                lastmouth = ((UserRecord)getItem(position-1)).getCreatetime().split("-")[1];
            else
                lastmouth = "**" ;

            int type = getItemType(lastmouth , currentMouth);

            System.out.println("type:"+type);

            if(convertView == null){
//                switch(type){
//                    case 1:
//                        convertView = inflater.inflate(R.layout.user_record_item_one,parent , false);
//                        viewHolder1 = new ViewHolder1();
//                        viewHolder1.tv_mouth = (TextView)convertView.findViewById(R.id.tv_mouth);
//
//                        viewHolder1.header = (ImageView)convertView.findViewById(R.id.header);
//
//                        viewHolder1.tv_day = (TextView)convertView.findViewById(R.id.tv_day);
//                        viewHolder1.tv_week = (TextView)convertView.findViewById(R.id.tv_week);
//
//                        viewHolder1.tv_typecn = (TextView)convertView.findViewById(R.id.tv_typecn);
//                        viewHolder1.tv_type_info = (TextView)convertView.findViewById(R.id.tv_type_info);
//
//                        convertView.setTag(viewHolder1);
//
//                        break;
//                    case 0:
//                        convertView = inflater.inflate(R.layout.user_record_item_two,parent , false);
//
//                        viewHolder2 = new ViewHolder2();
//
//                        viewHolder2.header = (ImageView)convertView.findViewById(R.id.header);
//
//                        viewHolder2.tv_day = (TextView)convertView.findViewById(R.id.tv_day);
//                        viewHolder2.tv_week = (TextView)convertView.findViewById(R.id.tv_week);
//
//                        viewHolder2.tv_typecn = (TextView)convertView.findViewById(R.id.tv_typecn);
//                        viewHolder2.tv_type_info = (TextView)convertView.findViewById(R.id.tv_type_info);
//
//                        convertView.setTag(viewHolder2);
//
//                        break;
//                }
                convertView = inflater.inflate(R.layout.user_record_item_one,parent , false);
                viewHolder1 = new ViewHolder1();

                viewHolder1.head = (LinearLayout)convertView.findViewById(R.id.head);

                if(type == 0)
                    viewHolder1.head.setVisibility(View.GONE);

                viewHolder1.tv_mouth = (TextView)convertView.findViewById(R.id.tv_mouth);

                viewHolder1.header = (ImageView)convertView.findViewById(R.id.header);

                viewHolder1.tv_day = (TextView)convertView.findViewById(R.id.tv_day);
                viewHolder1.tv_week = (TextView)convertView.findViewById(R.id.tv_week);

                viewHolder1.tv_typecn = (TextView)convertView.findViewById(R.id.tv_typecn);
                viewHolder1.tv_type_info = (TextView)convertView.findViewById(R.id.tv_type_info);

                convertView.setTag(viewHolder1);

            }else{
                switch(type){
                    case 1:
                        viewHolder1 = (ViewHolder1)convertView.getTag();
                        viewHolder1.head.setVisibility(View.VISIBLE);
                        break;
                    case 0:
                        viewHolder1 = (ViewHolder1)convertView.getTag();
                        viewHolder1.head.setVisibility(View.GONE);
                        break;
                }
            }
        /*设置资源*/
            switch(type){
                case 1:
                    currentMouth = userRecord.getCreatetime().substring(5,7) ;
                    mouth_day= userRecord.getCreatetime().substring(5,10) ;
                    time = userRecord.getCreatetime_d().substring(11,19);
                    year_mouth_day = userRecord.getCreatetime().split("-");
                    if(currentMouth.startsWith("0"))
                        viewHolder1.tv_mouth.setText(currentMouth.substring(1, 2)+cx.getString(R.string.mouth));
                    else
                        viewHolder1.tv_mouth.setText(currentMouth+cx.getString(R.string.mouth));

                    viewHolder1.tv_day.setText(mouth_day);
                    viewHolder1.tv_week.setText(DateUtils.getWeekDayFromDate(Integer.valueOf(year_mouth_day[0]),Integer.valueOf(year_mouth_day[1]),Integer.valueOf(year_mouth_day[2])));

                    viewHolder1.tv_typecn.setText(String.valueOf(userRecord.getXtb()));
                    viewHolder1.tv_type_info.setText(userRecord.getTypeCN()+userRecord.getXtb());

                    break;
                case 0:
                    mouth_day = userRecord.getCreatetime().substring(5,10) ;
                    time = userRecord.getCreatetime_d().substring(11,19);
                    year_mouth_day = userRecord.getCreatetime().split("-");
                    viewHolder1.tv_day.setText(mouth_day);
                    viewHolder1.tv_week.setText(DateUtils.getWeekDayFromDate(Integer.valueOf(year_mouth_day[0]),Integer.valueOf(year_mouth_day[1]),Integer.valueOf(year_mouth_day[2])));

                    viewHolder1.tv_typecn.setText(String.valueOf(userRecord.getXtb()));
                    viewHolder1.tv_type_info.setText(userRecord.getTypeCN()+userRecord.getXtb());

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
         LinearLayout head ;
     }

    class ViewHolder2 {
        TextView  tv_week , tv_day , tv_typecn , tv_type_info ;
        ImageView header ;
    }
}
