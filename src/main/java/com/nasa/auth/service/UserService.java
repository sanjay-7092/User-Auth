package com.nasa.auth.service;

import com.nasa.auth.DTO.User;
import com.nasa.auth.DTO.UserView;
import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.Util.AuthUtil;
import com.nasa.auth.mapper.UserMapper;
import com.nasa.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthUtil authUtil;
    UserService(UserRepository userRepository, UserMapper userMapper, AuthUtil authUtil){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.authUtil=authUtil;
    }
    public UserView signUp(User user){
        UserEntity newUser = userMapper.toEntity(user);
        newUser.setPassword(authUtil.encode(newUser.getPassword()));
        UserEntity userResponse = userRepository.save(newUser);
        return userMapper.toUserViewDto(userResponse);
    }

    public List<UserView> getAllUsers(){
        List<UserEntity> userEntities = userRepository.findAll();
        return userMapper.toAllUserViewDtos(userEntities);
    }

    public UserView getByUserId(Long id){
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userMapper.toUserViewDto(userEntity.get());
    }
    public UserView updateByID(Long id,User user) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            user.setId(id);
            UserEntity newUser = userMapper.toEntity(user);
            newUser.setPassword(authUtil.encode(userEntity.get().getPassword()));
            UserEntity userResponse = userRepository.save(newUser);
            return userMapper.toUserViewDto(userResponse);
        } else {
            throw new RuntimeException("User Not Found");
        }
    }
     public UserEntity findByUserName(String userName){
         UserEntity userEntity = userRepository.findByEmail(userName);
         return userEntity;
    }
}
