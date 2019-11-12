package com.imsle.cqceteasayschool.service;

import android.util.Log;

import com.imsle.cqceteasayschool.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Seale on 2019/11/12.
 * Author:臧锡洋
 * 功能描述:
 */
public class InfoClient {
    private static InfoClient infoClient = new InfoClient();
    private final String TAG = "InfoClient";
    ArrayList<News> news = new ArrayList<News>();
    private InfoClient(){}


    public static InfoClient getInstance(){return infoClient;}



    public void getNewsList() throws IOException {
        String baseUrl = "https://www.cqcet.edu.cn/";
        String uriPath = "https://www.cqcet.edu.cn/index/xwdt.htm#";
        Document doc = Jsoup.connect(uriPath).get();

        String title = doc.title();
        Elements articles = doc.select("a.c50592");
        Elements dates = doc.select("span.box_r");
        System.out.println("run: " + articles.size()+"onCreate: " + title);
        /*Log.d(TAG, "run: " + articles.size());
        Log.d(TAG, "onCreate: " + title);*/

        News news1;

        //爬取新闻标题和链接
        for(Element article : articles){
            news1 = new News();
            String s = baseUrl + article.select("a").attr("href").replace("../","");
            //Log.d(TAG, "标题与链接: " + article.text() + " " + s);
            news1.setUrl(s);
            news1.setTitle(article.text());
            news.add(news1);
        }

        //爬取新闻日期
        for(int i = 0;i < news.size();i ++){
            news.get(i).setDate(dates.get(i).text());
            System.out.println("日期: " + dates.get(i).text());
            //Log.d(TAG, "日期: " + dates.get(i).text());
        }

        //打印抓取数据集
        for(int i = 0;i < news.size();i ++){
            System.out.println( "爬取信息: " + "标题：" + news.get(i).getTitle() + "\n" + " 链接：" + news.get(i).getUrl() + "\n" + " 日期：" + news.get(i).getDate());
        }
    }
}
