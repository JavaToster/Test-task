package com.example.Test_task.services.note;

import com.example.Test_task.dao.containerOfNotes.ContainerOfNotesDAO;
import com.example.Test_task.dao.note.NoteDAO;
import com.example.Test_task.dao.person.PersonDAO;
import com.example.Test_task.dto.note.EditNoteDTO;
import com.example.Test_task.dto.note.NoteDTO;
import com.example.Test_task.models.container.ContainerOfNotes;
import com.example.Test_task.models.note.Note;
import com.example.Test_task.redis.ContainerOfNotesCacheManager;
import com.example.Test_task.validatior.note.NoteDTOValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private PersonDAO personDAO;
    @Mock
    private NoteDAO noteDAO;
    @Mock
    private NoteDTOValidator noteDTOValidator;
    @Mock
    private ContainerOfNotesDAO containerOfNotesDAO;
    @Mock
    private ContainerOfNotesCacheManager containerOfNotesCacheManager;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private NoteService noteService;

    @Test
    void findById() {
        Note exceptedNote = new Note(1);

        doReturn(new Note(1))
                .when(noteDAO)
                .findById(1);

        Note actualNote = noteService.findById(1);
        assertEquals(exceptedNote, actualNote);
    }
}