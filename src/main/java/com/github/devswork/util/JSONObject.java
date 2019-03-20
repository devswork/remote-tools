package com.github.devswork.util;

import java.util.Map;


public class JSONObject implements java.io.Serializable{

	private static final long serialVersionUID = -4926471374344394316L;

	private com.alibaba.fastjson.JSONObject obj=null;
	
	public JSONObject(){
		obj = new com.alibaba.fastjson.JSONObject(false);
	}
	
	public JSONObject(boolean ordered){
		obj = new com.alibaba.fastjson.JSONObject(ordered);
	}
	
	public JSONObject(Map<String,Object> map){
		obj = new com.alibaba.fastjson.JSONObject(map);
	}
	
	public void put(String key,Object value){
		obj.put(key, value);
	}
	
	public void put(Map map){
		obj.putAll(map);
	}
	
	public String toJson(){
		return obj.toJSONString();
	}
	
}