package com.nasa.auth.mapper;

import com.nasa.auth.DTO.User;
import com.nasa.auth.DTO.UserView;
import com.nasa.auth.Entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface UserMapper {

    UserEntity toEntity(User user);
    User toDto(UserEntity userEntity);
    UserView toUserViewDto(UserEntity userEntity);
}
