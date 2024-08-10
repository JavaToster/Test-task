package com.example.Test_task.redis;

import com.example.Test_task.dao.containerOfNotes.ContainerOfNotesDAO;
import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.redis.model.CacheableContainerOfNotes;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContainerOfNotesCacheManager {
    private final ContainerOfNotesDAO containerOfNotesDAO;
    private final ModelMapper modelMapper;

    @Cacheable(cacheNames = "containerOfNotes", key = "#id")
    public CacheableContainerOfNotes findById(long id){
        try {
            Thread.sleep(1000*3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ContainerOfNotes container = containerOfNotesDAO.findById(id);
        return modelMapper.map(container, CacheableContainerOfNotes.class);
    }

    @CachePut(cacheNames = "containerOfNotes", key = "#result.id")
    public CacheableContainerOfNotes updateOrSave(ContainerOfNotes container){
        return modelMapper.map(container, CacheableContainerOfNotes.class);
    }
}
