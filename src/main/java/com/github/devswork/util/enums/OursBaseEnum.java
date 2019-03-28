package com.github.devswork.util.enums;

public interface OursBaseEnum {
    int getValue();

    String getName();

    static <T extends OursBaseEnum> T valueOf(Class<T> c, int value) {
        if (c != null && c.isEnum()) {
            T[] enums = c.getEnumConstants();
            for (T e: enums) {
                if (e.getValue() == value) {
                    return e;
                }
            }
        }
        return null;
    }
}