package com.example.Test_task.services.note;

import com.example.Test_task.dao.containerOfNotes.ContainerOfNotesDAO;
import com.example.Test_task.dao.note.NoteDAO;
import com.example.Test_task.dao.person.PersonDAO;
import com.example.Test_task.dto.note.EditNoteDTO;
import com.example.Test_task.dto.note.NewNoteDTO;
import com.example.Test_task.dto.note.NoteDTO;
import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.models.note.Note;

import com.example.Test_task.redis.ContainerOfNotesCacheManager;
import com.example.Test_task.util.enums.note.NoteStatus;
import com.example.Test_task.util.exceptions.note.NoteNotFoundException;
import com.example.Test_task.util.exceptions.note.NoteValidateException;
import com.example.Test_task.util.exceptions.secutiry.ForbiddenException;
import com.example.Test_task.validatior.note.NoteDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {
    private final PersonDAO personDAO;
    private final NoteDAO noteDAO;
    private final NoteDTOValidator noteDTOValidator;
    private final ContainerOfNotesDAO containerOfNotesDAO;
    private final ContainerOfNotesCacheManager containerOfNotesCacheManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Note createNote(NoteDTO noteDTO, BindingResult errors, String email){
        noteDTOValidator.validateNoteDTO(noteDTO, errors);
        if(errors.hasErrors()){
            List<String> errorList = new ArrayList<>();
            for(ObjectError error: errors.getAllErrors()){
                errorList.add(error.getDefaultMessage());
            }
            throw new NoteValidateException(errorList.stream().collect(Collectors.joining(";")));
        }

        Note newNote = new Note(noteDTO.getTitle(), noteDTO.getDescription(),
                personDAO.findByEmail(email));

        ContainerOfNotes containerOfNotes = containerOfNotesDAO.getLast();
        setNoteContainer(containerOfNotes, newNote);

        noteDAO.save(newNote);
        containerOfNotesDAO.save(containerOfNotes);
        containerOfNotesCacheManager.updateOrSave(containerOfNotes);
        return newNote;
    }

    public Note findById(long id) {
        return noteDAO.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Note editNote(long id, EditNoteDTO editNoteDTO, String editorEmail,
                         BindingResult errors) throws NoteNotFoundException {
        Note note = noteDAO.findById(id);
        if(!isAuthor(note, editorEmail)){
            throw new ForbiddenException("you can't edit this note");
        }

        noteDTOValidator.validateEditNoteDTO(editNoteDTO, errors);

        if(errors.hasErrors()){
            List<String> errorsList = new ArrayList<>();
            for(ObjectError error: errors.getAllErrors()){
                errorsList.add(error.getDefaultMessage());
            }
            throw new NoteValidateException(errorsList.stream().collect(Collectors.joining(";")));
        }

        ContainerOfNotes container = note.getContainer();
        int idInNotes = container.getNotes().indexOf(note);

        note.setTitle(editNoteDTO.getTitle());
        note.setDescription(editNoteDTO.getDescription());
        note.setExecutor(personDAO.findByEmail(editNoteDTO.getExecutorEmail()));
        note.setStatus(NoteStatus.valueOf(editNoteDTO.getStatus()));

        container.getNotes().set(idInNotes, note);

        containerOfNotesDAO.save(container);
        containerOfNotesCacheManager.updateOrSave(container);
        noteDAO.save(note);
        return note;
    }

    private boolean isAuthor(Note note, String email){
        return note.getAuthor().getEmail().equals(email);
    }

    public Note editStatus(long id, String editorEmail, EditNoteDTO editNoteDTO, BindingResult errors) {
        Note note = noteDAO.findById(id);

        if(!isExecutorOrAuthor(note, editorEmail)){
            throw new ForbiddenException("you can't edit status");
        }

        noteDTOValidator.validateEditNoteDTO(editNoteDTO, errors);
        if(errors.hasFieldErrors("status")){
            throw new NoteValidateException(errors.getFieldError("status").getDefaultMessage());
        }
        ContainerOfNotes container = note.getContainer();
        int idInNotes = container.getNotes().indexOf(note);

        note.setStatus(NoteStatus.valueOf(editNoteDTO.getStatus()));
        container.getNotes().set(idInNotes, note);

        containerOfNotesDAO.save(container);
        containerOfNotesCacheManager.updateOrSave(container);
        noteDAO.save(note);
        return note;
    }

    private boolean isExecutorOrAuthor(Note note, String editorEmail) {
        return note.getExecutor().getEmail().equals(editorEmail) || note.getAuthor().getEmail().equals(editorEmail);
    }

    public Note setExecutor(long id, String editorEmail, EditNoteDTO editNoteDTO, BindingResult errors) {
        Note note = noteDAO.findById(id);

        if(!isAuthor(note, editorEmail)){
            throw new ForbiddenException("you can't set executor");
        }

        noteDTOValidator.validateEditNoteDTO(editNoteDTO, errors);
        if(errors.hasFieldErrors("executorEmail")){
            throw new NoteValidateException(errors.getFieldError("executorEmail").getField() + " - " + errors.getFieldError("executorEmail").getDefaultMessage());
        }
        ContainerOfNotes container = note.getContainer();
        int idInNotes = container.getNotes().indexOf(note);

        note.setExecutor(personDAO.findByEmail(editNoteDTO.getExecutorEmail()));

        container.getNotes().set(idInNotes, note);
        containerOfNotesDAO.save(container);
        containerOfNotesCacheManager.updateOrSave(container);
        noteDAO.save(note);
        return note;
    }

    public void delete(long id, String email) {
        Note note = noteDAO.findById(id);
        if(!isAuthor(note, email)){
            throw new ForbiddenException("you can't delete this note");
        }

        ContainerOfNotes container = note.getContainer();
        container.getNotes().remove(note);
        containerOfNotesDAO.save(container);
        containerOfNotesCacheManager.updateOrSave(container);
        noteDAO.delete(note);
    }

    private void setNoteContainer(ContainerOfNotes container, Note note){
        if(container.getNotes() == null || container.getNotes().isEmpty()){
            container.setNotes(Collections.singletonList(note));
            note.setContainer(container);
            return;
        }
        container.getNotes().add(note);
        note.setContainer(container);
    }
}
