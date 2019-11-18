package com.imsle.cqceteasayschool.utils;

import android.util.Log;

import com.imsle.cqceteasayschool.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class NewsUtil {
    //Activity 页面
    private String TAG;
    //拼接地址
    final String baseUrl = "https://www.cqcet.edu.cn/";
    final String nextPageBaseUrl = "https://www.cqcet.edu.cn/index/";

    final ArrayList<News> news = new ArrayList<News>();
    //访问链接
    private String uriPath;
    private News news1;
    //下一页链接
    private String nextPageUrl;
    //请求页面内容
    private Document doc;

    //构造方法，使用时需要传入新闻地址
    public NewsUtil(String newUrlPath,String TAG){
        this.TAG = TAG;
        this.uriPath = newUrlPath;
    }

    //获取当前页面的新闻的标题和链接
    public ArrayList<News> getNewsDetail(){
        try{
            doc = Jsoup.connect(uriPath).get();

            String title = doc.title();
            Elements articles = doc.select("a.c50592");
            Elements dates = doc.select("span.box_r");
            //Log.d(TAG, "run: " + articles.size());
            //Log.d(TAG, "onCreate: " + title);

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
                //Log.d(TAG, "日期: " + dates.get(i).text());
            }

            //打印抓取数据集
            for(int i = 0;i < news.size();i ++){
                Log.d(TAG, "爬取信息: " + "标题：" + news.get(i).getTitle() + "\n" + " 链接：" + news.get(i).getUrl() + "\n" + " 日期：" + news.get(i).getDate());
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        return news;
    }

    //获取新闻下一页链接
    public String getNextPageUrl(){
        Elements nextPageHtml = doc.select("a[class=Next]");

        //请求下一页地址
        nextPageUrl = nextPageBaseUrl+nextPageHtml.attr("href").toString();
        Log.d(TAG, "nextPage: " + nextPageBaseUrl+nextPageHtml.attr("href").toString());
        return nextPageUrl;
    }


    //获取新闻详细内容
    public String getNewsContent(String newsUrl){
        StringBuffer newsContent = new StringBuffer();
        //爬取具体新闻内容
        try{
            //获取爬取地址
            //String baseUrl_NewsTarget = news.get(i).getNewsLink();
            //测试爬取新闻地址
            //测试地址 "https://www.cqcet.edu.cn/info/1034/11666.htm"
            String testUrl = newsUrl;
            Document newsHtml = Jsoup.connect(testUrl).get();

            //Elements clickHtml = newsHtml.select("div[id=article_extinfo]>span");
            //String click = clickHtml.attr("span");

                       /* Elements content = newsHtml.select("div[id=article_body]");

                        Elements img = newsHtml.select("div>p>img");

                        for(Element element: img){
                            String imgUrl = baseUrl + element
                                    .attr("src")
                                    .replace("../../ "," ");
                            Log.d(TAG, "图片: " + imgUrl);
                        }

                        Log.d(TAG, "新闻内容: " + " " + content.text());
*/
            Elements article = newsHtml.getElementById("article_body").getAllElements();
            //Log.d(TAG, "article: " + article.text());

            Log.d(TAG, "run: " + article.size());
            for(int i = 1;i < article.size();i ++){
                String img =  baseUrl + article.get(i).select("img")
                        .attr("src")
                        .replace("../../","");

                // Log.d(TAG, "article: " + article.get(i).text() + img);
                if(article.get(i).text().isEmpty() && i % 2 == 1){
                    newsContent.append(img);
                    //Log.d(TAG, "image: " + img );
                }else if(!article.get(i).text().isEmpty()){
                    newsContent.append(article.get(i).text());
                    //Log.d(TAG, "content: " + article.get(i).text());
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        return newsContent.toString();
    }

}
