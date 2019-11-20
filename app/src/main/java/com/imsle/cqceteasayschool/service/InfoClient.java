package com.imsle.cqceteasayschool.service;

import android.util.Log;

import com.imsle.cqceteasayschool.model.KBDetail;
import com.imsle.cqceteasayschool.model.KCachievement;
import com.imsle.cqceteasayschool.model.News;
import com.imsle.cqceteasayschool.model.StuCredit;
import com.imsle.cqceteasayschool.model.StuDetail;
import com.imsle.cqceteasayschool.utils.KBUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Seale on 2019/11/12.
 * Author:臧锡洋
 * 功能描述:
 */
public class InfoClient {
    private static InfoClient infoClient = new InfoClient();
    private final String TAG = "InfoClient";
    ArrayList<News> news = new ArrayList<News>();
    OkHttpClient client = new OkHttpClient();
    private Document document;
    private int weekDay = 0;
    private KBDetail kbDetail = new KBDetail();
    private ArrayList<KBDetail> kbDetails = new ArrayList<>();
    private boolean weekDayFlag = false;//判断是否是同一节课

    private InfoClient() {
    }

    private InfoEvent infoEvent;
    private InfoEvent KcInfoEvent;
    private InfoEvent scoreEvent;
    private InfoEvent kbEvent;

    public static InfoClient getInstance() {
        return infoClient;
    }


    public ArrayList<News> getNewsList() throws IOException {
        String baseUrl = "https://www.cqcet.edu.cn/";
        String uriPath = "https://www.cqcet.edu.cn/index/xwdt.htm#";
        Document doc = Jsoup.connect(uriPath).get();

        String title = doc.title();
        Elements articles = doc.select("a.c50592");
        Elements dates = doc.select("span.box_r");

        News news1;

        //爬取新闻标题和链接
        for (Element article : articles) {
            news1 = new News();
            String s = baseUrl + article.select("a").attr("href").replace("../", "");
            news1.setUrl(s);
            news1.setTitle(article.text());
            news.add(news1);
        }

        //爬取新闻日期
        for (int i = 0; i < news.size(); i++) {
            news.get(i).setDate(dates.get(i).text());
        }

        return news;
    }

