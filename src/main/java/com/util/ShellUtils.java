package com.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShellUtils {

    public static String ex(ShellBean shell) {
        String result = "";
        ArrayList<String> list = shell.getList();
        for (String string : list) {
            result = ShellUtils.e(string);
        }
        return result;
    }

    public static String e(String commandStr) {
        BufferedReader br = null;
        StringBuilder sb = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    return e.toString();
                }
            }
        }
        return sb.toString();
    }

}