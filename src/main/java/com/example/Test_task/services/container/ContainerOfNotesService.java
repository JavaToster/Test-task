package com.example.Test_task.services.container;

import com.example.Test_task.dao.containerOfNotes.ContainerOfNotesDAO;
import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.util.exceptions.containerOfNotes.ContainerOfNotesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContainerOfNotesService {
    private final ContainerOfNotesDAO containerOfNotesDAO;

    public ContainerOfNotes findById(Long containerId) {
        return containerOfNotesDAO.findById(containerId);
    }

    public ContainerOfNotes findByIdLastContainer(Long containerId){
        System.out.println(containerId);
        if(containerId == -1){
            return containerOfNotesDAO.getLastContainerOfNotes();
        }

        try {
            return containerOfNotesDAO.findById(containerId);
        }catch (ContainerOfNotesNotFoundException e){
            return containerOfNotesDAO.getEmptyContainer();
        }
    }
}
