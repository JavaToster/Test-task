package com.example.Test_task.dao.comment;

import com.example.Test_task.models.comment.Comment;
import com.example.Test_task.repositories.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDAO {
    private final CommentRepository commentRepository;

    public void save(Comment comment){
        commentRepository.save(comment);
    }

}
