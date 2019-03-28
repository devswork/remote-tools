package com.github.devswork.util.exception;


public class EntityUniqueIndexException extends RuntimeException {
    public EntityUniqueIndexException() { }

    public EntityUniqueIndexException(String message) {
        super(message);
    }

    public EntityUniqueIndexException(String message,Throwable cause) {
        super(message,cause);
    }
}
