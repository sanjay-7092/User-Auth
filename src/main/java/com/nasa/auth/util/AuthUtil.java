package com.nasa.auth.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public  class AuthUtil {



    private static PasswordEncoder passwordEncoder;

    public AuthUtil(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }


    public static String encode(String password){
        return passwordEncoder.encode(password);
    }

    public static boolean matches(String rawPassword,String enocodedPassword){
        return passwordEncoder.matches(rawPassword,enocodedPassword);
    }
}
