package com.imsle.cqceteasayschool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class KCachievement {
    //序号
    private int serialNumber;
    //学期
    private String term;
    //课程编号
    private String courseNum;
    //课程名字
    private String courseName;
    //课程成绩
    private String achievenment;
    //成绩标识
    private String Tag;
    //学分
    private float credit;
    //总学时
    private float totalHours;
    //绩点
    private float achievementPoint;
    //补重学期
    private String supplementaryTerm;
    //考核方式
    private String assessmentMethod;
    //考试性质
    private String examinationNature;
    //课程属性
    private String curriculumAttributes;
    //课程性质
    private String courseNature;



}
