package com.nocturno.api.models.post.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
public class PostDTO {
    private UUID id; 
    private String content;
    private LocalDateTime createdAt;
    private String creatorUsername;
}
