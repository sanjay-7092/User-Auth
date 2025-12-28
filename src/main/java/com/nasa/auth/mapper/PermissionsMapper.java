package com.nasa.auth.mapper;

import com.nasa.auth.dto.Permission;
import com.nasa.auth.entity.PermissionEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PermissionsMapper {
    PermissionEntity toEntity (Permission permission);

    Permission toDto(PermissionEntity permission);

    Set<PermissionEntity> toEntityList(Set<Permission> permissions);
}
