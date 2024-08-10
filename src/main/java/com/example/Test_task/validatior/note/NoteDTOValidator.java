package com.example.Test_task.validatior.note;

import com.example.Test_task.dto.note.EditNoteDTO;
import com.example.Test_task.dto.note.NoteDTO;
import com.example.Test_task.models.note.Note;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

@Component
public class NoteDTOValidator {
    public void validateEditNoteDTO(EditNoteDTO editNoteDTO, Errors errors){
        System.out.println(editNoteDTO.getStatus());
        if(editNoteDTO.getTitle() == null || editNoteDTO.getTitle().isBlank()){
            errors.rejectValue("title", "", "title should be not empty");
        }

        if(editNoteDTO.getDescription() == null || editNoteDTO.getDescription().isBlank()){
            errors.rejectValue("description", "", "description should be not empty");
        }

        if(editNoteDTO.getExecutorEmail() == null || editNoteDTO.getExecutorEmail().isBlank()){
            errors.rejectValue("executorEmail", "", "executor email should be not empty");
        }

        if(editNoteDTO.getStatus() == null || editNoteDTO.getStatus().isBlank()){
            errors.rejectValue("status", "", "status should be not empty");
        }
    }

    public void validateNoteDTO(NoteDTO note, BindingResult errors){
        if(note.getTitle() == null || note.getTitle().isBlank()){
            errors.rejectValue("title", "", "title should be not empty");
        }
        if(note.getDescription() == null || note.getDescription().isBlank()){
            errors.rejectValue("description", "", "description should be not empty");
        }
    }
}
