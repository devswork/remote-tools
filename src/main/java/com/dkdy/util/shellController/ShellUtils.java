package com.dkdy.util.shellController;
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
  
public class ShellUtils {  
    public static String exeCmd(String commandStr) {  
        BufferedReader br = null; 
        StringBuilder sb=null;
        try {  
            Process p = Runtime.getRuntime().exec(commandStr);  
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));  
            String line = null;  
            sb = new StringBuilder();  
            while ((line = br.readLine()) != null) {  
                sb.append(line + "\n");  
            } 
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        finally  
        {  
            if (br != null)  
            {  
                try {  
                    br.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return sb.toString();
    }  
  
    public static void main(String[] args) {  
        String commandStr = "ping www.520may.com";  
        //String commandStr = "ipconfig";    
    }  
}