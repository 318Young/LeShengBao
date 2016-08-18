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
public class ShopPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //上拉加载更多
    public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    public static final int  LOADING_MORE=1;
    //上拉加载更多状态-默认为0
    private int load_more_status=0;
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        } else {
            View foot_view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_load_more_layout,parent,false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            return new FootViewHolder(foot_view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder)holder).mBoundString = dataList.get(position).getA_name();
            ((ViewHolder)holder).tv_name.setText(dataList.get(position).getA_name());
            ((ViewHolder)holder).tv_description.setText(dataList.get(position).getA_intro());
            holder.itemView.setTag(position);

            ((ViewHolder)holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ShopDetailActivity.class);
                    intent.putExtra(ShopDetailActivity.EXTRA_NAME, ((ViewHolder)holder).mBoundString);

                    context.startActivity(intent);
                }
            });

            Glide.with(((ViewHolder)holder).iv_avatar.getContext())
                    .load(R.drawable.p10)
                    .fitCenter()
                    .into(((ViewHolder)holder).iv_avatar);
        } else if (holder instanceof FootViewHolder){
            FootViewHolder footViewHolder=(FootViewHolder)holder;
            switch (load_more_status){
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    break;
            }
        }

    }
    /**
     * 进行判断是普通Item视图还是FootView视图
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends  RecyclerView.ViewHolder{
        private TextView foot_view_item_tv;
        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv=(TextView)view.findViewById(R.id.foot_view_item_tv);
        }
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     *  //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     * @param status
     */
    public void changeMoreStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }
}
