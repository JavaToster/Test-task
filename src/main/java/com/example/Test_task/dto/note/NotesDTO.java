package com.example.Test_task.dto.note;

import lombok.Data;

import java.util.List;

@Data
public class NotesDTO {
    private List<NoteDTO> notes;

    public NotesDTO(List<NoteDTO> notes){
        this.notes = notes;
    }
}
