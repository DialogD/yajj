package com.dialogd.yajj.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2020/4/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespShowCart {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer count;

    private String productName;

    private double price;

}
