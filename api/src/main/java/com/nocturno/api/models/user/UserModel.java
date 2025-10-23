package com.nocturno.api.models.user;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity(name = "tb_users") @Table(name = "tb_users")
public class UserModel{
    
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "display_name")
    private String displayName;

    private String username;

    private String email;

    private String password;

    private String bio;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "birth")
    private LocalDate birthday;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDate createdAt;

    private String picture;

    public UserModel(String displayName, String username, String email, String password, String bio, LocalDate birthday, String picture){
        this.displayName = displayName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.birthday = birthday;
        this.picture = picture;
    }

    @PrePersist
    void preSave(){
        if (this.role == null) this.role = Role.USER;
        if (this.status == null) this.status = Status.ACTIVE;
    }
}
