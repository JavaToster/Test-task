package com.example.Test_task.dto.note;

import com.example.Test_task.dto.person.PersonDTO;
import com.example.Test_task.util.enums.note.NotePriority;
import com.example.Test_task.util.enums.note.NoteStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class NoteDTO {
    private long id;
    private String title;
    private String description;
    private PersonDTO author;
    private PersonDTO executor;
    private NotePriority priority;
    private NoteStatus status;

    public NoteDTO(String title, String description) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public NoteDTO(){}
}
