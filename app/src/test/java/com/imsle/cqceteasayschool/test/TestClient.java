package com.imsle.cqceteasayschool.test;

import com.imsle.cqceteasayschool.model.News;
import com.imsle.cqceteasayschool.model.UserLogin;
import com.imsle.cqceteasayschool.service.InfoClient;
import com.imsle.cqceteasayschool.utils.ZhxyUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Seale on 2019/11/12.
 * Author:臧锡洋
 * 功能描述:
 */
public class TestClient {
    @Test
    public void toCqcet() throws IOException {
        String url = "https://www.cqcet.edu.cn/";
        Document document = Jsoup.connect(url).get();
        Elements heros = document.select(".heros.clearfix > li img");
        for (Element element : heros) {
            System.out.println(url + element.attr("src"));
        }
    }

    @Test
    public void toNews() throws IOException {
        InfoClient infoClient = InfoClient.getInstance();
        ArrayList<News> t1 = infoClient.getNewsList();
        for (News i : t1) {
            System.out.println(i.getTitle() + "\n\t" + i.getDate() + "\n\t" + i.getUrl());
        }
    }

    @Test
    public void login() {

    }

}

