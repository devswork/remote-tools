package com.github.devswork.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

public class OursCoreAssert {
    public static final String ASSERT_PREFIX = "[OursCoreAssert]";

    public static void notEmpty(Map map, String message) {
        if (map == null || map.size() <= 0) {
            throw new IllegalArgumentException(ASSERT_PREFIX + message);
        }
    }

    public static void empty(Map map, String message) {
        if (map != null && map.size() > 0) {
            throw new IllegalArgumentException(ASSERT_PREFIX + message);
        }
    }

    public static void notEmpty(Collection collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(ASSERT_PREFIX + message);
        }
    }

    public static void empty(Collection collection, String message) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(ASSERT_PREFIX + message);
        }
    }

    public static void notEmpty(String key, String message) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException(ASSERT_PREFIX + message);
        }
    }

    public static void empty(String key, String message) {
        if (StringUtils.isNotEmpty(key)) {
            throw new IllegalArgumentException(ASSERT_PREFIX + message);
        }
    }

    public static void notBlank(String key, String message) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException(ASSERT_PREFIX + message);
        }
    }

    public static void blank(String key, String message) {
        if (StringUtils.isNotBlank(key)) {
            throw new IllegalArgumentException(ASSERT_PREFIX + message);
        }
    }

    public static void notNull(Object object, String message) {
        Assert.notNull(object, ASSERT_PREFIX + message);
    }

    public static void isNull(Object object, String message) {
        Assert.isNull(object, ASSERT_PREFIX + message);
    }


    public static void isTrue(boolean bo, String message) {
        if (!bo) {
            throw new IllegalArgumentException(ASSERT_PREFIX + message);
        }
    }


    public static void isFalse(boolean bo, String message) {
        if (bo) {
            throw new IllegalArgumentException(ASSERT_PREFIX + message);
        }
    }
}

