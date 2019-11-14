package com.imsle.cqceteasayschool;

import android.app.Application;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

/**
 * 类名:App
 * 说明: Application
 * 创建时间: 2019/11/13 11:03
 * 创建人: 臧锡洋
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        QMUISwipeBackActivityManager.init(this);

    }
}
