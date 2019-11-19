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
public class StuCredit {
    //完成学分
    private float finishedCredit;
    //正在修读学分
    private float beingCredit;
}
