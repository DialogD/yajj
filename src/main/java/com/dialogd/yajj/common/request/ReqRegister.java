package com.dialogd.yajj.common.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Date: 2020/4/11
 */
@Data
@ApiModel(description = "注册接收参数")
public class ReqRegister {

    private String registerUsername;

    private String registerPassword;

    private String registerWellPassword;

    private String registerPhone;
}
