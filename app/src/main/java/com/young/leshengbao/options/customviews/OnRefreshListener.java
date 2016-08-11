package com.young.leshengbao.options.customviews;

/**
 * Created by Administrator on 2016/8/11.
 */
public interface OnRefreshListener {

    /**
     * 下拉刷新
     */
    void onDownPullRefresh();

    /**
     * 上拉加载更多
     */
    void onLoadingMore();
}
