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
public class StuDetail {
    //学生名字
    private String stuName;
    //学生账号
    private String username;
    //所属学院
    private String college;
    //所学专业
    private String major;
    //所属班级
    private String stuClass;
}
