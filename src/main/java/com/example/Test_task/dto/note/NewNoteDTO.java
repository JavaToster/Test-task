package com.example.Test_task.dto.note;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewNoteDTO {
    @NotEmpty(message = "title should be not empty")
    @Size(min = 5, message = "title should be minimum 5 characters")
    private String title;
    @NotEmpty(message = "description should be not empty")
    @Size(min = 10, message = "description should be minimum 10 characters ")
    private String description;
}
