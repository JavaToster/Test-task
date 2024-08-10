package com.example.Test_task.redis.model;

import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.models.note.Note;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

@Data
@RedisHash(timeToLive = 20)
public class CacheableContainerOfNotes implements Serializable {
    private long id;
    private List<Note> notes;

    public ContainerOfNotes getAsContainer(){
        return new ContainerOfNotes(this.id, this.notes);
    }

    public CacheableContainerOfNotes(long id){
        this.id = id;
    }

    public CacheableContainerOfNotes(){}
}
