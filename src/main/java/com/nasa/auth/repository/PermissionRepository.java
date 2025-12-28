package com.nasa.auth.repository;

import com.nasa.auth.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity,Long> {
}
