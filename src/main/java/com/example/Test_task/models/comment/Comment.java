package com.example.Test_task.models.comment;

import com.example.Test_task.models.note.Note;
import com.example.Test_task.models.person.Person;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "text")
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @ManyToOne
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    private Note note;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Person owner;

    public Comment(){}
    public Comment(String text, Note note, Person owner){
        this.text = text;
        this.note = note;
        this.owner = owner;
        this.creationDate = new Date();
    }
}
