package com.github.devswork.util.exception;

public class LockException extends RuntimeException {

    public LockException() { }

    public LockException(String cacheKey) {
        super(String.format("key:{%s} get lock failed",cacheKey));
    }
}
