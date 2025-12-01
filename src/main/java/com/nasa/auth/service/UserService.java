package com.nasa.auth.service;

import com.nasa.auth.DTO.User;
import com.nasa.auth.DTO.UserView;
import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.mapper.UserMapper;
import com.nasa.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<UserView> getAllUsers(){
        List<UserEntity> userEntities = userRepository.findAll();
        return userMapper.toAllUserViewDtos(userEntities);
    }

    public UserView getByUserId(Long id){
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userMapper.toUserViewDto(userEntity.get());
    }
    public UserView updateByID(Long id,User user){
     Optional<UserEntity> userEntity = userRepository.findById(id);
     if(userEntity.isPresent()){
        user.setId(id);
        UserEntity userResponse = userRepository.save(userMapper.toEntity(user));
        return userMapper.toUserViewDto(userResponse);
     }
     else{
         throw new RuntimeException("User Not Found");
     }
    }
}
