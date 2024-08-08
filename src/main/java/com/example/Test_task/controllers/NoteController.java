package com.example.Test_task.controllers;

import com.example.Test_task.dto.ErrorResponseDTO;
import com.example.Test_task.dto.comment.CommentDTO;
import com.example.Test_task.dto.comment.CreateCommentDTO;
import com.example.Test_task.dto.note.*;
import com.example.Test_task.models.comment.Comment;
import com.example.Test_task.models.note.Note;
import com.example.Test_task.security.PersonDetails;
import com.example.Test_task.services.comment.CommentService;
import com.example.Test_task.services.note.NoteService;
import com.example.Test_task.util.Convertor;
import com.example.Test_task.util.exceptions.ApplicationRuntimeException;
import com.example.Test_task.util.exceptions.person.PersonNotFoundException;
import com.example.Test_task.util.exceptions.secutiry.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final Convertor convertor;
    private final NoteService noteService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> showAllNotes() {
        List<NoteDTO> notes = convertor.convertToNoteDTO(noteService.findAllNotes());
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CreatedNoteDTO> create(@RequestBody NewNoteDTO newNoteDTO, Principal principal) {
        Note createdNote = noteService.createNote(newNoteDTO, principal.getName());
        return new ResponseEntity<>(convertor.convertToCreatedNoteDTO(createdNote), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> showNote(@PathVariable("id") long id) {
        NoteDTO noteDTO = convertor.convertToNoteDTO(noteService.findById(id));
        return new ResponseEntity<>(noteDTO, HttpStatus.OK);
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<NoteDTO> editNote(@PathVariable("id") long id, @RequestBody EditNoteDTO editNote, Principal principal,
                                            BindingResult errors) {
        String editorEmail = principal.getName();
        Note note = noteService.editNote(id, editNote, editorEmail, errors);

        return new ResponseEntity<>(convertor.convertToNoteDTO(note), HttpStatus.OK);
    }

    @PostMapping("/{id}/edit_status")
    public ResponseEntity<NoteDTO> editNoteStatus(@PathVariable("id") long id, @RequestBody EditNoteDTO editNoteDTO,
                                                  Principal principal, BindingResult errors){
        Note editedNote = noteService.editStatus(id, principal.getName(), editNoteDTO, errors);
        return new ResponseEntity<>(convertor.convertToNoteDTO(editedNote), HttpStatus.OK);
    }

    @PostMapping("/{id}/set_executor")
    public ResponseEntity<NoteDTO> setExecutor(@PathVariable("id") long id, @RequestBody EditNoteDTO editNoteDTO, Principal principal){
        Note editedNote = noteService.setExecutor(id, principal.getName(), editNoteDTO);
        return new ResponseEntity<>(convertor.convertToNoteDTO(editedNote), HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public HttpStatus delete(@PathVariable("id") long id, Principal principal){
        noteService.delete(id, principal.getName());
        return HttpStatus.OK;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> exceptionHandle(ApplicationRuntimeException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> exceptionHandle(ForbiddenException e){
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.FORBIDDEN);
    }

}
