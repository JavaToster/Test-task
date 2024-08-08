package com.example.Test_task.models.container;

import com.example.Test_task.models.note.Note;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "ContainerOfNotes")
public class ContainerOfNotes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "container")
    private List<Note> notes;
}
