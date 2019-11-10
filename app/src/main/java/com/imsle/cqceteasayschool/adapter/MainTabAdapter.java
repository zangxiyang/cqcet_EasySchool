package com.imsle.cqceteasayschool.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.shizhefei.view.indicator.IndicatorViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by Seale on 2019/11/6.
 * Author:臧锡洋
 * 功能描述: App中底部导航栏的适配器
 */
public class MainTabAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private String []tabNames ;


    public MainTabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        return null;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return null;
    }
}
