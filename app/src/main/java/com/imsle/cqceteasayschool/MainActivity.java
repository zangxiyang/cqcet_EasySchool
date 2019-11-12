package com.imsle.cqceteasayschool;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.imsle.cqceteasayschool.fragment.FuncFragment;
import com.imsle.cqceteasayschool.fragment.HomeFragment;
import com.imsle.cqceteasayschool.fragment.MyFragment;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

//    private ArrayList<Fragment> fragments = new ArrayList<>();
    //底部导航栏
    private FrameLayout fl_content;
    private BottomBarLayout bottomBar;

    HomeFragment homeFragment;
    FuncFragment funcFragment;
    MyFragment myFragment;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initBottomBarClickListener();

    }

    /**
     * 函数名: initActivity
     * 函数说明: 初始化界面
     * 创建时间: 2019/11/8 17:24
     *
     * @return: void 不返回
     */
    public void initView() {


        //初始化fragment
        fl_content = findViewById(R.id.fl_content);
        bottomBar = findViewById(R.id.bottomBar);


        changeFragment(0);

    }

    /***
     * 函数名: initBottomBarClickListener 
     * 函数说明: 设置底部导航栏点击事件
     * 创建时间: 2019/11/8 21:31
     * @param:
     * @return: void
     */
    private void initBottomBarClickListener() {
        bottomBar.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                Log.i("MainActivity", "position: " + currentPosition);

                changeFragment(currentPosition);

                if (currentPosition == 0) {
                    //如果是第一个，即首页
                    //TODO 这是进行刷新第一个页面,对数据进行更新
                    if (previousPosition == currentPosition) {
                        //在首页的基础上再次点击
                        Log.d(TAG, "onItemSelected: 首页的基础上进行了点击");
                    }
                }

            }
        });

    }


    /**
     * 函数名: changeFragment
     * 函数说明: FragmentManager事务
     * 创建时间: 2019/11/8 23:21
     *
     * @param: currentPosition
     * @return: void
     */
    /*private void changeFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, fragments.get(currentPosition));
        transaction.commit();
    }*/
    private void changeFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏所有可用的fragment,防止重叠
        hideFragment(transaction);

        switch (currentPosition){
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_content,homeFragment);
                }else{
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (funcFragment == null) {
                    funcFragment = new FuncFragment();
                    transaction.add(R.id.fl_content,funcFragment);
                }else{
                    transaction.show(funcFragment);
                }
                break;
            case 2:
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    transaction.add(R.id.fl_content,myFragment);
                }else{
                    transaction.show(myFragment);
                }
                break;

        }
        transaction.commit();


    }

    /**
     * 隐藏所有可用的fragment
     *
     * @param fragmentTransaction FragmentTransaction 事务
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null){
            fragmentTransaction.hide(homeFragment);
        }
        if (myFragment != null){
            fragmentTransaction.hide(myFragment);
        }
        if (funcFragment != null){
            fragmentTransaction.hide(funcFragment);
        }
    }


}
