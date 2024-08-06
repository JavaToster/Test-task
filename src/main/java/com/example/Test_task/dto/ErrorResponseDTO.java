package com.example.Test_task.dto;

import lombok.Data;

@Data
public class ErrorResponseDTO{
    private String msg;

    public ErrorResponseDTO(String msg) {
        this.msg = msg;
    }
}