package com.nasa.auth.mapper;

import com.nasa.auth.dto.User;
import com.nasa.auth.dto.UserView;
import com.nasa.auth.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface UserMapper {

    UserEntity toEntity(User user);
    User toDto(UserEntity userEntity);
    UserView toUserViewDto(UserEntity userEntity);
    List<UserView> toAllUserViewDtos(List<UserEntity> userEntities);
}
