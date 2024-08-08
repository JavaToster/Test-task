package com.example.Test_task.dto.note;

import lombok.Data;

@Data
public class CreatedNoteDTO {
    private long id;
    private String title;
    private String description;
    private String authorEmail;
}
