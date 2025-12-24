package com.nocturno.api.models.post;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity(name = "tb_posts") @Table(name = "tb_posts")
@DynamicInsert
public class PostModel {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String content;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "creator")
    private UserModel creator;

    @Column(name = "number_likes")
    private Integer numberLikes;

    public PostModel(String content, UserModel creator){
        this.content = content;
        this.creator = creator;
    }
}
