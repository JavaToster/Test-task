package com.example.Test_task.util.exceptions.person;

import com.example.Test_task.util.exceptions.ApplicationRuntimeException;

public class PersonRegisterException extends ApplicationRuntimeException {
    public PersonRegisterException(String msg){
        super(msg);
    }
}
