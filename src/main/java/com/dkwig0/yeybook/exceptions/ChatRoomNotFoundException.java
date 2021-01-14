package com.dkwig0.yeybook.exceptions;

public class ChatRoomNotFoundException extends RuntimeException {

    public ChatRoomNotFoundException(Long roomId) {
        super("Access denied for:" +
                " room id - " + roomId);
    }

}
