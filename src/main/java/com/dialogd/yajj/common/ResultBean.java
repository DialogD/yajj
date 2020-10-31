package com.dialogd.yajj.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Date: 2020/3/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultBean<T> implements Serializable {

    /** 成功状态码 */
    public final static int RESULT_CODE_SUCCESS = 200;
    /** 业务异常状态码 */
    public final static int RESULT_CODE_SERVICE_ERR = 98;
    /** 系统异常状态码 */
    public final static int RESULT_CODE_SYSTEM_ERR = 99;
    /** 成功但查无数据 */
    public final static String FAIL_CODE_SUCCESS_NODATA = "1108";

    private static final long serialVersionUID = -9206309315097197290L;

    @ApiModelProperty(value = "返回码,成功状态码-200")
    private Integer code;

    @ApiModelProperty(value = "返回信息")
    private String message;


    @ApiModelProperty(value = "返回数据对象")
    private T data;

    public boolean success() {
        return RESULT_CODE_SUCCESS == code;
    }

    public boolean Nsuccess() {
        return !success();
    }

    public static <T> ResultBean<T> SUCCESSNODATA(){
        return new ResultBean<T>(RESULT_CODE_SUCCESS, "暂无数据");
    }

    public static <T> ResultBean<T> SUCCESSNODATA(String message){
        if(StringUtils.isEmpty(message)) {
            return SUCCESSNODATA();
        }
        return new ResultBean<T>(RESULT_CODE_SUCCESS,message);
    }


    public static <T> ResultBean<T> SUCCESS() {
        return SUCCESS(null);
    }

    public static <T> ResultBean<T> SUCCESS(T t) {
        return new ResultBean<T>(RESULT_CODE_SUCCESS, t);
    }

    public static <T> ResultBean<T> SUCCESS(String message) {
        return new ResultBean<T>(RESULT_CODE_SUCCESS, message);
    }

    public static <T> ResultBean<T> EXCEPTION(String message) {
        return new ResultBean<T>(RESULT_CODE_SYSTEM_ERR, message);
    }

    public static <T> ResultBean<T> FAIL(String message) {
        return new ResultBean<T>(RESULT_CODE_SERVICE_ERR, message);
    }

    private ResultBean(int code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    public ResultBean(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }


    public ResultBean(int code,String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return data;
    }

    public void setObject(T data) {
        this.data = data;
    }

}
