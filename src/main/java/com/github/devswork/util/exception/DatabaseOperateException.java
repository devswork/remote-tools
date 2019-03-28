package com.github.devswork.util.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseOperateException extends Exception {

    private static final Integer DEFUALT_STATUS = ResponseStatusCode.SAVE_DATABASE_FAILED;
    private static final String DEFUALT_MESSAGE = "数据库发生异常，来自DatabaseOperateException";

    private Integer status;
    private String message;


    public DatabaseOperateException() {
        super(DEFUALT_MESSAGE);
        this.message = DEFUALT_MESSAGE;
        this.status = DEFUALT_STATUS;
    }

    public DatabaseOperateException(String message) {
        super(message);
        this.message = message;
        this.status = DEFUALT_STATUS;
    }

    public DatabaseOperateException(Integer status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        log.error("发生异常啦！！！！DatabaseOperateException异常{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}');
        return "DatabaseOperateException异常{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

}
