package com.sly.myplugin.base.result;

/**
 * 基础结果code
 * @author SLY
 * @date 2021/12/6
 */
public enum BaseResultCode implements IResultCode {
    /** 重复提交 */
    PREVENT_REPEAT("999997", "请不要重复提交！"),
    /** 不支持的HTTP请求方式 */
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("999998", "HTTP请求方式有误，请检查！"),
    /** 服务器未知异常 */
    SERVER_ERROR("999999", "抱歉，服务器繁忙请稍候再试！"),
    ;
    BaseResultCode(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private final String code;
    private final String msg;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
