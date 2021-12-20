package com.sly.myplugin.base.result;

/**
 * 返回结果code接口
 *
 * @author SLY
 * @date 2021/11/25
 */
public interface ResultCode {
    /**
     * 获取返回码
     *
     * @return {@link String}
     */
    String getCode();

    /**
     * 获取消息
     *
     * @return {@link String}
     */
    String getMsg();
}
