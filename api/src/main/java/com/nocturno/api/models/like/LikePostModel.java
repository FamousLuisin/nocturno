package com.nocturno.api.models.like;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.nocturno.api.models.post.PostModel;
import com.nocturno.api.models.user.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity(name = "tb_post_likes") @Table(name = "tb_post_likes")
public class LikePostModel {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostModel post;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
