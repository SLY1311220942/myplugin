package com.sly.myplugin.base.result;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @author SLY
 * @date 2021/11/25
 */
public class Result<T> implements Serializable {
    /** 成功返回码 */
    private static final String SUCCESS_CODE = "000000";
    /** 成功返回消息 */
    private static final String SUCCESS_MSG = "操作成功！";
    /** 失败返回码 */
    private static final String FAILED_CODE = "999999";
    /** 失败返回消息 */
    private static final String FAILED_MSG = "请求失败！";

    /** 返回码 */
    private String code;
    /** 消息 */
    private String msg;
    /** 数据 */
    private T data;

    private Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MSG);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static <T> Result<T> failed() {
        return new Result<>(FAILED_CODE, FAILED_MSG);
    }

    public static <T> Result<T> failed(IResultCode code) {
        return new Result<>(code.getCode(), code.getMsg());
    }

    public static <T> Result<T> failed(T data) {
        return new Result<>(FAILED_CODE, FAILED_MSG, data);
    }

    public static <T> Result<T> failed(String code, String msg, T data) {
        Result<T> result = new Result<>(code, msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failed(IResultCode code, T data) {
        return new Result<>(code.getCode(), code.getMsg(), data);
    }

    public static <T> Result<T> getInstance(IResultCode code) {
        return new Result<>(code.getCode(), code.getMsg());
    }



    public static <T> Result<T> getInstance(IResultCode code, T data) {
        return new Result<>(code.getCode(), code.getMsg(), data);
    }

    public String getCode() {
        return code;
    }

    public Result<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
}
