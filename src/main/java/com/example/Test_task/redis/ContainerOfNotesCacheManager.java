package com.example.Test_task.redis;

import com.example.Test_task.dao.containerOfNotes.ContainerOfNotesDAO;
import com.example.Test_task.models.container.ContainerOfNotes;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContainerOfNotesCacheManager {
    private final ContainerOfNotesDAO containerOfNotesDAO;

    @Cacheable(cacheNames = "containerOfNotes", key = "#id")
    public ContainerOfNotes findById(long id){
        try {
            Thread.sleep(1000*3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ContainerOfNotes container = containerOfNotesDAO.findById(id);
        Hibernate.initialize(container.getNotes());
        return container;
    }

    @CachePut(cacheNames = "containerOfNotes", key = "#result.id")
    public ContainerOfNotes updateOrSave(ContainerOfNotes container){
        return container;
    }
}
