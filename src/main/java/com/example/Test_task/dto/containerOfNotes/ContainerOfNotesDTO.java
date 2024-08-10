package com.example.Test_task.dto.containerOfNotes;

import com.example.Test_task.dto.note.NoteDTO;
import com.example.Test_task.models.container.ContainerOfNotes;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@RedisHash(timeToLive = 120)
public class ContainerOfNotesDTO implements Serializable {
    private long id;
    private List<NoteDTO> notes;

    public ContainerOfNotesDTO(long id, List<NoteDTO> notes){
        this.id = id;
        this.notes = notes;
    }

    public ContainerOfNotesDTO(){}
}
