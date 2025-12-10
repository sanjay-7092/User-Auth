package com.nasa.auth.service;

import com.nasa.auth.DTO.UserLogin;
import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.Util.AuthUtil;
//import com.nasa.security.utils.JWTUtil;
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
//                return JWTUtil.generateToken(userEntity.getEmail());
                return "Succesful";
            }
            return "Password Doesn't matches";
        }
    return "User not found";
    }

}


