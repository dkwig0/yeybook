package com.dkwig0.yeybook.exceptions;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException(Long id) {
        super("Message wasn't found id: " + id);
    }

}
