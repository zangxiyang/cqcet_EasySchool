package com.imsle.cqceteasayschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.activity.WebViewActivity;
import com.imsle.cqceteasayschool.adapter.HomeRecyclerAdapter;
import com.imsle.cqceteasayschool.model.Banner;
import com.imsle.cqceteasayschool.model.News;
import com.imsle.cqceteasayschool.utils.NewsUtil;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述:
 */
public class HomeFragment extends ImmersionFragment {
    private static final String TAG = "HomeFragment";
    private View view;
    public RecyclerView home_recyclerView;
    private BGABanner banner_home;
    private View banner_home_view;
    private Banner bannerModel = new Banner();
    private List<News> newsList = new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(R.layout.recycler_item, newsList);
    int currentPage = 0;

    QMUITipDialog qmuiTipDialog;

    Handler handler = new Handler();

    private String nextUrl;
    private NewsUtil newsUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        initSmartRefresh();
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

    /***
     * 函数名: initImmersionBar
     * 函数说明: 初始化沉浸式
     * 创建时间: 2019/11/13 2:56
     * @param:
     * @return: void
     */
    @Override
    public void initImmersionBar() {

        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.homeToolBarColor)
                .autoStatusBarDarkModeEnable(true)
                .init();
    }

    private void initRecyclerView() {
        home_recyclerView = view.findViewById(R.id.home_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        home_recyclerView.setLayoutManager(linearLayoutManager);
        home_recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        //本身的适配器
        adapter.setOnItemClickListener(new HomeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, News news, int i) {
                String url = news.getUrl();
                Bundle bundle = new Bundle();
                bundle.putString("url",url);
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_still);
            }
        });

        //TODO 进行添加轮播图操作
        adapter.addHeaderView(banner_home_view);

        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        home_recyclerView.setAdapter(adapter);

    }

    /**
     * 初始化首页banner视图
     */
    public void initBanner() {
        banner_home_view = LayoutInflater.from(getContext()).inflate(R.layout.banner_item, null);
        banner_home = banner_home_view.findViewById(R.id.banner_home);
        banner_home.setAdapter(new BGABanner.Adapter<ImageView, String>() {
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
    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<News> newData;
                newsUtil = new NewsUtil("https://www.cqcet.edu.cn/index/xwdt.htm", "HomeFragment");
                newData = newsUtil.getNewsDetail();
                newsList.clear();
                newsList.addAll(newData);
                nextUrl = newsUtil.getNextPageUrl();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            }

        }).start();
    }


    /***
     * 函数名: initSmartRefresh
     * 函数说明: 初始化上拉刷新下拉加载
     * 创建时间: 2019/11/15 21:16
     * @param:
     * @return: void
     */
    private void initSmartRefresh() {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        /*        refreshLayout.setPrimaryColorsId(R.color.RefreshThemeForPrimaryColor, android.R.color.white);*/

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<News> newData;
                        newsUtil = new NewsUtil("https://www.cqcet.edu.cn/index/xwdt.htm", "HomeFragment");
                        newData = newsUtil.getNewsDetail();
                        newsList.clear();
                        newsList.addAll(newData);
                        nextUrl = newsUtil.getNextPageUrl();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        },1500);
                        refreshLayout.finishRefresh(1500);
                    }
                }).start();

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<News> next_news = new ArrayList<>();
                        NewsUtil newsUtil = new NewsUtil(nextUrl,"homeFragment");
                        next_news = newsUtil.getNewsDetail();
                        newsList.addAll(next_news);
                       handler.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               adapter.notifyDataSetChanged();
                           }
                       },1500);
                        refreshLayout.finishLoadMore(1500);
                    }
                }).start();
            }
        });
    }


}
