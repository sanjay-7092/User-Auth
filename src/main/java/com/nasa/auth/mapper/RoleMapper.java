package com.nasa.auth.mapper;

import com.nasa.auth.dto.Role;
import com.nasa.auth.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleEntity toEntity(Role role);
    Role toDto(RoleEntity role);
    List<RoleEntity> toEntityList(List<Role> roleList);
    List<Role> toDtoList(List<RoleEntity> roleEntities);

}
