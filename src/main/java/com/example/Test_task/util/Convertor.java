package com.example.Test_task.util;

import com.example.Test_task.dao.person.PersonDAO;
import com.example.Test_task.dto.auth.PersonRegisterRequestDTO;
import com.example.Test_task.dto.note.CreatedNoteDTO;
import com.example.Test_task.dto.note.EditNoteDTO;
import com.example.Test_task.dto.note.NoteDTO;
import com.example.Test_task.dto.note.NotesDTO;
import com.example.Test_task.models.note.Note;
import com.example.Test_task.models.person.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Convertor {
    private final ModelMapper modelMapper;
    private final PersonDAO personDAO;

    public Person convertToPerson(PersonRegisterRequestDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }

    public NotesDTO convertToNotesDTO(List<Note> allNotes) {
        List<NoteDTO> notesDTOList = new ArrayList<>();
        for(Note note: allNotes){
            notesDTOList.add(convertToNoteDTO(note));
        }
        return new NotesDTO(notesDTOList);
    }

    public CreatedNoteDTO convertToCreatedNoteDTO(Note createdNote) {
        CreatedNoteDTO createdNoteDTO = modelMapper.map(createdNote, CreatedNoteDTO.class);
        createdNoteDTO.setAuthorEmail(createdNote.getAuthor().getEmail());
        return createdNoteDTO;
    }

    public NoteDTO convertToNoteDTO(Note note) {
        return modelMapper.map(note, NoteDTO.class);
    }

    public List<NoteDTO> convertToNoteDTO(List<Note> notes){
        List<NoteDTO> notesDTO = new ArrayList<>();
        notes.forEach(note -> notesDTO.add(convertToNoteDTO(note)));
        return notesDTO;
    }
}
