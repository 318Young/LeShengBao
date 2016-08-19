package com.young.leshengbao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.young.leshengbao.R;
import com.young.leshengbao.model.MyMsg;
import com.young.leshengbao.model.ShopModel;
import com.young.leshengbao.options.ShopDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhe on 2016/8/17.
 */
public class MainFragmentAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater inflater;
    private List<ShopModel> dataList;
    private MyFilter myFilter;
    private boolean notiyfyByFilter;
    private Context context;
    List<ShopModel> copyUserList;
    public MainFragmentAdapter(Context context, List<ShopModel> dataList){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.dataList = dataList;
        copyUserList = dataList ;
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
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, null);
            holder.rl_content = (RelativeLayout) convertView.findViewById(R.id.rl_content);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.rl_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ShopDetailActivity.class);
                intent.putExtra(ShopDetailActivity.EXTRA_NAME, dataList.get(i).getA_name());
                intent.putExtra(ShopDetailActivity.EXTRA_IMAGE, "http://123.207.137.67" + dataList.get(i).getA_img());
                intent.putExtra(ShopDetailActivity.EXTRA_CODE,  "http://123.207.137.67/Account.aspx?code=" +dataList.get(i).getA_accountid());
                context.startActivity(intent);
            }
        });

        holder.tv_name.setText(dataList.get(i).getA_name());
        holder.tv_description.setText(dataList.get(i).getA_intro());
        Glide.with(holder.iv_avatar.getContext())
                .load("http://123.207.137.67" + dataList.get(i).getA_img())
                .fitCenter()
                .into(holder.iv_avatar);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(this.dataList);
        }
        return myFilter;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
//        if (!notiyfyByFilter) {
//            dataList.clear();
//            copyUserList.addAll(this.dataList);
//        }
    }

    public static class ViewHolder {
        public RelativeLayout rl_content;
        public  ImageView iv_avatar;
        public  TextView tv_name;
        public  TextView tv_description;
    }

    private class MyFilter extends  Filter {
        List<ShopModel> mOriginalList = null;

        public MyFilter(List<ShopModel> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(CharSequence prefix) {
            Filter.FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<ShopModel>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = copyUserList;
                results.count = copyUserList.size();
            } else {
                String prefixString = prefix.toString();
                final int count = mOriginalList.size();
                final ArrayList<ShopModel> newValues = new ArrayList<ShopModel>();
                for (int i = 0; i < count; i++) {
                    final ShopModel contact = mOriginalList.get(i);
                    String name = contact.getA_name();

                    if (name.contains(prefixString)) {
                        newValues.add(contact);
                    } else {
                        final String[] words = name.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with
                        // space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {
                                newValues.add(contact);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected synchronized void publishResults(CharSequence constraint, FilterResults results) {
//            MainFragmentAdapter.this.dataList.clear();
            MainFragmentAdapter.this.dataList = (List<ShopModel>) results.values;
            MainFragmentAdapter.this.notifyDataSetChanged();
//            if (results.count > 0) {
//                notiyfyByFilter = true;
//                notifyDataSetChanged();
//                notiyfyByFilter = false;
//            } else {
//                notifyDataSetInvalidated();
//            }
        }
    }
}
