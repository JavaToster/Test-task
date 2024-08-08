package com.example.Test_task.dto.comment;

import com.example.Test_task.dto.note.NoteDTO;
import com.example.Test_task.dto.person.PersonDTO;
import lombok.Builder;
import lombok.Data;

@Data
public class CommentDTO {
    private long id;
    private String text;
    private String creationTime;
    private NoteDTO note;
    private PersonDTO owner;

    public CommentDTO(long id, String text){
        this.id = id;
        this.text = text;
    }
}
