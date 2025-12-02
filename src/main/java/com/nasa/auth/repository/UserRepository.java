package com.nasa.auth.repository;

import com.nasa.auth.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByEmail(String email);
}
