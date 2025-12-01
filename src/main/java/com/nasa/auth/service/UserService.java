package com.nasa.auth.service;

import com.nasa.auth.DTO.User;
import com.nasa.auth.DTO.UserView;
import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.mapper.UserMapper;
import com.nasa.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    UserService(UserRepository userRepository,UserMapper userMapper){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
    }
    public UserView signUp(User user){
        UserEntity userEntity =  userMapper.toEntity(user);
        return userMapper.toUserViewDto(userRepository.save(userEntity));
    }
}
