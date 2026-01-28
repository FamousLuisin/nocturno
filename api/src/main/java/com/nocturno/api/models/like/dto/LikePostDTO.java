package com.nocturno.api.models.like.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class LikePostDTO {
    private String username;
    private String displayName;
    private LocalDateTime createdAt;
}
