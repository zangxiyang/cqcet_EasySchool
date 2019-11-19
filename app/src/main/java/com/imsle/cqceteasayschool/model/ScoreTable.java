package com.imsle.cqceteasayschool.model;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 类名:ScoreTable
 * 说明: 成绩实体类
 * 创建时间: 2019/11/19 9:26
 * 创建人: 臧锡洋
 */

@SmartTable(name = "成绩")
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Accessors(chain = true)
public class ScoreTable {

    @SmartColumn(name = "课程名",id = 0)
    private String name;

    @SmartColumn(name = "考试类型",id = 1)
    private String type;

    @SmartColumn(name = "绩点",id = 2)
    private String achievement;

    @SmartColumn(name = "学分",id = 3)
    private String credit;

    @SmartColumn(name = "课程类型",id = 4)
    private String lesson;

    @SmartColumn(name = "成绩",id = 5)
    private String score;



}
