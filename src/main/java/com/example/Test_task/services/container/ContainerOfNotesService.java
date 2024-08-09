package com.example.Test_task.services.container;

import com.example.Test_task.dao.containerOfNotes.ContainerOfNotesDAO;
import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.redis.ContainerOfNotesCacheManager;
import com.example.Test_task.util.exceptions.containerOfNotes.ContainerOfNotesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContainerOfNotesService {
    private final ContainerOfNotesDAO containerOfNotesDAO;
    private final ContainerOfNotesCacheManager containerOfNotesCacheManager;

    public ContainerOfNotes findById(Long containerId){
        if (containerId  == 0){
            return containerOfNotesCacheManager.findById(containerOfNotesDAO.getLastIdOfContainer());
        }

        try{
            return containerOfNotesCacheManager.findById(containerId);
        }catch (ContainerOfNotesNotFoundException e){
            return containerOfNotesDAO.getEmptyContainer();
        }
    }
}
