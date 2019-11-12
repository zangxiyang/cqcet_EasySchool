package com.imsle.cqceteasayschool.test;

import com.imsle.cqceteasayschool.service.InfoClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

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
        for (Element element : heros){
            System.out.println(url+element.attr("src"));
        }
    }

    @Test
    public void toNews() throws IOException {
        InfoClient infoClient = InfoClient.getInstance();
        infoClient.getNewsList();

    }

}