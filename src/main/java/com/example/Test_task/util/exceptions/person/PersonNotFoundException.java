package com.example.Test_task.util.exceptions.person;

import com.example.Test_task.util.exceptions.ApplicationRuntimeException;

public class PersonNotFoundException extends ApplicationRuntimeException {
    public PersonNotFoundException(String msg) {
        super(msg);
    }
}
