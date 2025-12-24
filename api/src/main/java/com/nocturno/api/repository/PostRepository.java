package com.nocturno.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nocturno.api.models.post.PostModel;
import com.nocturno.api.models.user.UserModel;

import jakarta.transaction.Transactional;

@Repository
public interface PostRepository extends JpaRepository<PostModel, UUID> {
    
    List<PostModel> findByCreator(UserModel creator);

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE tb_posts
                SET number_likes = number_likes + 1
                WHERE id = :id
            """, nativeQuery = true)
    void addLikes(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE tb_posts
                SET number_likes = number_likes - 1
                WHERE id = :id
            """, nativeQuery = true)
    void removeLikes(@Param("id") UUID id);
}
