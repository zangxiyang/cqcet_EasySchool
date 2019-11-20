package com.imsle.cqceteasayschool.model;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 类名:MySubject
 * 说明: 课程表实体类
 * 创建时间: 2019/11/19 0:15
 * 创建人: 臧锡洋
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString
public class MySubject implements ScheduleEnable {
    public static final String EXTRAS_ID="extras_id";
    public static final String EXTRAS_AD_URL="extras_ad_url";//广告Url

    private int id=0;

    /**
     * 课程名
     */
    private String name;

    //无用数据
    private String time;

    /**
     * 教室
     */
    private String room;

    /**
     * 教师
     */
    private String teacher;

    /**
     * 第几周至第几周上
     */
    private List<Integer> weekList;

    /**
     * 开始上课的节次
     */
    private int start;

    /**
     * 上课节数
     */
    private int step;

    /**
     * 周几上
     */
    private int day;

    private String term;

    /**
     *  一个随机数，用于对应课程的颜色
     */
    private int colorRandom = 0;

    private String url;


    public MySubject(String term,String name, String room, String teacher, List<Integer> weekList, int start, int step, int day, int colorRandom,String time) {
        super();
        this.term=term;
        this.name = name;
        this.room = room;
        this.teacher = teacher;
        this.weekList=weekList;
        this.start = start;
        this.step = step;
        this.day = day;
        this.colorRandom = colorRandom;
        this.time=time;
    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule=new Schedule();
        schedule.setDay(getDay());
        schedule.setName(getName());
        schedule.setRoom(getRoom());
        schedule.setStart(getStart());
        schedule.setStep(getStep());
        schedule.setTeacher(getTeacher());
        schedule.setWeekList(getWeekList());
        schedule.setColorRandom(2);
        schedule.putExtras(EXTRAS_ID,getId());
        schedule.putExtras(EXTRAS_AD_URL,getUrl());
        return schedule;
    }

    public Schedule clearSchedule(){
        Schedule schedule=new Schedule();
        return schedule;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

