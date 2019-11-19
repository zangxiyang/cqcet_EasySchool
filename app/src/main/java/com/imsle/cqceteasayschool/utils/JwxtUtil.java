package com.imsle.cqceteasayschool.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.imsle.cqceteasayschool.model.KBDetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JwxtUtil {

    //教务系统cookie
    private String jwxtCookie;
    private String jwxtBasicCookie;

    private String TAG = "MainActivity";

    //Jsoup爬取网页
    private Document document;
    private KBDetail kbDetail = new KBDetail();
    ArrayList<KBDetail> kbDetails = new ArrayList<>();
    //判断是否是同一节课
    private boolean weekDayFlag = false;
    //星期
    private int weekDay = 0;
    private String location;

    private String username;
    private String password;

    //智慧校园cookie
    private String cookie;
    private String basicCookie;

    final OkHttpClient httpClient = new OkHttpClient.Builder()
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

    final OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .retryOnConnectionFailure(true)
            .followRedirects(false)
            .followSslRedirects(false)
            .build();
    //get请求获取cookies
    //同步请求

    public JwxtUtil(String cookie,String basicCookie){
        this.cookie = cookie;
        this.basicCookie = basicCookie;
    }



    public String getJwxtCookie(){

        //测试教务系统
        Request jwxt1 = new Request.Builder()
                .get()
                .url("http://42.247.8.142:8080/Logon.do?method=logonByCqdzzy&url=index")
                .addHeader("Cookie",cookie)
                .build();
        final Call jwxtCall1 = client.newCall(jwxt1);
        try {
            Response response = jwxtCall1.execute();
            location = response.header("Location");
            Log.d(TAG, "jwxt1: " + location);
            JwxtCookieUtils(response,jwxt1,"jwxt1:",true,"SERVERID=125");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Request jwxt2 = new Request.Builder()
                .get()
                .url(location)
                .addHeader("Cookie",basicCookie)
                .build();
        final Call jwxtCall2 = client.newCall(jwxt2);
        try {
            Response response = jwxtCall2.execute();
            location = response.header("Location");
            Log.d(TAG, "jwxt2: " + location + basicCookie);
            //JwxtCookieUtils(response,jwxt1,"jwxt2:",false,null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Request jwxt3 = new Request.Builder()
                .get()
                .url(location)
                .addHeader("Cookie",jwxtCookie)
                .build();
        final  Call jwxtCall3 = client.newCall(jwxt3);
        try {
            Response response = jwxtCall3.execute();
            location = response.header("Location");
            Log.d(TAG, "jwxt3: " + location);
        }catch (Exception e){
            e.printStackTrace();
        }

        Request jwxt4 = new Request.Builder()
                .get()
                .url(location)
                .addHeader("Cookie",jwxtCookie)
                .build();
        final  Call jwxtCall4 = client.newCall(jwxt4);
        try {
            Response response = jwxtCall4.execute();
            location = response.header("Location");
            Log.d(TAG, "jwxt4: " + location);
            JwxtCookieUtils(response,jwxt4,"jwxt4",false,jwxtCookie);
        }catch (Exception e){
            e.printStackTrace();
        }

        return jwxtCookie;
    }


    public void JwxtCookieUtils(Response response,Request request,String title,boolean flag,String addCookie){
        Headers loginHeaders = response.headers();
        HttpUrl loginUrl = request.url();
        List<Cookie> cookies = Cookie.parseAll(loginUrl,loginHeaders);
        jwxtCookie = cookies.toString().substring(1,cookies.toString().indexOf(";"));
        Log.d(TAG, title + jwxtCookie);

        if(flag){
            jwxtBasicCookie = cookie;
            Log.d(TAG, "jwxtCookieUtils: " + title +": " + jwxtBasicCookie);
        }
        if(addCookie != null){
            jwxtCookie = jwxtCookie + ";" + addCookie;
            Log.d(TAG, "jwxtCookieUtils: " + jwxtCookie);
        }
    }

    public String getJwxtBasicCookie(){
        return basicCookie;
    }



}
