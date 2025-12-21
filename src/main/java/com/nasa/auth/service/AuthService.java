package com.nasa.auth.service;

import com.nasa.auth.DTO.UserLogin;
import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.Exception.InvalidUserExeption;
import com.nasa.auth.Exception.UnAuthorizedAccessException;
import com.nasa.auth.util.AuthUtil;
import com.nasa.auth.util.JwtUtil;
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
        try {
            UserEntity userEntity = userService.findByUserName(userLogin.getUserName());
            boolean isPasswordMatches = AuthUtil.matches(userLogin.getPassword(), userEntity.getPassword());
            if (isPasswordMatches) {
                    return JwtUtil.generateToken(userEntity.getEmail());
            }else{
                throw new UnAuthorizedAccessException("AUTH-LOGIN-001");
            }
        }catch(InvalidUserExeption ex){
            throw new InvalidUserExeption(ex.getErrorCode());
        }catch(UnAuthorizedAccessException ex){
            throw new UnAuthorizedAccessException(ex.getErrorCode());
        }
    }

}


