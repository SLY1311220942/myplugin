package com.sly.myplugin.base.result;

/**
 * 基础结果code
 * @author SLY
 * @date 2021/12/6
 */
public enum BaseResultCode implements IResultCode {
    /** 重复提交 */
    PREVENT_REPEAT("999999", "请不要重复提交"),
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
