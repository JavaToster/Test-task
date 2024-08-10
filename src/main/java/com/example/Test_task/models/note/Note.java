package com.example.Test_task.models.note;

import com.example.Test_task.models.comment.Comment;
import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.models.person.Person;
import com.example.Test_task.util.enums.note.NotePriority;
import com.example.Test_task.util.enums.note.NoteStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "note")
public class Note implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private NoteStatus status;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Person author;
    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private Person executor;
    @Enumerated(EnumType.ORDINAL)
    private NotePriority priority;
    @OneToMany(mappedBy = "note")
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "container_id", referencedColumnName = "id")
    private ContainerOfNotes container;

    public Note(String title, String description, Person author){
        this.title = title;
        this.description = description;
        this.author = author;
        this.status = NoteStatus.WAITING;
        this.priority = NotePriority.LOW;
    }

    public Note(){}

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", author=" + author +
                ", executor=" + executor +
                ", priority=" + priority +
                ", comments=" + comments +
                ", container=" + container +
                '}';
    }
}
