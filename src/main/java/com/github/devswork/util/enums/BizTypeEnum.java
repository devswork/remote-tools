package com.github.devswork.util.enums;

public enum BizTypeEnum implements OursBaseEnum {
    UNKNOWN(0,"unknown"),
    JOIN_BEAT(1,"pinyin"),
    JOIN_BEAT_TEMPLATE(2,"pinyin model"),
    JOIN_BEAT_TEMPLATE_CLASSIFY(3,"pinyin type"),
    COMMENT(4,"record"),
    COUNTER(5,"count"),
    WORKS(6,"node"),
    CHALLENGE(7,"task");

    int value;
    String name;

    BizTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    public String getName() {
        return name;
    }
}
