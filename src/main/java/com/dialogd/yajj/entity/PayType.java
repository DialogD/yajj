package com.dialogd.yajj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayType implements Serializable {
    private static final long serialVersionUID = 7054771713993258633L;
    private Integer id;
    /**
     * 支付类型描述
     */
    private String payDesc;

    /**
     * 支付logo图片
     */
    private String logo;

    private Boolean flag;

}