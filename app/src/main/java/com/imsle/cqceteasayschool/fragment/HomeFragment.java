package com.imsle.cqceteasayschool.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.adapter.HeaderViewAdapter;
import com.imsle.cqceteasayschool.adapter.HomeRecyclerAdapter;
import com.imsle.cqceteasayschool.model.News;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述:
 */
public class HomeFragment extends ImmersionFragment {
    private static final String TAG = "HomeFragment";
    private View view ;
    private RecyclerView home_recyclerView ;
    private List<News> newsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);
        loadData();//加载数据
        initRecyclerView();
        return view;
    }

    @Override
    public void initImmersionBar() {

        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.homeToolBarColor)
                .autoStatusBarDarkModeEnable(true)
                .init();
    }

    private void initRecyclerView(){
        home_recyclerView = view.findViewById(R.id.home_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        home_recyclerView.setLayoutManager(linearLayoutManager);
        home_recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        //本身的适配器
        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(newsList);
        adapter.setOnItemClickListener(new HomeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, News news,int i) {
                Toast.makeText(getContext(),"点击了第"+i+"项",Toast.LENGTH_SHORT).show();
            }
        });
        //对适配器进行包装
        HeaderViewAdapter homeRecyclerAdapter = new HeaderViewAdapter(adapter);
        //TODO 进行添加轮播图操作
        Log.d(TAG, "initUI: 加载中");
        home_recyclerView.setAdapter(homeRecyclerAdapter);
    }
    private void loadData (){
        for (int i = 0 ; i < 30 ; i ++){
            News news = new News();
            news.setTitle("测试"+i);
            news.setSubTitle("测试"+i+"条描述");
            news.setDate("2019年11月"+(i+1)+"日");
            newsList.add(news);
        }
    }

}
