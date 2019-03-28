package com.github.devswork.util.exception;

/**
 * @author owell
 * @date 2018/8/10 18:29
 */
public class LockException extends RuntimeException {

    public LockException() {
    }

    public LockException(String cacheKey) {
        super(String.format("key:{%s}获取锁失败",cacheKey));
    }
}
