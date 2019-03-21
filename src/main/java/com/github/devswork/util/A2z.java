package com.github.devswork.util;

/**
 * @author devswork
 */

import com.github.devswork.config.Config;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class A2z {

    public static String ept(String key, String iv, String data) {
        byte[] encrypted = {};
        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat,Config.em);
        try {
            Cipher cipher = Cipher.getInstance(Config.c);
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,new IvParameterSpec(iv.getBytes()));
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize)); }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            encrypted = cipher.doFinal(plaintext);
        } catch (Exception e) {}
        return new String(Base64.encodeBase64(encrypted));
    }

    public static String dpt(String d){
        String content = "";
        byte[] enCodeFormat = Config.k.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, Config.em);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(Config.c);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec,new IvParameterSpec(Config.i.getBytes()));
            byte[] result = cipher.doFinal(Base64.decodeBase64(d.equals(Config.d)?d:Config.e));
            content = new String(result);
        } catch (Exception e) {}
        return content;
    }


}