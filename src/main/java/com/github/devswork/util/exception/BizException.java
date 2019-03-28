package com.github.devswork.util.exception;

import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;

public class BizException extends RuntimeException {

    private int errorCode;

    private String errorMsg;


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

        this.errorCode = resultMsg.getCode();

        Object [] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            arguments[i] = String.valueOf(args[i]);
        }


        this.errorMsg = resultMsg.getMsg();

        if (!ArrayUtils.isEmpty(args)) {
            this.errorMsg = MessageFormat.format(resultMsg.getMsg(), arguments);
        }

        this.errorMsgForDev = resultMsg.getMsgForDev();

        if (!ArrayUtils.isEmpty(args)) {
            this.errorMsgForDev = MessageFormat.format(resultMsg.getMsgForDev(), arguments);
        }
    }
}
