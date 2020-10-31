package com.dialogd.yajj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {
    private static final long serialVersionUID = -6905809057860774909L;
    private Long id;

    private Long userId;

    private Long productId;

    private Integer count;

    private String productName;

    private Long price;

    private Date updateTime;

}