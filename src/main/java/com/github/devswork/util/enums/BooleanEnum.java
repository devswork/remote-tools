package com.github.devswork.util.enums;

public enum BooleanEnum implements OursBaseEnum{
    FALSE(0,"false",false),
    TRUE(1,"true",true),
    ;
    int value;
    String name;
    boolean booleanValue;

    BooleanEnum(int value,String name,boolean booleanValue){
        this.name=name;
        this.value = value;
        this.booleanValue = booleanValue;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    public boolean booleanValue(){
        return this.booleanValue;
    }

    public static BooleanEnum valueOf(boolean bo){
        return bo?TRUE:FALSE;
    }

    public static BooleanEnum valueOf(int value){
        return TRUE.getValue() == value ? TRUE:FALSE;
    }

    @Override
    public String getName() {
        return name;
    }
}
