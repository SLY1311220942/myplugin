package com.sly.myplugin.base.exception;

import com.sly.myplugin.base.result.BaseResultCode;
import com.sly.myplugin.base.result.IResultCode;

/**
 * 业务异常
 *
 * @author SLY
 * @date 2022/1/2
 */
public class BusinessException extends RuntimeException {

    /**
     * 返回码
     */
    private String code = BaseResultCode.SERVER_ERROR.getCode();

    /**
     * 消息
     */
    private String msg;

    /**
     * 自定义 data 数据
     */
    private Object data;

    public BusinessException() {

    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(IResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public BusinessException(String message) {
        this.msg = message;
    }

    public BusinessException(IResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
