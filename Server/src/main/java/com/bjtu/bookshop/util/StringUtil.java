package com.bjtu.bookshop.util;

import org.springframework.util.DigestUtils;

public class StringUtil {
    public static String MD5(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }

    public static String getRandString() {
        return getRandString(32);
    }

    public static String getRandString(int size) {
        String rtn = "";
        for (int i = 0; i < 10; i++) {
            rtn = MD5(rtn + Math.random());
        }
        return rtn;
    }
}