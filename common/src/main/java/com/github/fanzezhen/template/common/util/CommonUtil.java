package com.github.fanzezhen.template.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CommonUtil {
    public static String encrypt(String s){
        return new BCryptPasswordEncoder().encode(s);
    }

    public static boolean verifyPassword(String rawPassword, String encodedPassword){
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++){
            System.out.println(encrypt("111111"));
        }
    }
}
