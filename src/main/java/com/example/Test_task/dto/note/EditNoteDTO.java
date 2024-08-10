package com.example.Test_task.dto.note;

import lombok.Data;

@Data
public class EditNoteDTO {
    private String title;
    private String description;
    private String executorEmail;
    private String status;
    private String priority;
}
