package com.nasa.auth.service;

import com.nasa.auth.DTO.User;
import com.nasa.auth.DTO.UserSecure;
import com.nasa.auth.DTO.UserView;
import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.Exception.InvalidUserExeption;
import com.nasa.auth.util.AuthUtil;
import com.nasa.auth.mapper.UserMapper;
import com.nasa.auth.repository.UserRepository;
import com.nasa.auth.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

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
        try {
            if(isInValidUser(user)){
                throw new InvalidUserExeption("AUTH-USER-001");
            }else{
                if(userRepository.findByEmail(user.getEmail()).isPresent()){
                    throw new InvalidUserExeption("AUTH-USER-002");
                }
                if(!user.getPassword().equals(user.getConfirmPassword())){
                    throw new InvalidUserExeption("AUTH-USER-003");
                }
                UserEntity newUser = userMapper.toEntity(user);
                newUser.setPassword(authUtil.encode(newUser.getPassword()));
                UserEntity userResponse = userRepository.save(newUser);
                return userMapper.toUserViewDto(userResponse);
            }
        } catch(InvalidUserExeption ex){
            throw new InvalidUserExeption("Unable to create user : Contact Adminstrator",ex.getErrorCode());
        }catch(Exception ex){
            throw new InvalidUserExeption("Unable to create user : Contact Adminstrator");
        }
    }
    private boolean isInValidUser(User user){
        return StringUtil.isEmpty(user.getEmail()) || StringUtil.isEmpty(user.getPassword()) || StringUtil.isEmpty(user.getFirstName()) ||StringUtil.isEmpty(user.getConfirmPassword());
    }

    public List<UserView> getAllUsers(){
        List<UserEntity> userEntities = userRepository.findAll();
        if(userEntities.isEmpty()){
            throw new InvalidUserExeption("AUTH-USER-005");
        }
        return userMapper.toAllUserViewDtos(userEntities);
    }

    public UserView getByUserId(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->new InvalidUserExeption("AUTH-USER-004"));
        return userMapper.toUserViewDto(userEntity);
    }
    public UserView updateByID(Long id,User user) {
            UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new InvalidUserExeption("AUTH-USER-004"));
            userEntity.setDob(user.getDob());
            userEntity.setEmail(user.getEmail());
            userEntity.setRole(user.getRole());
            userEntity.setContactNumber(user.getContactNumber());
            userEntity.setIsActive(user.getIsActive());
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            UserEntity updated = userRepository.save(userEntity);
            return userMapper.toUserViewDto(updated);
    }
     public UserEntity findByUserName(String userName){
         return userRepository.findByEmail(userName).orElseThrow(() -> new InvalidUserExeption("User Not Found with the Email : {} "+ userName,""));
    }

    public void changePassword(UserSecure userSecure,Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()->new InvalidUserExeption("AUTH-USER-004"));
        if(!AuthUtil.matches(userSecure.getCurrentPassword(),userEntity.getPassword())){
           throw new InvalidUserExeption("AUTH-LOGIN-001");
        }
        else{
            if(AuthUtil.matches(userSecure.getNewPassword(),userEntity.getPassword())){
                throw new InvalidUserExeption("New Password must be newer than old password","");
            }
            userEntity.setPassword(AuthUtil.encode(userSecure.getNewPassword()));
            userRepository.save(userEntity);
        }
    }
}
