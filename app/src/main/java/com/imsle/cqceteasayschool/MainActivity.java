package com.imsle.cqceteasayschool;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.imsle.cqceteasayschool.fragment.FuncFragment;
import com.imsle.cqceteasayschool.fragment.HomeFragment;
import com.imsle.cqceteasayschool.fragment.MyFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<Fragment> fragments = new ArrayList<>();
    //底部导航栏
    private FrameLayout fl_content;
    private BottomBarLayout bottomBar;

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

        //首页Fragment
        HomeFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);

        //百宝箱fragment
        FuncFragment funcFragment = new FuncFragment();
        fragments.add(funcFragment);

        //我的fragment
        MyFragment myFragment = new MyFragment();
        fragments.add(myFragment);

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
                test();
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
    private void changeFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, fragments.get(currentPosition));
        transaction.commit();
    }

    String temp;
    String basicCookie;

    public void test() {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .retryOnConnectionFailure(true)
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
        //get请求获取cookies
        //get请求

        /*client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() >= 200 && response.code() < 300) {
                    if (response.header("Set-Cookie") != null && response.header("Set-Cookie").length() > 0){
                        String cookie = response.headers().get("Set-Cookie")
                                .substring(0,response.header("Set-Cookie").indexOf(";"));
                        temp = cookie;
                        System.out.println("Set-Cookie:"+temp);
                    }


                }
            }
        });*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request1 = new Request.Builder()
                        .get()
                        .url("http://sso.cqcet.edu.cn/login")
                        .build();
                final Call call = client.newCall(request1);
                try {
                    Response response = call.execute();
                    System.out.println("第一次get:" + response.header("Set-Cookie"));
                    temp = response.header("Set-Cookie").substring(0, response.header("Set-Cookie").indexOf(";"));
                    Thread.sleep(300);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (temp != null) {
                    //post请求
                    FormBody formBody = new FormBody.Builder()
                            .add("type", "1")
                            .add("username", "2017180543")
                            .add("password", "1235689az")
                            .build();

                    Request request = new Request.Builder()
                            .url("http://sso.cqcet.edu.cn/login_process")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36")
                            .addHeader("Host", "sso.cqcet.edu.cn")
                            .addHeader("Proxy-Connection", "keep-alive")
                            .addHeader("Cookie", temp)
                            .addHeader("Referer", "http://sso.cqcet.edu.cn/login")
                            .addHeader("Origin", "http://sso.cqcet.edu.cn")
                            .post(formBody)
                            .build();
                    final Call call1 = client.newCall(request);
                    try {
                        Response response = call1.execute();
                        System.out.println("Post:" + response.header("Set-Cookie"));
                        System.out.println("post:" + response.headers());
                        temp = response.header("Set-Cookie").substring(0, response.header("Set-Cookie").indexOf(";"));
                        basicCookie = temp;
                        System.out.println("-------------------------------------");
                        System.out.println("当前temp:" + temp);
                        Thread.sleep(300);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Request request2 = new Request.Builder()
                        .url("http://ossc.cqcet.edu.cn/")
                        .addHeader("Referer", "http://sso.cqcet.edu.cn/login")
                        .addHeader("Proxy-Connection", "keep-alive")
                        .addHeader("Host", "ossc.cqcet.edu.cn")
                        .addHeader("Upgrade-Insecure-Requests", "1")
                        .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36")
                        .build();
                final Call call3 = client.newCall(request2);
                try {
                    Response response = call3.execute();
                    System.out.println("-------------------------------------");
                    System.out.println("第二次get:" + response.headers());
                    System.out.println("-------------------------------------");
                    System.out.println("第二次get:" + response.headers().get("Set-Cookie"));
                    temp = response.header("Set-Cookie").substring(0, response.header("Set-Cookie").indexOf(";"));
                    System.out.println("-------------------------------------");
                    System.out.println("当前temp:" + temp);
                    Thread.sleep(300);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Request request3 = new Request.Builder()
                        .get()
                        .url("http://ossc.cqcet.edu.cn/login")
                        .addHeader("Cookie", temp)
                        .build();
                final Call call4 = client.newCall(request3);
                String location = "";
                try {
                    Response response = call4.execute();
                    System.out.println("第三次get:" + response.headers() + "\n" + "------------------------------------");
                    location = response.header("Location");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (location != null) {
                    Request request4 = new Request.Builder()
                            .get()
                            .url(location)
                            .addHeader("Cookie", basicCookie)
                            .build();
                    final Call call5 = client.newCall(request4);
                    try {
                        Response response = call5.execute();
                        System.out.println("第四次get:" + response.headers());
                        System.out.println("--------------------------------------------------------");
                        System.out.println("跳转地址:"+response.header("Location") + "\n" + "------------------------------------");
                        location = response.header("Location");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (location != null) {
                        Request request5 = new Request.Builder()
                                .get()
                                .url(location)
                                .addHeader("Cookie", temp)
                                .build();
                        final Call call6 = client.newCall(request5);
                        try {
                            Response response = call6.execute();
                            System.out.println("第四次get:" + response.headers());
                            System.out.println("--------------------------------------------------------");
                            System.out.println("跳转地址:" + response.header("Location") + "\n" + "------------------------------------");
                            temp = response.header("Set-Cookie").substring(0, response.header("Set-Cookie").indexOf(";"));
                            location = response.header("Location");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (location != null){
                            Request request6 = new Request.Builder()
                                    .get()
                                    .url(location)
                                    .addHeader("Cookie", temp)
                                    .build();
                            final Call call7 = client.newCall(request6);
                            try {
                                Response response = call7.execute();
                                System.out.println("第五次get:" + response.headers());
                                System.out.println("--------------------------------------------------------");
                                temp = temp+"; "+response.header("Set-Cookie").substring(0, response.header("Set-Cookie").indexOf(";"));
                                System.out.println("-----------------------------");
                                System.out.println(temp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                Request testRequest = new Request.Builder()
                        .get()
                        .url("http://ossc.cqcet.edu.cn/info/userinfo")
                        .addHeader("Cookie", temp)
                        .build();
                final Call test = client.newCall(testRequest);
                try {
                    Response response = test.execute();
                    System.out.println("--------------------------------------------------------");
                    System.out.println("测试接口:" + response.body().string());
                    System.out.println("-----------------------------");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();



            /*client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if(response.code() >= 200 && response.code() < 300) {
                        System.out.println(response.body().string());
                        System.out.println(response.header("Set-Cookie"));
                    }
                }
            });*/


            /*client.newCall(request2).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() >= 200 && response.code() < 300) {
                        System.out.println(response.headers().toString());
                    }
                }
            });*/


    }

}
