package com.example.Test_task.util.exceptions.containerOfNotes;

import com.example.Test_task.util.exceptions.ApplicationRuntimeException;

public class ContainerOfNotesNotFoundException extends ApplicationRuntimeException {
    public ContainerOfNotesNotFoundException(String msg) {
        super(msg);
    }
}
