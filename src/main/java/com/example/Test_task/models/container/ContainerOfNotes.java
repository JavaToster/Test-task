package com.example.Test_task.models.container;

import com.example.Test_task.dto.containerOfNotes.ContainerOfNotesDTO;
import com.example.Test_task.models.note.Note;
import com.example.Test_task.redis.ContainerOfNotesCacheManager;
import com.example.Test_task.redis.model.CacheableContainerOfNotes;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "Container_Of_Notes")
public class ContainerOfNotes implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "container")
    private List<Note> notes;

    @Override
    public String toString() {
        return "ContainerOfNotes{" +
                "id=" + id +
                ", notes=" + notes +
                '}';
    }

    public ContainerOfNotes(long id){
        this.id = id;
    }

    public ContainerOfNotes(){}

    public ContainerOfNotes(long id, List<Note> notes){
        this.id = id;
        this.notes = notes;
    }


}
