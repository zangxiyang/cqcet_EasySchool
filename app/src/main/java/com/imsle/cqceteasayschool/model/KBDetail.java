package com.imsle.cqceteasayschool.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class KBDetail {
    //课程名
    private String courseName;
    //教师名
    private String teacherName;
    //周次
    private String week;
    //节数
    private String section;
    //教室
    private String classRoom;
    //星期
    private int weekDay;
    //周次详情
    private ArrayList<Integer> weekDetail;
    //星期详情
    private ArrayList<Integer> sections;
}
