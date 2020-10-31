package com.dialogd.yajj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsProviders implements Serializable {
    private static final long serialVersionUID = -1664871659946121242L;
    private Integer id;

    /**
     * 物流名称
     */
    private String name;

    /**
     * 图片logo
     */
    private String logo;

    /**
     * 座机
     */
    private String landline;

    /**
     * 电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    private Boolean flag;
}