package com.nocturno.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nocturno.api.models.like.LikePostModel;
import com.nocturno.api.models.post.PostModel;
import com.nocturno.api.models.user.UserModel;

@Repository
public interface LikePostRepository extends JpaRepository<LikePostModel, UUID> {
    
    LikePostModel findByPostAndUser(PostModel post, UserModel user);
}
