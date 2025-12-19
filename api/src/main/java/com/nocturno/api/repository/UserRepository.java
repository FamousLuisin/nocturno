package com.nocturno.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nocturno.api.models.user.Status;
import com.nocturno.api.models.user.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    
    UserModel findByEmail(String email);

    UserModel findByUsername(String username);

    UserModel findByEmailAndStatus(String username, Status status);
}
