package com.dialogd.yajj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    private static final long serialVersionUID = 2488352117152594486L;
    private Integer id;

    private Long userId;

    private String receiver;

    private String phone;

    private Integer provinceId;

    private Integer cityId;

    private Integer areaId;

    private String street;

    private String zipCode;

    private Boolean isDefault;

    private String tag;

    private Boolean flag;

    private Date createTime;

    private Date updateTime;

}