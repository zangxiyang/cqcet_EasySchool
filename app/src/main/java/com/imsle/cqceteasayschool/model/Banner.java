package com.imsle.cqceteasayschool.model;

import android.widget.ImageView;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Seale on 2019/11/12.
 * Author:臧锡洋
 * 功能描述: banner图模型
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Accessors(chain = true)
public class Banner {
    private List<String> img;
    private List<String> des;

}
