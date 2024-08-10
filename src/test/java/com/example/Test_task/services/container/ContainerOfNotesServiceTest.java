package com.example.Test_task.services.container;

import com.example.Test_task.dao.containerOfNotes.ContainerOfNotesDAO;
import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.redis.ContainerOfNotesCacheManager;
import com.example.Test_task.redis.model.CacheableContainerOfNotes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ContainerOfNotesServiceTest {

    @Mock
    private ContainerOfNotesDAO containerOfNotesDAO;
    @Mock
    private ContainerOfNotesCacheManager containerOfNotesCacheManager;
    @InjectMocks
    private ContainerOfNotesService containerOfNotesService;

    @Test
    void findById() {
        CacheableContainerOfNotes exceptCacheableContainer = new CacheableContainerOfNotes(1);
        doReturn(new CacheableContainerOfNotes(1))
                .when(containerOfNotesCacheManager)
                .findById(1);

        ContainerOfNotes actualContainer = containerOfNotesService.findById(1L);
        CacheableContainerOfNotes actualCacheableContainer = new CacheableContainerOfNotes(actualContainer.getId());

        assertEquals(exceptCacheableContainer, actualCacheableContainer);

        ContainerOfNotes exceptedContainer = new ContainerOfNotes(1);
        doReturn(new ContainerOfNotes(1))
                .when(containerOfNotesDAO)
                .getLast();

        ContainerOfNotes actualContainerOfNotes = containerOfNotesService.findById(0l);
        assertEquals(exceptedContainer, actualContainerOfNotes);
    }
}