package com.example.Test_task.util.exceptions.note;

import com.example.Test_task.util.exceptions.ApplicationRuntimeException;

public class NoteNotFoundException extends ApplicationRuntimeException {
    public NoteNotFoundException(String msg) {
        super(msg);
    }
}
