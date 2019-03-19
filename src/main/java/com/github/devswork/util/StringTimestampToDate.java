package com.github.devswork.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author devswork
 */

@Slf4j
public class StringTimestampToDate {

    public static Date string2Date13(String timestamp) {
        Long times = Long.parseLong(timestamp);
        if (timestamp.length() == 13) {
            return new Date(times);
        }
        if(timestamp.length() == 10){
            return new Date(times * 1000);
        }

        return null;

    }

}
