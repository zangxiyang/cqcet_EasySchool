package com.imsle.cqceteasayschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;


public class MainActivity extends FragmentActivity {

    private SViewPager viewPager;
    private FixedIndicatorView indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImmersionBar.with(this)
                .statusBarColor(R.color.homeToolBarColor)
                .fitsSystemWindows(true)
                .init();

        initActivity();

    }

    /**
     * 函数说明: 初始化界面
     * 创建时间: 2019/11/6 14:30
     * 创建人: 臧锡洋
     */

    public void initActivity (){
        viewPager = findViewById(R.id.tabmain_viewPager);
        indicator = findViewById(R.id.tabmain_indicator);
        indicator.setOnTransitionListener
                (new OnTransitionTextListener().setColor
                        (getResources().getColor(R.color.tabBottomTitleFire),
                                getResources().getColor(R.color.tabBottomTitleNormal)));

    }

}
