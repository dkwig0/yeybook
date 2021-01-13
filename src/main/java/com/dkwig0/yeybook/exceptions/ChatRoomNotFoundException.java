package com.dkwig0.yeybook.exceptions;

public class ChatRoomNotFoundException extends RuntimeException {

    public ChatRoomNotFoundException(Long userId, Long roomId) {
        super("Access denied for:" +
                " user id - " + userId +
                " room id - " + roomId);
    }

}
