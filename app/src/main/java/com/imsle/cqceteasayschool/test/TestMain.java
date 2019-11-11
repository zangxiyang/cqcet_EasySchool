package com.imsle.cqceteasayschool.test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述: 测试类
 */
public class TestMain {
    private static OkHttpClient client = new OkHttpClient();
    public static void main(String[] args) {

        //get请求获取cookies

        //get请求
        Request request1 = new Request.Builder().url("http://sso.cqcet.edu.cn/login").build();
        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() >= 200 && response.code() < 300) {
                    System.out.println(response.header("Set-Cookie"));
                    String cookie_Str = response.headers().get("Set-Cookie");
                }
            }
        });



        //post请求
        FormBody formBody = new FormBody.Builder()
                .add("type","1")
                .add("username","2017180543")
                .add("password","1235689az")
                .build();

        Request request = new Request.Builder().url("http://sso.cqcet.edu.cn/login_process").
                addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .addHeader("Accept-Language","zh-CN,zh;q=0.9")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() >= 200 && response.code() < 300) {
                    System.out.println(response.body().string());
                }
            }
        });
    }
}
