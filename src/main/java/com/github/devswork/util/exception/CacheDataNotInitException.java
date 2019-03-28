package com.github.devswork.util.exception;


public class CacheDataNotInitException extends Exception{
    public CacheDataNotInitException() {
    }

    public CacheDataNotInitException(String message) {
        super(message);
    }

    public CacheDataNotInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheDataNotInitException(Throwable cause) {
        super(cause);
    }

    public CacheDataNotInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
