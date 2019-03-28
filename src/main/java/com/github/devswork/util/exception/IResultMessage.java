package com.github.devswork.util.exception;

/**
 * on 2017/10/6.
 */
public interface IResultMessage {
    /**
     * 获取错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 获取展示给客户的错误信息
     *
     * @return 错误信息
     */
    String getMsg();

    /**
     * 获取展示给开发者的错误信息
     *
     * @return 错误信息
     */
    String getMsgForDev();
}
