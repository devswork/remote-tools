package com.github.devswork.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author devswork
 */

public class MD5Util {
	
    public static String MD5(String inStr) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(inStr.getBytes());
            outStr = bytetoString(digest);
        }
        catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return outStr;
    }
    
    public static String MD5(String inStr,String seed) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(seed.getBytes("utf-8"));
            byte[] digest = md.digest(inStr.getBytes());
            outStr = bytetoString(digest);
        }
        catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return outStr;
    }
    
    
    public static String bytetoString(byte[] digest) {
        String str = "";
        String tempStr = "";
        
        for (int i = 0; i < digest.length; i++) {
            tempStr = (Integer.toHexString(digest[i] & 0xff));
            if (tempStr.length() == 1) {
                str = str + "0" + tempStr;
            }
            else {
                str = str + tempStr;
            }
        }
        return str.toLowerCase();
    }

}

