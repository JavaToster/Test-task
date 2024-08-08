package com.example.Test_task.util;

import com.example.Test_task.dao.person.PersonDAO;
import com.example.Test_task.dto.auth.PersonRegisterRequestDTO;
import com.example.Test_task.dto.comment.CommentDTO;
import com.example.Test_task.dto.containerOfNotes.ContainerOfNotesDTO;
import com.example.Test_task.dto.note.CreatedNoteDTO;
import com.example.Test_task.dto.note.EditNoteDTO;
import com.example.Test_task.dto.note.NoteDTO;
import com.example.Test_task.dto.note.NotesDTO;
import com.example.Test_task.dto.person.PersonDTO;
import com.example.Test_task.models.comment.Comment;
import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.models.note.Note;
import com.example.Test_task.models.person.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class Convertor {
    private final ModelMapper modelMapper;
    private final PersonDAO personDAO;

    public Person convertToPerson(PersonRegisterRequestDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }

    public PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
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

    public CommentDTO convertToCommentDTO(Comment createdComment) {
        CommentDTO commentDTO = new CommentDTO(createdComment.getId(), createdComment.getText());
        commentDTO.setNote(convertToNoteDTO(createdComment.getNote()));
        commentDTO.setOwner(convertToPersonDTO(createdComment.getOwner()));
        commentDTO.setCreationTime(convertDateToString(createdComment.getCreationDate()));

        return commentDTO;
    }

    public String convertDateToString(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        StringBuilder builder = new StringBuilder();
        builder.append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(calendar.get(Calendar.MONTH)).append(calendar.get(Calendar.YEAR)).append(" ")
                .append(calendar.get(Calendar.HOUR)).append(":").append(calendar.get(Calendar.MINUTE));

        return builder.toString();
    }

    public ContainerOfNotesDTO convertToContainerOfNotesDTO(ContainerOfNotes containerOfNotes){
        return new ContainerOfNotesDTO(containerOfNotes.getId(), convertToNoteDTO(containerOfNotes.getNotes()));
    }
}
