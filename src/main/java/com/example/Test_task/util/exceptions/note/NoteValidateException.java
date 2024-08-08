package com.example.Test_task.util.exceptions.note;

import com.example.Test_task.util.exceptions.ApplicationRuntimeException;

public class NoteValidateException extends ApplicationRuntimeException {
    public NoteValidateException(String msg) {
        super(msg);
    }
}
