package com.github.devswork.util.exception;

/**
 * 违反唯一索引约束
 * @author owell
 * @date 2018/7/25 13:01
 */
public class EntityUniqueIndexException extends RuntimeException {
    public EntityUniqueIndexException() {
    }

    public EntityUniqueIndexException(String message) {
        super(message);
    }

    public EntityUniqueIndexException(String message,Throwable cause) {
        super(message,cause);
    }
}
