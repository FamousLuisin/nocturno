package com.nocturno.api.models.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class UpdateDTO {
    private String username;
    private String displayName;
    private String bio;
}
