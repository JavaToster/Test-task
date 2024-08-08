package com.example.Test_task.models.person;

import com.example.Test_task.models.comment.Comment;
import com.example.Test_task.models.note.Note;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "author")
    private List<Note> createdNotes;
    @OneToMany(mappedBy = "executor")
    private List<Note> executeNotes;
    @OneToMany(mappedBy = "owner")
    private List<Comment> comments;
}
