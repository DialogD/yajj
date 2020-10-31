package com.dialogd.yajj.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2020/4/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespProductDetail {

    private Long id;

    private String name;

    private String price;

    private String salePrice;

    private String image;
}
