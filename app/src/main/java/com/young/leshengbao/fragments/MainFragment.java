package com.young.leshengbao.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.young.leshengbao.R;
import com.young.leshengbao.adapter.ShopPagerAdapter;
import com.young.leshengbao.model.ShopModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhe on 2016/7/19.
 */
public class MainFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_main, null);
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_details_list, container, false);
        setupRecyclerView(rv);

        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new ShopPagerAdapter(getActivity(),getRandomSublist()));
    }

    private List<ShopModel> getRandomSublist() {
        ArrayList<ShopModel> dataList = new ArrayList<>();
       for (int i = 0; i < 30; i++){
           ShopModel model = new ShopModel();
           model.setName("王鹏伟" + i);
           model.setDescription("是傻逼"+ i);
           dataList.add(model);
       }
        return dataList;
    }
}
