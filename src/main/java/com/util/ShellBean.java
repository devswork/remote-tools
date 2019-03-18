package com.util;

import java.io.Serializable;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

public class ShellBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private  ArrayList<String> list;

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	public static void main(String[] args) {
		ShellBean shellBean = new ShellBean();
		ArrayList<String> list = new ArrayList<String>();
		list.add("cd /");
		list.add("ls");
		shellBean.setList(list);
		String jsonString = JSON.toJSONString(shellBean);
		System.out.println(jsonString);
	}
}
