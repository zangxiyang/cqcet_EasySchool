package com.imsle.cqceteasayschool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 类名:CookieMSG
 * 说明:
 * 创建时间: 2019/11/20 0:49
 * 创建人: 臧锡洋
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CookieMSG {
    private String cookie;
    private String msg ;
    private String basicCookie;

}
