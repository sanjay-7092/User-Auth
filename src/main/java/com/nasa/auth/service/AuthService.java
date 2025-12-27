package com.nasa.auth.service;

import com.nasa.auth.dto.UserLogin;
import com.nasa.auth.entity.UserEntity;
import com.nasa.auth.exception.InvalidUserExeption;
import com.nasa.auth.exception.UnAuthorizedAccessException;
import com.nasa.auth.util.AuthUtil;
import com.nasa.auth.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final UserService userService;
    private final AuthUtil authUtil;

    protected AuthService(UserService userService, AuthUtil authUtil) {
        this.userService = userService;
        this.authUtil = authUtil;
    }

    public String login(UserLogin userLogin) {
        try {
            log.info("User tries to login");
            UserEntity userEntity = userService.findByUserName(userLogin.getUserName());
            boolean isPasswordMatches = AuthUtil.matches(userLogin.getPassword(), userEntity.getPassword());
            if (isPasswordMatches) {
                    log.info("Password matches : returning the auth token");
                    return JwtUtil.generateToken(userEntity);
            } else{
                log.error("password doesn't matches");
                throw new UnAuthorizedAccessException("AUTH-LOGIN-001");
            }
        }catch(InvalidUserExeption ex){
            log.error("Error while login: {}",ex.getMessage());
            throw new InvalidUserExeption(ex.getErrorCode());
        }catch(UnAuthorizedAccessException ex){
            log.error("Error while loin : {}",ex.getMessage());
            throw new UnAuthorizedAccessException(ex.getErrorCode());
        }
    }

}


