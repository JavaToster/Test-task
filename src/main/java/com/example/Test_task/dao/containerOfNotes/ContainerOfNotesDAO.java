package com.example.Test_task.dao.containerOfNotes;

import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.repositories.containerOfNotes.ContainerOfNotesRepository;
import com.example.Test_task.util.exceptions.containerOfNotes.ContainerOfNotesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContainerOfNotesDAO {
    private final ContainerOfNotesRepository containerOfNotesRepository;

    public ContainerOfNotes findById(Long containerId) {
        return containerOfNotesRepository.findById(containerId).orElseThrow(() -> new ContainerOfNotesNotFoundException("container with "+containerId+" not found"));
    }

    public ContainerOfNotes getLast(){
        return containerOfNotesRepository.findLast().orElse(getEmptyContainer());
    }

    public ContainerOfNotes getEmptyContainer(){
        return new ContainerOfNotes(0, Collections.emptyList());
    }

    public void save(ContainerOfNotes container){
        containerOfNotesRepository.save(container);
    }
}
