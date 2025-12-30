package com.nasa.auth.service;

import com.nasa.auth.dto.Role;
import com.nasa.auth.dto.User;
import com.nasa.auth.dto.UserSecure;
import com.nasa.auth.dto.UserView;
import com.nasa.auth.entity.RoleEntity;
import com.nasa.auth.entity.UserEntity;
import com.nasa.auth.exception.InvalidUserExeption;
import com.nasa.auth.util.AuthUtil;
import com.nasa.auth.mapper.UserMapper;
import com.nasa.auth.repository.UserRepository;
import com.nasa.auth.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    UserService(UserRepository userRepository, UserMapper userMapper,RoleService roleService){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.roleService=roleService;
    }
    public UserView signUp(User user){
        try {
            log.info("Creating the user");
            if(isInValidUser(user)){
                log.error("Required fields is missed for creating user");
                throw new InvalidUserExeption("AUTH-USER-001");
            }else{
                if(userRepository.findByEmail(user.getEmail()).isPresent()){
                    log.error("User Already exist with this email : {}",user.getEmail());
                    throw new InvalidUserExeption("AUTH-USER-002");
                }
                if(!user.getPassword().equals(user.getConfirmPassword())){
                    log.error("Password doesn't matches with confirm password");
                    throw new InvalidUserExeption("AUTH-USER-003");
                }
                UserEntity newUser = userMapper.toEntity(user);
                handleRoles(newUser,user.getRoles());
                newUser.setPassword(AuthUtil.encode(newUser.getPassword()));
                UserEntity userResponse = userRepository.save(newUser);
                log.info("User was successfully created");
                return userMapper.toUserViewDto(userResponse);
            }
        } catch(InvalidUserExeption ex){
            log.error("Error while creating User : {}",ex.getMessage());
            throw new InvalidUserExeption("Unable to create user : Contact Adminstrator",ex.getErrorCode());
        }catch(Exception ex){
            log.error("Error while creating User : {}",ex.getMessage());
            throw new InvalidUserExeption("Unable to create user : Contact Adminstrator");
        }
    }
    private boolean isInValidUser(User user){
        return StringUtil.isEmpty(user.getEmail()) || StringUtil.isEmpty(user.getPassword()) || StringUtil.isEmpty(user.getFirstName()) ||StringUtil.isEmpty(user.getConfirmPassword());
    }
    private void handleRoles(UserEntity userEntity, Set<Role> roles){
        Set<RoleEntity> roleEntities = new HashSet<>();
        for(Role role:roles){
            Optional<RoleEntity> roleEntity = roleService.getRoleEntityById(role.getId());
            if(roleEntity.isPresent()){
                roleEntities.add(roleEntity.get());
            }
        }
        userEntity.setRoles(roleEntities);
    }

    public List<UserView> getAllUsers(){
        log.info("Retreving the all users");
        List<UserEntity> userEntities = userRepository.findAll();
        if(userEntities.isEmpty()){
            log.error("Users are empty");
            throw new InvalidUserExeption("AUTH-USER-005");
        }
        return userMapper.toAllUserViewDtos(userEntities);
    }

    @Cacheable(cacheNames = "user",key = "#id")
    public UserView getByUserId(Long id){
        log.info("Fetching the user with the id : {}",id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->new InvalidUserExeption("AUTH-USER-004"));
        return userMapper.toUserViewDto(userEntity);
    }
    @CachePut(cacheNames = "user",key="#id")
    public UserView updateByID(Long id,User user) {
            log.info("Updating the user with the id : {}",id);
            UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new InvalidUserExeption("AUTH-USER-004"));
            userEntity.setDob(user.getDob());
            userEntity.setEmail(user.getEmail());
            userEntity.setContactNumber(user.getContactNumber());
            userEntity.setIsActive(user.getIsActive());
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            handleRoles(userEntity,user.getRoles());
            UserEntity updated = userRepository.save(userEntity);
            log.info("Successfully updated the user");
            return userMapper.toUserViewDto(updated);
    }
     public UserEntity findByUserName(String userName){
         log.info("Fetch the user by email : {}",userName);
         return userRepository.findByEmail(userName).orElseThrow(() -> new InvalidUserExeption("User Not Found with the Email : {} "+ userName,""));
    }

    @CacheEvict(cacheNames = "user",key="#id")
    public void deleteUserById(Long id){
        log.info("Delete the user by id");
        if(!userRepository.existsById(id)){
            throw new InvalidUserExeption("","User Not found");
        }
        userRepository.deleteById(id);
    }

    public void changePassword(UserSecure userSecure,Long id){
        log.info("Changing the password for the user with the id : {}",id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()->new InvalidUserExeption("AUTH-USER-004"));
        if(!AuthUtil.matches(userSecure.getCurrentPassword(),userEntity.getPassword())){
            log.error("current password doesn't matches");
            throw new InvalidUserExeption("AUTH-LOGIN-001");
        }
        else{
            if(AuthUtil.matches(userSecure.getNewPassword(),userEntity.getPassword())){
                log.error("New password must be diffrent from current password");
                throw new InvalidUserExeption("New Password must be newer than old password","");
            }
            userEntity.setPassword(AuthUtil.encode(userSecure.getNewPassword()));
            userRepository.save(userEntity);
        }
    }
}
