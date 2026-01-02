package com.nocturno.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nocturno.api.models.like.LikePostModel;
import com.nocturno.api.models.like.dto.LikePostDTO;
import com.nocturno.api.models.post.PostModel;
import com.nocturno.api.models.user.UserModel;

@Repository
public interface LikePostRepository extends JpaRepository<LikePostModel, UUID> {
    
    LikePostModel findByPostAndUser(PostModel post, UserModel user);

    @Query("""
        SELECT new com.nocturno.api.models.like.dto.LikePostDTO(
            u.username,
            u.displayName,
            pl.createdAt
        )
        FROM tb_post_likes pl
        JOIN pl.user u
        WHERE pl.post.id = :postId
        ORDER BY pl.createdAt DESC
    """)
    List<LikePostDTO> findLikesByPost(UUID postId);
}
