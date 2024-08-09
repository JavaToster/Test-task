package com.example.Test_task.controllers;

import com.example.Test_task.dto.ErrorResponseDTO;
import com.example.Test_task.dto.comment.CommentDTO;
import com.example.Test_task.dto.comment.CreateCommentDTO;
import com.example.Test_task.dto.containerOfNotes.ContainerOfNotesDTO;
import com.example.Test_task.dto.note.*;
import com.example.Test_task.models.comment.Comment;
import com.example.Test_task.models.note.Note;
import com.example.Test_task.services.comment.CommentService;
import com.example.Test_task.services.container.ContainerOfNotesService;
import com.example.Test_task.services.note.NoteService;
import com.example.Test_task.util.Convertor;
import com.example.Test_task.util.exceptions.ApplicationRuntimeException;
import com.example.Test_task.util.exceptions.secutiry.ForbiddenException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(
        name = "main controller in application",
        description = "here creating new notes, update and delete notes"
)
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final Convertor convertor;
    private final NoteService noteService;
    private final CommentService commentService;
    private final ContainerOfNotesService containerOfNotesService;

    @Operation(
            summary = "return all notes from database",
            description = "use pagination, method return note container with notes"
    )
    @GetMapping
    public ResponseEntity<ContainerOfNotesDTO> showAllNotes(@RequestParam(value = "id", defaultValue = "0") Long containerId) {
        ContainerOfNotesDTO container = convertor.convertToContainerOfNotesDTO(containerOfNotesService.findById(containerId));
        return new ResponseEntity<>(container, HttpStatus.OK);
    }

    @Operation(
            summary = "method create new note",
            description = "create new note and return createdNoteDTO, if request is valid, else throw ApplicationRuntimeException"
    )
    @PostMapping("/create")
    //TODO:EDIT RESPONSE ENTITY and will make request is valid
    public ResponseEntity<CreatedNoteDTO> create(@RequestBody NewNoteDTO newNoteDTO, Principal principal) {
        Note createdNote = noteService.createNote(newNoteDTO, principal.getName());
        return new ResponseEntity<>(convertor.convertToCreatedNoteDTO(createdNote), HttpStatus.OK);
    }

    @Operation(
            summary = "return note by id",
            description = "return NoteDTO"
    )
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> showNote(@PathVariable("id") long id) {
        NoteDTO noteDTO = convertor.convertToNoteDTO(noteService.findById(id));
        return new ResponseEntity<>(noteDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "method edit note by id and return edited note",
            description = "if info is validate - return edited note DTO, else throw ApplicationRuntimeException"
    )
    @PostMapping("/{id}/edit")
    public ResponseEntity<NoteDTO> editNote(@PathVariable("id") long id, @RequestBody EditNoteDTO editNote, Principal principal,
                                            BindingResult errors) {
        String editorEmail = principal.getName();
        Note note = noteService.editNote(id, editNote, editorEmail, errors);

        return new ResponseEntity<>(convertor.convertToNoteDTO(note), HttpStatus.OK);
    }

    @Operation(
            summary = "for update status of note",
            description = "it can do executor or author of note. Validate request dto, is valid return edited note dto else throw ApplicationRuntimeException. " +
                    "Also if person is not executor of author -> throw Forbidden exception"
    )
    @PostMapping("/{id}/edit_status")
    public ResponseEntity<NoteDTO> editNoteStatus(@PathVariable("id") long id, @RequestBody EditNoteDTO editNoteDTO,
                                                  Principal principal, BindingResult errors){
        Note editedNote = noteService.editStatus(id, principal.getName(), editNoteDTO, errors);
        return new ResponseEntity<>(convertor.convertToNoteDTO(editedNote), HttpStatus.OK);
    }

    @Operation(
            summary = "for set executor",
            description = "only author of notes can set executor. If person is not author, throw Forbidden exception"
    )
    @PostMapping("/{id}/set_executor")
    public ResponseEntity<NoteDTO> setExecutor(@PathVariable("id") long id, @RequestBody EditNoteDTO editNoteDTO, Principal principal){
        Note editedNote = noteService.setExecutor(id, principal.getName(), editNoteDTO);
        return new ResponseEntity<>(convertor.convertToNoteDTO(editedNote), HttpStatus.OK);
    }

    @Operation(
            summary = "for delete note",
            description = "only author can delete note, else throw Forbidden exception"
    )
    @PostMapping("/{id}/delete")
    public HttpStatus delete(@PathVariable("id") long id, Principal principal){
        noteService.delete(id, principal.getName());
        return HttpStatus.OK;
    }

    @Operation(
            summary = "for add comment to note",
            description = "everyone can add comment to note, validate request, if all is good, return created comment dto"
    )
    @PostMapping("/{id}/add_comment")
    public ResponseEntity<CommentDTO> addCommentToNote(@PathVariable("id") long noteId, @RequestBody @Valid CreateCommentDTO createCommentDTO, Principal principal,
                                                       BindingResult errors){
        Comment createdComment = commentService.addComment(noteId, createCommentDTO, principal.getName(), errors);
        return new ResponseEntity<>(convertor.convertToCommentDTO(createdComment), HttpStatus.OK);
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
