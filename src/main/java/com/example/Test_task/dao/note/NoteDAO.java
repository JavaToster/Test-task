package com.example.Test_task.dao.note;

import com.example.Test_task.models.note.Note;
import com.example.Test_task.repositories.note.NoteRepository;
import com.example.Test_task.util.exceptions.note.NoteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoteDAO {
    private final NoteRepository noteRepository;

    public Note findById(long id) throws NoteNotFoundException{
        return noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException("Note not found"));
    }

    public void save(Note note){
        noteRepository.save(note);
    }

    public List<Note> findAll(){
        return noteRepository.findAll();
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }
}
