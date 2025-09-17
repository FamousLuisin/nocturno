package com.nocturno.api.models.user.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class RegisterDTO {
    private String displayName;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String bio;
    private LocalDate birthday;
}
