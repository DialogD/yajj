package com.dialogd.yajj.common.emuns;

/**
 *
 *
 */
public enum ApiResponseEnum implements BaseEnum {
    FAIL(           100, "FAIL"           , "失败"),
    SUCCESS(           200, "SUCCESS"           , "成功" ),
    INTERNAL_ERROR(    500, "INTERNAL_ERROR"    , "服务器处理失败" ),
    NOT_EXIST(    404, "NOT_EXIST"    , "记录不存在" ),
    PARAMETER_INVALID( 600, "PARAMETER_INVALID" , "非法参数"),
    SESSIONID_IS_NULL(    601, "SESSIONID_IS_NULL"    , "登录信息无效" ),
    AGENT_INFO_INVALID(    700, "AGENT_INFO_INVALID"    , "代理商账户信息无效" ),
    MERCHANT_NOT_EXISTS(    701, "MERCHANT_NOT_EXISTS"    , "商户不存在" ),
    SYSTEM_CODE_IS_VALID(    702, "SYSTEM_CODE_IS_VALID"    , "系统编码非法" ),
    ORDER_ID_IS_VALID(    703, "ORDER_ID_IS_VALID"    , "订单号非法" ),
    UPDATE_COUNT_TOO_MANY(    1000, "UPDATE_COUNT_TOO_MANY"    , "更新条数过多" ),
    APPLY_WITHDRAW_FAIL(    1001, "APPLY_WITHDRAW_FAIL"    , "申请提现失败" )
    ;

    protected int code;

    protected String subCode;

    protected String msg;

    ApiResponseEnum(int code, String subCode, String msg) {
        this.subCode = subCode;
        this.code = code;
        this.msg = msg;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getSubCode() {
        return subCode;
    }

    @Override
    public String getMsg() {
        return msg;
    }


}
