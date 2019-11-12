package com.imsle.cqceteasayschool.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.headerviewadapter.adapter.HeaderViewAdapter;
import com.donkingliang.headerviewadapter.view.HeaderRecyclerView;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.adapter.HomeRecyclerAdapter;
import com.imsle.cqceteasayschool.model.Banner;
import com.imsle.cqceteasayschool.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述:
 */
public class HomeFragment extends ImmersionFragment {
    private static final String TAG = "HomeFragment";
    private View view ;
    private HeaderRecyclerView home_recyclerView ;
    private BGABanner banner_home;
    private View banner_home_view;
    private Banner bannerModel = new Banner();
    private List<News> newsList = new ArrayList<>();
    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);

        loadData();//加载数据
        try {
            loadBannerData(); // 加载头图数据
        } catch (IOException e) {
            e.printStackTrace();
        }

        initBanner();
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

    private void    initRecyclerView(){
        home_recyclerView = (HeaderRecyclerView)view.findViewById(R.id.home_recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        home_recyclerView.setLayoutManager(linearLayoutManager);
        home_recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        home_recyclerView.addHeaderView(banner_home_view);
        //本身的适配器
        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(newsList);
        adapter.setOnItemClickListener(new HomeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, News news,int i) {
                Toast.makeText(getContext(),"点击了第"+i+"项",Toast.LENGTH_SHORT).show();
            }
        });
        //对适配器进行包装
        //TODO 进行添加轮播图操作
        Log.d(TAG, "initUI: 加载中");
        home_recyclerView.setAdapter(adapter);
    }

    /**
     * 初始化首页banner视图
     */
    public void initBanner(){
        banner_home_view = LayoutInflater.from(getContext()).inflate(R.layout.banner_item,null);
        banner_home = banner_home_view.findViewById(R.id.banner_home);
        banner_home.setAdapter(new BGABanner.Adapter<ImageView,String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                Glide.with(getActivity())
                        .load(bannerModel.getImg().get(position))
                        /*.apply(new RequestOptions().placeholder(R.drawable.holder)
                                .error(R.drawable.holder).dontAnimate().centerCrop())*/
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(itemView);
            }

        });

    }



    /**
     * 解析Banner数据
     */

    public void loadBannerData() throws IOException {
            String url = "https://www.cqcet.edu.cn/";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Document document = null;
                    try {
                        document = Jsoup.connect(url).get();
                        Elements heros = document.select(".heros.clearfix > li img");

                        List<String> img = new ArrayList<>();
                        for (Element element : heros) {
                            img.add(url + element.attr("src"));
                        }
                        bannerModel.setImg(img);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                banner_home.setData(bannerModel.getImg(), null);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }


    /**
     * TODO RecyclerView 获取新闻数据
     */
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
