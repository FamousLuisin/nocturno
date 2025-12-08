package com.nocturno.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nocturno.api.models.post.PostModel;
import com.nocturno.api.models.user.UserModel;

@Repository
public interface PostRepository extends JpaRepository<PostModel, UUID> {
    
    List<PostModel> findByCreator(UserModel creator);
}
