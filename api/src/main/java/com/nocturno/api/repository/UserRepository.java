package com.nocturno.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nocturno.api.models.user.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    
    UserModel findByEmail(String email);

    UserModel findByUsername(String username);
}
