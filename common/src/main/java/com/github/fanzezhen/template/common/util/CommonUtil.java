package com.github.fanzezhen.template.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CommonUtil {
    public static String encrypt(String s){
        return new BCryptPasswordEncoder().encode(s);
    }
}
