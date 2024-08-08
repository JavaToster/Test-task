package com.example.Test_task.services.comment;

import com.example.Test_task.dao.comment.CommentDAO;
import com.example.Test_task.dao.note.NoteDAO;
import com.example.Test_task.dao.person.PersonDAO;
import com.example.Test_task.dto.comment.CreateCommentDTO;
import com.example.Test_task.models.comment.Comment;
import com.example.Test_task.models.note.Note;
import com.example.Test_task.models.person.Person;
import com.example.Test_task.util.exceptions.comment.CommentValidateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentDAO commentDAO;
    private final NoteDAO noteDAO;
    private final PersonDAO personDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Comment addComment(long noteId, CreateCommentDTO createCommentDTO, String email,
                              BindingResult errors) {
        if(errors.hasErrors()){
            throw new CommentValidateException(errors.getAllErrors().getFirst().getDefaultMessage());
        }

        Note note = noteDAO.findById(noteId);
        Person commentOwner = personDAO.findByEmail(email);
        Comment newComment = new Comment(createCommentDTO.getText(), note, commentOwner);
        commentDAO.save(newComment);
        return newComment;
    }
}
