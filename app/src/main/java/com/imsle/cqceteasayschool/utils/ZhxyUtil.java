package com.imsle.cqceteasayschool.utils;

import android.util.Log;


import com.google.gson.Gson;
import com.imsle.cqceteasayschool.model.UserDetail;
import com.imsle.cqceteasayschool.model.UserLogin;
import com.imsle.cqceteasayschool.model.CookieMSG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ZhxyUtil {
    private String basicCookie;
    private String cookie;
    private String TAG = "MainActivity";
    private String username;
    private String password;

    private CookieMSG msg = new CookieMSG();

    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {

                //自定义
                private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<HttpUrl, List<Cookie>>();

                //复写
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url,cookies);
                }

                //复写
                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url);
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            }).build();

    private final OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .retryOnConnectionFailure(true)
            .followRedirects(false)
            .followSslRedirects(false)
            .build();
    //get请求获取cookies
    //同步请求

    public ZhxyUtil(UserLogin userLogin){
        this.username = userLogin.getUsername();
        this.password = userLogin.getPassword();
    }

    public CookieMSG getZhxyCookie(){

        //第一次:http://sso.cqcet.edu.cn/login 获取cookie(get)
        Request request1 = new Request.Builder()
                .get()
                .url("http://sso.cqcet.edu.cn/login")
                .build();
        final Call call1 = client.newCall(request1);
        try {
            Response response = call1.execute();

            CookieUtils(response,request1,"Request1",false,null);
            Thread.sleep(300);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (cookie != null) {
            //post请求
            FormBody formBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();

            //第二次：http://sso.cqcet.edu.cn/login_process 获取cookie(post)
            Request request2 = new Request.Builder()
                    .url("http://sso.cqcet.edu.cn/login_process")
                    .addHeader("Cookie", cookie)
                    .post(formBody)
                    .build();
            final Call call2 = client.newCall(request2);
            try {
                Response response = call2.execute();
                Headers loginHeaders = response.headers();
                HttpUrl loginUrl = request2.url();

                String testCookie = Cookie.parseAll(loginUrl,loginHeaders).toString();

                Log.d(TAG, "getZhxyCookie: " + testCookie.isEmpty() + " " +testCookie + " " + testCookie.length());
                if(testCookie.isEmpty() || testCookie.length() == 2){
                    msg.setCookie(null);
                    msg.setMsg("密码或账号错误");
                    msg.setBasicCookie(null);
                    return msg;
                }
                CookieUtils(response,request2,"Request2",true,null);
                Thread.sleep(300);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //第三次：http://ossc.cqcet.edu.cn/ 获取cookie(get)
        Request request3 = new Request.Builder()
                .url("http://ossc.cqcet.edu.cn/")
                .build();
        final Call call3 = client.newCall(request3);
        try {
            Response response = call3.execute();

            CookieUtils(response,request3,"Request3",false,null);
            Thread.sleep(300);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //第四次：http://ossc.cqcet.edu.cn/login 获取cookie(get)
        Request request4 = new Request.Builder()
                .url("http://ossc.cqcet.edu.cn/login")
                .addHeader("Cookie", cookie)
                .build();
        final Call call4 = client.newCall(request4);
        String location = "";
        try {
            Response response = call4.execute();

            //获取跳转地址
            location = response.header("Location");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (location != null) {
            Request request5 = new Request.Builder()
                    .get()
                    .url(location)
                    .addHeader("Cookie", basicCookie)
                    .build();
            final Call call5 = client.newCall(request5);
            try {
                Response response = call5.execute();

                location = response.header("Location");
                System.out.println("跳转地址:"+location + "\n" + "------------------------------------");

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (location != null) {
                Request request6 = new Request.Builder()
                        .get()
                        .url(location)
                        .addHeader("Cookie", cookie)
                        .build();
                final Call call6 = client.newCall(request6);
                try {
                    Response response = call6.execute();

                    System.out.println("跳转地址:" + response.header("Location") + "\n" + "------------------------------------");

                    CookieUtils(response,request6,"Request6",false,null);
                    location = response.header("Location");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (location != null){
                    Request request7 = new Request.Builder()
                            .get()
                            .url(location)
                            .addHeader("Cookie", cookie)
                            .build();
                    final Call call7 = client.newCall(request7);
                    try {
                        Response response = call7.execute();

                        CookieUtils(response,request7,"Request7",false,cookie);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        msg.setCookie(cookie);
        msg.setBasicCookie(basicCookie);
        msg.setMsg("成功");

        return msg;
    }

    public void CookieUtils(Response response, Request request, String title, boolean flag, String addCookie){
        Headers loginHeaders = response.headers();
        HttpUrl loginUrl = request.url();
        List<Cookie> cookies = Cookie.parseAll(loginUrl,loginHeaders);

        cookie = cookies.toString().substring(1,cookies.toString().indexOf(";"));
        Log.d(TAG, title + ": " + cookie);
        if(flag){
            basicCookie = cookie;
            Log.d(TAG, "CookieUtils: " + title +": " + basicCookie);
        }
        if(addCookie != null){
            cookie = cookie + ";" + addCookie;
            Log.d(TAG, "CookieUtils: " + cookie);
        }
    }

    public UserDetail getUserDetail(){
        UserDetail userDetail = new UserDetail();

        Request testRequest = new Request.Builder()
                .get()
                .url("http://ossc.cqcet.edu.cn/info/userinfo")
                .addHeader("Cookie", cookie)
                .build();
        final Call test = client.newCall(testRequest);
        try {
            Response response = test.execute();
            //System.out.println("--------------------------------------------------------");
            String user = response.body().string();
            //System.out.println("测试接口:" + user);
            Gson gson = new Gson();
            userDetail = gson.fromJson(user,UserDetail.class);
            //Log.d(TAG, "getUserDetail: " + userDetail.getMsg());
            //System.out.println("-----------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userDetail;
    }


}
