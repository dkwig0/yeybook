package com.dkwig0.yeybook.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User doesn't exists id:" + id);
    }

}
