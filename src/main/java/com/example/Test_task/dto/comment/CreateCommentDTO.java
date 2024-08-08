package com.example.Test_task.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCommentDTO {
    @NotEmpty
    @Size(min = 3, message = "comment should be minimum 3 characters")
    private String text;
}
