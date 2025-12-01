package com.nasa.auth.service;

import com.nasa.auth.DTO.UserLogin;
import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    protected AuthService(UserService userService){
        this.userService=userService;
    }

    public String login(UserLogin userLogin){
        UserEntity userEntity = userService.findByUserName(userLogin.getUserName());
        if(userEntity!=null && userEntity.getPassword().equalsIgnoreCase(userLogin.getPassword())){
            return "Login was successfull";
        }
        throw new RuntimeException("Error While login");
    }
}
