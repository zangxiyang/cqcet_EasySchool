package com.imsle.cqceteasayschool.service;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Seale on 2019/11/12.
 * Author:臧锡洋
 * 功能描述: 网络请求服务
 */
public class NetClient {
    private String temp;
    private String basicCookie;

    public void test() {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .retryOnConnectionFailure(true)
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
        //get请求获取cookies
        //同步请求

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

    }
}
