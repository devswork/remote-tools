package com.github.devswork.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static boolean isInteger(BigDecimal value) {
        return new BigDecimal(value.intValue()).compareTo(value)==0;
    }
}
