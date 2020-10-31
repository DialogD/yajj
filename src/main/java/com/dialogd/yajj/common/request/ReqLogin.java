package com.dialogd.yajj.common.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Date: 2020/4/11
 */
@Data
@ApiModel(description = "登录接收参数")
public class ReqLogin {

    private String username;

    private String password;
}
