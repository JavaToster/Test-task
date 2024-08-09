package com.example.Test_task.dao.containerOfNotes;

import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.repositories.containerOfNotes.ContainerOfNotesRepository;
import com.example.Test_task.util.exceptions.containerOfNotes.ContainerOfNotesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContainerOfNotesDAO {
    private final ContainerOfNotesRepository containerOfNotesRepository;

    public ContainerOfNotes findById(Long containerId) {
        return containerOfNotesRepository.findById(containerId).orElseThrow(() -> new ContainerOfNotesNotFoundException("container with "+containerId+" not found"));
    }

    public long getLastIdOfContainer(){
        return containerOfNotesRepository.findLast();
    }

    public ContainerOfNotes getEmptyContainer(){
        return new ContainerOfNotes();
    }
    public void save(ContainerOfNotes container){
        containerOfNotesRepository.save(container);
    }
}
