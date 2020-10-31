package com.dialogd.yajj.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2020/4/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespAddress {

    private Integer id;

    private Long userId;

    private String receiver;

    private String phone;

    private String address;

    private Boolean isDefault;

    private String tag;
}
