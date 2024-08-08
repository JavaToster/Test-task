package com.example.Test_task.util.exceptions.comment;

import com.example.Test_task.util.exceptions.ApplicationRuntimeException;

public class CommentValidateException extends ApplicationRuntimeException {
    public CommentValidateException(String msg) {
        super(msg);
    }
}
