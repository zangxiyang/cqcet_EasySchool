package com.imsle.cqceteasayschool.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.imsle.cqceteasayschool.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述:
 */
public class HomeFragment extends ImmersionFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,null);
    }

    @Override
    public void initImmersionBar() {

        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.homeToolBarColor)
                .autoStatusBarDarkModeEnable(true)
                .init();
    }
}
