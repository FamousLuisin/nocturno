package com.nocturno.api.models.user.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserDTO {
    
    private String email;
    private String username;
    private String displayName;
    private String bio;
    private LocalDate birthday;
    private String picture;
}
