package com.dialogd.yajj.common.response;

import com.alibaba.fastjson.JSON;
import com.dialogd.yajj.common.emuns.ApiResponseEnum;
import com.dialogd.yajj.common.emuns.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Date: 2020/3/15
 */
@Data
@ApiModel(description = "返回参数")
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 8710025851080570135L;
    @ApiModelProperty(value = "返回信息",required = true)
    private String msg = "";
    @ApiModelProperty(value = "返回码,200:请求成功,其他都表示失败",required = true)
    private int code;
    @ApiModelProperty(value = "返回值")
    private T data;

    public ApiResponse() {
        this.setApiResponseEnum(ApiResponseEnum.SUCCESS);
    }

    private ApiResponse(T t) {
        this();
        this.data = t;
    }

    private ApiResponse(ApiResponseEnum ret) {
        this.setApiResponseEnum(ret);
    }

    private ApiResponse(BaseEnum ret, T t) {
        this.setApiResponseEnum(ret);
        this.data = t;
    }


    public static <T> ApiResponse<T> returnSuccess() {
        return new ApiResponse();
    }

    public static <T> ApiResponse<T> returnSuccess(T data) {
        return new ApiResponse(ApiResponseEnum.SUCCESS, data);
    }

    public static <T> ApiResponse<T> returnFail(BaseEnum ret) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.msg = ret.getMsg();
        apiResponse.code = ret.getCode();
        return apiResponse;
    }

    public static <T> ApiResponse<T> returnFail(String msg) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.msg = msg;
        apiResponse.code = ApiResponseEnum.FAIL.getCode();
        return apiResponse;
    }

    public static <T> ApiResponse<T> returnSuccess(String msg) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.msg = msg;
        return apiResponse;
    }

    public static <T> ApiResponse<T> returnFail(BaseEnum baseEnum,String msg) {
        ApiResponse apiResponse = new ApiResponse();
        if(StringUtils.isNotBlank(msg)){
            apiResponse.msg = msg;
        } else {
            apiResponse.msg = baseEnum.getMsg();
        }
        apiResponse.code = baseEnum.getCode();
        return apiResponse;
    }

    public void setApiResponseEnum(BaseEnum apiResponseEnum) {
        this.code = apiResponseEnum.getCode();
        this.msg = apiResponseEnum.getMsg();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