    public StuDetail getStuInfo(String cookie) {

        StuDetail stuDetail_model = new StuDetail();
        //查询学生信息
        final Request stuDetail = new Request.Builder()
                .url("http://42.247.8.142:8080/jsxsd/framework/xsMain_new.jsp?t1=1")
                .addHeader("Cookie", cookie)
                .build();

        final Call stuDetailCall = client.newCall(stuDetail);

        stuDetailCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                Elements elements = document.getElementsByClass("middletopdwxxcont");


                stuDetail_model.setStuName(elements.get(1).text());
                stuDetail_model.setUsername(elements.get(2).text());
                stuDetail_model.setCollege(elements.get(3).text());
                stuDetail_model.setMajor(elements.get(4).text());
                stuDetail_model.setStuClass(elements.get(5).text());

                Log.d(TAG, "学生信息: " + stuDetail_model);
                infoEvent.OnFinishEvent();
            }
        });
        return stuDetail_model;
    }

    public ArrayList<KCachievement> getStuCachievement(String cookie) {
        //查询表单
        FormBody CjForm = new FormBody.Builder()
                //.add("kksj","2017-2018-2")
                //.add("xsfs","all")
                .build();

        //查询成绩测试
        Request CJrequest = new Request.Builder()
                .url("http://42.247.8.142:8080/jsxsd/kscj/cjcx_list")
                .addHeader("Cookie", cookie)
                .post(CjForm)
                .build();

        final Call CJCall = client.newCall(CJrequest);
        //存储所有课程信息
        ArrayList<KCachievement> kCachievements = new ArrayList<>();
        CJCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //把网页解析为document
                String CJHtml = response.body().string();
                Document document = Jsoup.parse(CJHtml);

                Elements cj = document.getElementsByClass("Nsb_r_list Nsb_table").select("tr>td");
                Elements infoMation = document.select("html>div");

                Log.d(TAG, "info: " + infoMation.text());


                //存储每个课程详细信息
                KCachievement kCachievement = new KCachievement();

                //取出成绩html数据
                int Cjflag = 0;
                for (Element element : cj) {
                    //Log.d(TAG, "element: " + element);
                    switch (Cjflag++) {
                        case 0:
                            kCachievement.setSerialNumber(Integer.parseInt(element.text()));
                            break;
                        case 1:
                            kCachievement.setTerm(element.text());
                            break;
                        case 2:
                            kCachievement.setCourseNum(element.text());
                            break;
                        case 3:
                            kCachievement.setCourseName(element.text());
                            break;
                        case 4:
                            kCachievement.setAchievenment(element.text());
                            break;
                        case 5:
                            kCachievement.setTag(element.text());
                            break;
                        case 6:
                            kCachievement.setCredit(Float.parseFloat(element.text()));
                            break;
                        case 7:
                            kCachievement.setTotalHours(Float.parseFloat(element.text()));
                            break;
                        case 8:
                            kCachievement.setAchievementPoint(Float.parseFloat(element.text()));
                            break;
                        case 9:
                            kCachievement.setSupplementaryTerm(element.text());
                            break;
                        case 10:
                            kCachievement.setAssessmentMethod(element.text());
                            break;
                        case 11:
                            kCachievement.setExaminationNature(element.text());
                            break;
                        case 12:
                            kCachievement.setCurriculumAttributes(element.text());
                            break;
                        case 13:
                            kCachievement.setCourseNature(element.text());
                            kCachievements.add(kCachievement);
                            //Log.d(TAG, "kCachievement: " + kCachievement);
                            kCachievement = new KCachievement();
                            Cjflag = 0;
                    }
                }

                for (KCachievement kCachievement1 : kCachievements) {
                    Log.d(TAG, "kCachievement1: " + kCachievement1);
                }
                if (KcInfoEvent != null){KcInfoEvent.OnFinishEvent();}
            }
        });
        return kCachievements;
    }

    public StuCredit getStuCredit(String Cookie) {
        StuCredit stuCredit = new StuCredit();
        //查询学分
        FormBody StuCent = new FormBody.Builder()
                .add("ndzydm", "20176104")
                .add("jx0301zxjhid", "")
                .build();

        Request StuRequest = new Request.Builder()
                .url("http://42.247.8.142:8080/jsxsd/xxwcqk/xxwcqkOnkclb.do")
                .addHeader("Cookie", Cookie)
                .post(StuCent)
                .build();

        final Call StuCall = client.newCall(StuRequest);

        StuCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                //Log.d(TAG, "StudentTest: " + document);
                Elements elements = document.select("td>b");


                stuCredit.setFinishedCredit(Float.parseFloat(elements.get(1).text()));
                stuCredit.setBeingCredit(Float.parseFloat(elements.get(2).text()));

                Log.d(TAG, "stuCredit: " + stuCredit);
                scoreEvent.OnFinishEvent();
            }
        });
        return stuCredit;
    }


    public ArrayList<KBDetail> getKB(String cookie){
        kbDetails.clear();

        //访问教务系统测试
        Request jwxtTest = new Request.Builder()
                .url("http://42.247.8.142:8080/jsxsd/xskb/xskb_list.do")
                .get()
                .addHeader("Cookie",cookie)
                .build();
        final Call testJwxtCall = client.newCall(jwxtTest);

        testJwxtCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    //获取课表网页
                    String str = response.body().string();
                    document = Jsoup.parseBodyFragment(str);

                    Elements contentDetail = document.getElementsByClass("kbcontent");
                    //Log.d(TAG, "onResponse: ");
                    //Log.d(TAG, "onResponse: " + contentDetail.text());

                    for(int i = 0;i < contentDetail.size();i ++){
                        weekDay = contentDetail.get(i).select("div[class=kbcontent]").attr("id").charAt(33) - 48;
                        //Log.d(TAG, "kcContens: " + contentDetail.get(i).text() + " week" + weekDay);
                        //分割第n节所有课
                        String[] kcDetails = contentDetail.get(i).text().toString().split(" ");
                        int flag = 0;
                        for (String kcdetail : kcDetails){
                            //Log.d(TAG, "kcdetail: " + kcdetail);
                            if(kcdetail.equals("---------------------") || kcdetail.isEmpty()){
                                weekDayFlag = true;
                                //Log.d(TAG, "onResponse: " + "continue");
                                continue;
                            }

                            if(!kcdetail.isEmpty()){
                                switch (flag){
                                    case 0: kbDetail.setCourseName(kcdetail);
                                        flag ++;
                                        break;
                                    case 1: kbDetail.setTeacherName(kcdetail);
                                        flag ++;
                                        break;
                                    case 2:
                                        if(Character.isDigit(kcdetail.charAt(0))){
                                            kbWeekAndSection(kcdetail);
                                            flag ++;
                                        }else{
                                            kbDetail.setCourseName(kcDetails[0] + kcDetails[1]);
                                            kbDetail.setTeacherName(kcDetails[2]);
                                            //Log.d(TAG, "傻逼打的空格: ");
                                            flag = 2;
                                        }

                                        break;
                                    case 3:
                                        kbDetail.setClassRoom(kcdetail);
                                        kbDetail.setWeekDay(weekDay);
                                        flag = 0;
                                        kbDetails.add(kbDetail);
                                        //Log.d(TAG, "kbDetails: " + kbDetail);
                                        kbDetail = new KBDetail();
                                }
                            }
                        }
                    }


                    //对周次进行处理
                    ArrayList<Integer> arrayList = null;
                    for(int i = 0;i < kbDetails.size();i ++){
                        Stack<String> temp= new Stack<>();
                        //使用split分割周次
                        String[] spiTemp = kbDetails.get(i).getWeek().split(",");

                        //将周次存入temp
                        for(int j = 0;j < spiTemp.length;j ++){
                            // Log.d(TAG, "spiTemp: " + spiTemp[j]);
                            //去除中文
                            int t = spiTemp[j].indexOf("(");
                            if( t != -1){
                                spiTemp[j] = spiTemp[j].substring(0,t);
                            }

                            temp.push(spiTemp[j]);
                            arrayList = new ArrayList();
                            //Log.d(TAG, "temp: " + temp);

                        }

                        //将周次存入KBDetails
                        while (!temp.isEmpty()){
                            String week = temp.pop();
                            //Log.d(TAG, "week: " + week);

                            if(week.contains("-")){
                                //Log.d(TAG, "----------: ");
                                String[] temp2 = week.split("-");
                                int startWeek = Integer.parseInt(temp2[0]);
                                int endWeek = Integer.parseInt(temp2[1]);

                                // Log.d(TAG, "start end: " + startWeek + " " +endWeek);

                                for(int start = startWeek;start <= endWeek;start ++){
                                    arrayList.add(start);
                                }
                            }else{
                                arrayList.add(Integer.parseInt(week));
                            }

                        }
                        //Log.d(TAG, "arrlist: " + arrayList);
                        kbDetails.get(i).setWeekDetail(arrayList);
                        getSection(kbDetails);

                    }

                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Log.d(TAG, "onResponse: " + kbDetails.size());
                //打印课表详情
                        /*for(KBDetail kbDetail : kbDetails){
                              Log.d(TAG, "myKb: " + kbDetail);
                         }*/

                //打印本周课表
                /*KBUtil kbUtil = new KBUtil(kbDetails);*/
                //ArrayList<KBDetail> test = kbUtil.getWeekKB(12);

                //Log.d(TAG, "本周课表: " + test);

                /*for(KBDetail tes:test){
                    Log.d(TAG, "课程名字：" + tes.getCourseName());
                    Log.d(TAG, "任课老师：" + tes.getTeacherName());
                    Log.d(TAG, "上课教室：" + tes.getClassRoom());
                }*/
                if (kbEvent != null){kbEvent.OnFinishEvent();}
            }
        });
        return kbDetails;
    }
    public void getSection(ArrayList<KBDetail> kbDetails) {
        ArrayList<Integer> section = new ArrayList<>();

        for (KBDetail kbDetail : kbDetails) {
            if (kbDetail.getSection().contains("11")) {
                section.add(11);
            }
            if (kbDetail.getSection().contains("10")) {
                section.add(10);
            }
            if (kbDetail.getSection().contains("9")) {
                section.add(9);
                Collections.sort(section);
                kbDetail.setSections(section);
                section = new ArrayList<Integer>();
                continue;
            }
            if (kbDetail.getSection().contains("8")) {
                section.add(8);
            }
            if (kbDetail.getSection().contains("7")) {
                section.add(7);
                Collections.sort(section);
                kbDetail.setSections(section);
                section = new ArrayList<Integer>();
                continue;
            }
            if (kbDetail.getSection().contains("6")) {
                section.add(6);
            }
            if (kbDetail.getSection().contains("5")) {
                section.add(5);
                Collections.sort(section);
                kbDetail.setSections(section);
                section = new ArrayList<Integer>();
                continue;
            }
            if (kbDetail.getSection().contains("4")) {
                section.add(4);
            }
            if (kbDetail.getSection().contains("3")) {
                section.add(3);
                Collections.sort(section);
                kbDetail.setSections(section);
                section = new ArrayList<Integer>();
                continue;
            }
            if (kbDetail.getSection().contains("2")) {
                section.add(2);
            }
            String temp = kbDetail.getSection();
            if (kbDetail.getSection().contains("1")) {
                section.add(1);
                Collections.sort(section);
                kbDetail.setSections(section);
                section = new ArrayList<Integer>();
                continue;
            }
        }
    }

    public void kbWeekAndSection(String kcDetails){
        String[] temp = kcDetails.split("\\[");
        //Log.d(TAG, "kbWeekAndSection: " + temp[0]);
        kbDetail.setWeek(temp[0]);
        //Log.d(TAG, "kbWeekAndSection: " + temp[1]);
        kbDetail.setSection("["+temp[1]);
    }



    public void setKbDetailOnFinishEvent(InfoEvent event){
        this.kbEvent = event;
    }

    public void setKCachievementOnFinishEvent(InfoEvent event) {
        this.KcInfoEvent = event;
    }

    public void setStuInfoFinishEvent(InfoEvent event) {
        this.infoEvent = event;
    }

    public void setScoreEvent(InfoEvent event) {
        this.scoreEvent = event;
    }

    public interface InfoEvent {
        void OnFinishEvent();
    }
}
