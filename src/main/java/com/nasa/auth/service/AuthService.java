package com.nasa.auth.service;

import com.nasa.auth.DTO.UserLogin;
import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.util.AuthUtil;
import com.nasa.auth.util.JWTUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthUtil authUtil;

    protected AuthService(UserService userService, AuthUtil authUtil) {
        this.userService = userService;
        this.authUtil = authUtil;
    }

    public String login(UserLogin userLogin) {
        UserEntity userEntity = userService.findByUserName(userLogin.getUserName());
        if (userEntity != null) {
            boolean isPasswordMatches = authUtil.matches(userLogin.getPassword(), userEntity.getPassword());
            if (isPasswordMatches) {
                return JWTUtil.generateToken(userEntity.getEmail());
            }
            throw new RuntimeException("Password doesn't match");
        }
    throw new RuntimeException("User Not Found");
    }

}


