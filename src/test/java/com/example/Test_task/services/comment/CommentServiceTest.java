package com.example.Test_task.services.comment;

import com.example.Test_task.dao.comment.CommentDAO;
import com.example.Test_task.dao.note.NoteDAO;
import com.example.Test_task.dao.person.PersonDAO;
import com.example.Test_task.dto.comment.CreateCommentDTO;
import com.example.Test_task.models.comment.Comment;
import com.example.Test_task.models.note.Note;
import com.example.Test_task.models.person.Person;
import com.example.Test_task.util.enums.note.NotePriority;
import com.example.Test_task.util.enums.note.NoteStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private CommentDAO commentDAO;
    @Mock
    private NoteDAO noteDAO;
    @Mock
    private PersonDAO personDAO;
    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private CommentService commentService;

    @Test
    void addComment() {
        Comment comment = new Comment();
        comment.setId(0);
        comment.setCreationDate(new Date());
        comment.setText("superText");
        CreateCommentDTO createCommentDTO = new CreateCommentDTO();
        createCommentDTO.setText("superText");
        Comment actual = commentService.addComment(1, createCommentDTO, "kamil.gizatullin.03@gmail.com", bindingResult);
        assertEquals(comment, actual);
    }
}