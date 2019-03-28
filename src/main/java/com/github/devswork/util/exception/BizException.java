package com.github.devswork.util.exception;

import org.apache.commons.lang.ArrayUtils;

import java.text.MessageFormat;

/**
 *  on 2017/10/6.
 */
public class BizException extends RuntimeException {
    // 错误码
    private int errorCode;

    // 展示给用户的错误信息
    private String errorMsg;

    // 展示给开发者的错误信息
    private String errorMsgForDev;

    public BizException() {
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getErrorMsgForDev() {
        return errorMsgForDev;
    }

    public <T extends IResultMessage> BizException(T resultMsg, Object... args) {
        super("errorCode:" + resultMsg.getCode() + ", errorMsg:" + MessageFormat.format(resultMsg.getMsgForDev(), args));

        // 设置错误码
        this.errorCode = resultMsg.getCode();

        Object [] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            arguments[i] = String.valueOf(args[i]);
        }

        // 设置展示给用户的错误信息
        this.errorMsg = resultMsg.getMsg();

        if (!ArrayUtils.isEmpty(args)) {
            this.errorMsg = MessageFormat.format(resultMsg.getMsg(), arguments);
        }

        // 设置展示给开发者的错误信息
        this.errorMsgForDev = resultMsg.getMsgForDev();

        if (!ArrayUtils.isEmpty(args)) {
            this.errorMsgForDev = MessageFormat.format(resultMsg.getMsgForDev(), arguments);
        }
    }
}
