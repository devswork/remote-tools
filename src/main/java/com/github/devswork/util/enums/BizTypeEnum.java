package com.github.devswork.util.enums;

public enum BizTypeEnum implements OursBaseEnum {
    UNKNOWN(0,"未知"),
    JOIN_BEAT(1,"拼音"),
    JOIN_BEAT_TEMPLATE(2,"拼音模板"),
    JOIN_BEAT_TEMPLATE_CLASSIFY(3,"拼音分类"),
    COMMENT(4,"留言"),
    COUNTER(5,"统计"),

    WORKS(6,"node那边的视频"),
    CHALLENGE(7,"挑战任务"),

    ;
    //不能重复,缓存用到value做拼接key
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
