package com.github.devswork.util.constent;

public class OursCoreCacheContent {
    public static final String CACHE_NAMESPACE = "root:";

    public static final String SET_PREFIX = CACHE_NAMESPACE + "set_";

    public static final String LOCK_PREFIX = CACHE_NAMESPACE + "lock:";

    public static final long DEFAULT_CACHE_EXPIRE_30_DAY = 30*24*1000*60*60L;

    public static final long DEFAULT_CACHE_EXPIRE_12_HOUR = 12*1000*60*60L;

    public static final long DEFAULT_CACHE_EXPIRE_24_HOUR = 24*1000*60*60L;

    public static final long DEFAULT_CACHE_EXPIRE_36_HOUR = 36*1000*60*60L;

    public static final long DEFAULT_CACHE_EXPIRE_ONE_HOUR = 1000*60*60L;

    public static final long DEFAULT_CACHE_EXPIRE_ONE_MINUTE = 1000*60L;

    public static final long DEFAULT_CACHE_EXPIRE_ONE_SECONDS = 1000L;

    public static final long DEFAULT_CACHE_EXPIRE_ZERO = 1000L;

    //没数据插入缓存的score值
    public static final long DEFAULT_NULL_DATA_ZSET_CACHE_SCORE = -10000000000L;
}
