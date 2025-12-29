package com.nasa.auth.service;

import com.nasa.auth.dto.Permission;
import com.nasa.auth.dto.Role;
import com.nasa.auth.entity.PermissionEntity;
import com.nasa.auth.entity.RoleEntity;
import com.nasa.auth.exception.RoleErrorException;
import com.nasa.auth.mapper.RoleMapper;
import com.nasa.auth.repository.PermissionRepository;
import com.nasa.auth.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;
    protected RoleService(RoleRepository roleRepository, RoleMapper roleMapper,PermissionRepository permissionRepository){
        this.roleRepository=roleRepository;
        this.roleMapper=roleMapper;
        this.permissionRepository=permissionRepository;
    }

    public List<Role> getAll(){
        log.info("Retriving the all roles");
        List<RoleEntity> roleEntities= roleRepository.findAll();
        if(CollectionUtils.isEmpty(roleEntities)){
            log.error("No Roles found");
            throw new RoleErrorException("ROLE-002","No roles found");
        }
        return roleMapper.toDtoList(roleEntities);
    }

    public Role getById(Long id){
        log.info("Reterive the role by id");
        return roleMapper.toDto(getRoleEntityById(id).orElseThrow(()-> new RoleErrorException("ROLE-002","No ROle found with the id")));
    }
    Optional<RoleEntity> getRoleEntityById(Long id){
        return roleRepository.findById(id);
    }
    public Role create(Role role){
        log.info("Creating the Role of : {}",role.getName());
        Optional<RoleEntity> roleEntity = roleRepository.findByName(role.getName());
        if(roleEntity.isPresent()){
               throw new RoleErrorException("ROLE-001","Role name Already exists");
        }
        RoleEntity currentEntity = roleMapper.toEntity(role);
        if(currentEntity.getPermissions()!=null) {
            handlePermissions(role.getPermissions(),currentEntity);
        }
        return roleMapper.toDto(roleRepository.save(currentEntity));
    }

    public Role updateById(Role role,Long id){
        RoleEntity roleEntity =roleRepository.findById(id)
                .orElseThrow(() ->
                        new RoleErrorException("ROLE-001","Role name Already exists"));
        roleEntity.setName(role.getName());
        roleEntity.setDescription(role.getDescription());
        if(roleEntity.getPermissions()!=null) {
            handlePermissions(role.getPermissions(),roleEntity);
        }
        return roleMapper.toDto(roleRepository.save(roleEntity));
    }

    private void handlePermissions(Set<Permission> permissions,RoleEntity roleEntity){
        Set<PermissionEntity> permissionEntities = new HashSet<>();
        for(Permission permission: permissions){
            if(permission.getId()!=null) {
                Optional<PermissionEntity> current = permissionRepository.findById(permission.getId());
                if(current.isPresent()){
                    permissionEntities.add(current.get());
                }
            }
        }
        roleEntity.setPermissions(permissionEntities);
    }
    public void delete(Long id){
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(()-> new RoleErrorException("ROLE-001","Role Doesn't exist"));
        log.info("Role is marked for delete role name :{}",roleEntity.getName());
        roleRepository.deleteById(id);
    }


}
