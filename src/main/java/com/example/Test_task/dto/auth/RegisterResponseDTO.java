package com.example.Test_task.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDTO {
    private String email;
}
