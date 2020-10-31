package com.dialogd.yajj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1179180054759599232L;
    private Long id;

    private Long orderId;

    private Long productId;

    private String productName;

    private Long productPrice;

    private String productImages;

    private Integer count;

    private Boolean flag;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;


}