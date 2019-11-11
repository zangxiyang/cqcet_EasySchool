package com.imsle.cqceteasayschool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述: 首页新闻数据模型
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private String title ;
    private String subTitle ;
    private String date ;
    private String url ;
}
