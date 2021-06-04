package com.angular.spring.utils;

import org.springframework.util.DigestUtils;

public class HashUtils {
    public static String md5hash(String hashText) {
        byte[] hashBytes = hashText.getBytes();
        return DigestUtils.md5DigestAsHex(hashBytes);
    }
}