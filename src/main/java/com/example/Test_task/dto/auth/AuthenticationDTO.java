package com.example.Test_task.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthenticationDTO {
    @NotEmpty(message = "email should be not empty")
    private String email;
    @NotEmpty(message = "password should be not empty")
    private String password;
}
