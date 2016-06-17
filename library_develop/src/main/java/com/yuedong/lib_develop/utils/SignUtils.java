package com.yuedong.lib_develop.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密帮助类
 */
public class SignUtils {

    /**
     * md5
     *
     * @param data
     * @return
     */
    public static String md5(String data) {
        char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte buf[] = digest.digest(data.getBytes("UTF-8"));
            char str[] = new char[buf.length * 2];
            int k = 0;
            for (int i = 0; i < buf.length; i++) {
                byte byte0 = buf[i];
                str[k++] = hex[byte0 >>> 4 & 0xf];
                str[k++] = hex[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
