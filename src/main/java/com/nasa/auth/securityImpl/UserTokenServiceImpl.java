//package com.nasa.auth.securityImpl;
//
//import com.nasa.auth.Entity.UserEntity;
//import com.nasa.auth.service.UserService;
//import com.nasa.security.models.UserToken;
//import com.nasa.security.service.UserTokenService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserTokenServiceImpl implements UserTokenService {
//
//    private final UserService userService;
//
//    UserTokenServiceImpl(UserService userService){
//        this.userService=userService;
//    }
//    @Override
//    public UserToken getByUserName(String userName) {
//        UserEntity userEntity = userService.findByUserName(userName);
//        if(userEntity!=null){
//            UserToken userToken = new UserToken();
//            userToken.setFirstName(userEntity.getFirstName());
//            userToken.setLastName(userEntity.getLastName());
//            userToken.setUsername(userEntity.getEmail());
//            return userToken;
//        }else{
//            throw new RuntimeException("User Not exists");
//        }
//    }
//}
