package com.young.leshengbao.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.young.leshengbao.R;
import com.young.leshengbao.options.ShopDetailActivity;
import com.young.leshengbao.model.ShopModel;


import java.util.List;

/**
 * Created by chenhe on 2016/7/19.
 */
public class ShopPagerAdapter extends RecyclerView.Adapter<ShopPagerAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<ShopModel> dataList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;

        public final View mView;
        public final ImageView iv_avatar;
        public final TextView tv_name;
        public final TextView tv_description;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            iv_avatar = (ImageView) view.findViewById(R.id.avatar);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_name.getText();
        }
    }

//    public String getValueAt(int position) {
//        return dataList.get(position);
//    }

    public ShopPagerAdapter(Context context, List<ShopModel> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        dataList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBoundString = dataList.get(position).getName();
        holder.tv_name.setText(dataList.get(position).getName());
        holder.tv_description.setText(dataList.get(position).getDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ShopDetailActivity.class);
                intent.putExtra(ShopDetailActivity.EXTRA_NAME, holder.mBoundString);

                context.startActivity(intent);
            }
        });

        Glide.with(holder.iv_avatar.getContext())
                .load(R.drawable.p10)
                .fitCenter()
                .into(holder.iv_avatar);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
